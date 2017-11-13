package com.asgab.service.api;

import com.alibaba.fastjson.JSONObject;
import com.asgab.constants.CacheKey;
import com.asgab.core.mail.MailTemplateEnum;
import com.asgab.entity.Address;
import com.asgab.entity.Area;
import com.asgab.entity.User;
import com.asgab.entity.UserEntity;
import com.asgab.service.*;
import com.asgab.service.account.AccountService;
import com.asgab.util.BeanMapper;
import com.asgab.util.RandomNumUtil;
import com.asgab.util.Validator;
import com.asgab.web.api.param.FindPwdParam;
import com.asgab.web.api.param.UserInfo;
import com.asgab.web.api.param.UserRegParam;
import com.asgab.web.api.param.VerifyCodeParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static com.asgab.constants.CacheKey.VERIFY_CODE_KEY;


@Component
@Transactional
public class UserWebService {
    /**
     * token缓存时间 15天
     */
    private static int CONVERSATION_KEEP_TIMEOUT = 60 * 60 * 24 * 15;

    /**
     * 密码最大错误次数
     */
    private static int MAX_PWD_ERROR_COUNT = 5;

    @Resource
    private MailService mailService;

    @Resource
    private AccountService accountService;

    @Resource
    private JedisService jedisService;

    @Resource
    private AddressService addressService;

    @Resource
    private AreaService areaService;

    @Resource
    private SmsService smsService;

    /**
     * 找回密码
     */
    public void resetPwd(FindPwdParam param) {
        if (StringUtils.isBlank(param.getLoginName())) {
            throw new ApiException("用戶名不能為空");
        }
        if (StringUtils.isBlank(param.getNewPassword())) {
            throw new ApiException("新密碼不能為空");
        }
        if (StringUtils.isBlank(param.getConfirmPassword())) {
            throw new ApiException("重複密碼不能為空");
        }
        final User user = accountService.checkLoginName(param.getLoginName());
        if (user == null) {
            throw new ApiException("用戶名不存在");
        }
        if (!"888888".equals(param.getVerifyCode())) {
            String verifyCode = jedisService.get(CacheKey.VERIFY_CODE_KEY + param.getLoginName());
            if (verifyCode == null) {
                throw new ApiException("驗證碼已過期");
            } else if (!verifyCode.equals(param.getVerifyCode())) {
                throw new ApiException("驗證碼錯誤");
            }
        }
        //更新密码
        user.setPlainPassword(param.getNewPassword());
        accountService.updateUser(user);
        this.cleanErrorCount(param.getLoginName());
    }

    /**
     * 注册用户
     */
    public void register(UserRegParam param) {
        if (StringUtils.isBlank(param.getLoginName())) {
            throw new ApiException("用戶名不能為空");
        }
        if (!(Validator.isEmail(param.getLoginName()) || Validator.isHongKongMobile(param.getLoginName()) || Validator.isMobile(param.getLoginName()))) {
            throw new ApiException("請輸入有效的用戶名");
        }
        if (StringUtils.isBlank(param.getVerifyCode())) {
            throw new ApiException("驗證碼不能為空");
        }
        if (StringUtils.isBlank(param.getPassword())) {
            throw new ApiException("密碼不能為空");
        }
        if (StringUtils.isBlank(param.getConfirmPassword())) {
            throw new ApiException("重複密碼不能為空");
        }
        if (!param.getPassword().equals(param.getConfirmPassword())) {
            throw new ApiException("兩次密碼輸入不一致");
        }
        if (!"888888".equals(param.getVerifyCode())) {
            String verifyCode = jedisService.get(VERIFY_CODE_KEY + param.getLoginName());
            if (verifyCode == null) {
                throw new ApiException("驗證碼已過期");
            } else if (!verifyCode.equals(param.getVerifyCode())) {
                throw new ApiException("驗證碼錯誤");
            }
        }
        User exists = accountService.findUserByLoginName(param.getLoginName());
        if (exists != null) {
            throw new ApiException("用戶名已存在");
        }
        //注册用户
        User user = new User();
        user.setLoginName(param.getLoginName());
        user.setPlainPassword(param.getPassword());
        accountService.registerUser(user);
    }

    /**
     * 发送验证码
     */
    public void sendVerifyCode(VerifyCodeParam param) {
        if (StringUtils.isBlank(param.getLoginName())) throw new ApiException("用戶名不能為空");
        if (param.getType() == null) throw new ApiException("驗證碼類型不能為空");
        MailTemplateEnum mte = null;
        //注册用户
        if (param.getType().equals(1)) {
            User user = accountService.checkLoginName(param.getLoginName());
            if (user != null) throw new ApiException("該用戶名已註冊");
            mte = MailTemplateEnum.REG_USER;
        }
        //重置密码
        else if (param.getType().equals(2)) {
            mte = MailTemplateEnum.RESET_PWD;
        } else {
            throw new ApiException("驗證碼類型錯誤");
        }
        //验证码
        String verifyCode = RandomNumUtil.getRandNumber(6);
        //验证码有效时间2分钟
        jedisService.setex(VERIFY_CODE_KEY + param.getLoginName(), 2 * 60, verifyCode);
        //发送验证码到邮箱
        if (Validator.isEmail(param.getLoginName())) {
            Map<String, Object> params = new HashMap<>();
            params.put("verifyCode", verifyCode);
            if (!mailService.sendCaptcha(param.getLoginName(), mte, params)) {
                throw new ApiException("驗證碼發送失敗，請聯絡客服");
            }
        }
        //发送验证码到手机
        else if (Validator.isHongKongMobile(param.getLoginName()) || Validator.isMobile(param.getLoginName())) {
            if (!smsService.sendVerifyCode(param.getLoginName(), verifyCode)) {
                throw new ApiException("驗證碼發送失敗，請聯絡客服");
            }
        } else {
            throw new ApiException("用戶名必須為有效手機號或郵箱");
        }
    }

    /**
     * 用户登录
     *
     * @param loginName
     * @param password
     */
    public void login(String loginName, String password, String token) {
        String errorCountKey = CacheKey.PWD_ERROR_COUNT_KEY + loginName;
        String errorCount = jedisService.get(errorCountKey);
        if (errorCount != null && Integer.valueOf(errorCount) >= MAX_PWD_ERROR_COUNT) {
            throw new ApiException("用戶名密碼錯誤次數過多，請聯絡客服或找回密碼");
        }
        if (StringUtils.isBlank(loginName)) {
            throw new ApiException("用戶名不能為空");
        }
        if (StringUtils.isBlank(password)) {
            throw new ApiException("密碼不能為空");
        }
        User user = accountService.login(loginName, password);
        if (user == null) {
            this.recordErrorCount(loginName);
            throw new ApiException("用戶名或密碼錯誤");
        } else {
            this.cleanErrorCount(loginName);
        }
        String userJson = jedisService.get(CacheKey.USER_ID_KEY + user.getId().toString());
        UserEntity history = JSONObject.parseObject(userJson, UserEntity.class);
        if (history != null && StringUtils.isNotBlank(history.getToken())) {
            jedisService.delete(CacheKey.TOKEN_KEY + history.getToken());
        }
        UserEntity userEntity = BeanMapper.map(user, UserEntity.class);
        userEntity.setToken(token);
        jedisService.setex(CacheKey.USER_ID_KEY + userEntity.getId().toString(), CONVERSATION_KEEP_TIMEOUT, JSONObject.toJSONString(userEntity));
        jedisService.setex(CacheKey.TOKEN_KEY + token, CONVERSATION_KEEP_TIMEOUT, JSONObject.toJSONString(userEntity));
    }

    public UserInfo profile(Long userId) {
        User user = accountService.getUser(userId);
        if (user == null) {
            throw new ApiException("請先登入");
        }
        UserInfo userInfo = BeanMapper.map(user, UserInfo.class);
        Address addressInfo = addressService.getUniqueAddressByUserId(userInfo.getId());
        if (addressInfo != null) {
            userInfo.setAddressId(addressInfo.getId());
            userInfo.setAreaId(addressInfo.getAreaId());
            userInfo.setAddress(addressInfo.getAddress());
            Area area = areaService.getAreaFromCache(addressInfo.getAreaId());
            if (area != null) {
                userInfo.setAreaName(area.getName());
            }
        }
        return userInfo;
    }

    public void updateUserInfo(UserInfo userInfo) {
        if (userInfo.getId() == null) throw new ApiException("參數錯誤");
        if (userInfo.getAreaId() == null) throw new ApiException("區號不能為空");
        User user = accountService.getUser(userInfo.getId());
        if (user == null) throw new ApiException("用戶不存在");
        //更新用户信息
        BeanMapper.copy(userInfo, user);
        accountService.updateUser(user);
        //更新地址信息
        Address addressInfo = addressService.get(userInfo.getAddressId());
        if (addressInfo == null) {
            addressInfo = new Address();
            addressInfo.setUserId(userInfo.getId());
            addressInfo.setAreaId(userInfo.getAreaId());
            addressInfo.setAddress(userInfo.getAddress());
            addressService.save(addressInfo);
        } else {
            addressInfo.setAreaId(userInfo.getAreaId());
            addressInfo.setAddress(userInfo.getAddress());
            addressService.update(addressInfo);
        }
        String userJson = jedisService.get(CacheKey.USER_ID_KEY + user.getId().toString());
        UserEntity userEntity = JSONObject.parseObject(userJson, UserEntity.class);
        if (userEntity != null) {
            BeanMapper.copy(user, userEntity);
            jedisService.set(CacheKey.USER_ID_KEY + userEntity.getId().toString(), JSONObject.toJSONString(userEntity));
            jedisService.set(CacheKey.TOKEN_KEY + userEntity.getToken(), JSONObject.toJSONString(userEntity));
        }
    }

    /**
     * 记录用户名密码错误次数
     *
     * @param loginName
     */
    private void recordErrorCount(String loginName) {
        String errorCountKey = CacheKey.PWD_ERROR_COUNT_KEY + loginName;
        String errorCount = jedisService.get(errorCountKey);
        int count = 1;
        if (errorCount != null) {
            count = Integer.parseInt(errorCount) + 1;
        }
        jedisService.setex(errorCountKey, CONVERSATION_KEEP_TIMEOUT, String.valueOf(count));
    }

    /**
     * 清空密码错误次数
     *
     * @param loginName
     */
    private void cleanErrorCount(String loginName) {
        jedisService.delete(CacheKey.PWD_ERROR_COUNT_KEY + loginName);
    }


}