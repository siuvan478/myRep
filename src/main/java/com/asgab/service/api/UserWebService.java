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

    /**
     * 找回密码
     */
    public void resetPwd(FindPwdParam param) {
        if (StringUtils.isBlank(param.getLoginName())) {
            throw new ApiException("用户名不能为空");
        }
        if (StringUtils.isBlank(param.getNewPassword())) {
            throw new ApiException("新密码不能为空");
        }
        if (StringUtils.isBlank(param.getConfirmPassword())) {
            throw new ApiException("重复密码不能为空");
        }
        final User user = accountService.checkLoginName(param.getLoginName());
        if (user == null) {
            throw new ApiException("用户名不存在");
        }
        if (!"888888".equals(param.getVerifyCode())) {
            String verifyCode = jedisService.get(CacheKey.VERIFY_CODE_KEY + param.getLoginName());
            if (verifyCode == null) {
                throw new ApiException("验证码已过期");
            } else if (!verifyCode.equals(param.getVerifyCode())) {
                throw new ApiException("验证码错误");
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
            throw new ApiException("手机号或邮箱不能为空");
        }
        if (!(Validator.isEmail(param.getLoginName()) || Validator.isHongKongMobile(param.getLoginName()) || Validator.isMobile(param.getLoginName()))) {
            throw new ApiException("请输入有效的用户名");
        }
        if (StringUtils.isBlank(param.getVerifyCode())) {
            throw new ApiException("验证码不能为空");
        }
        if (StringUtils.isBlank(param.getPassword())) {
            throw new ApiException("密码不能为空");
        }
        if (StringUtils.isBlank(param.getConfirmPassword())) {
            throw new ApiException("重复密码不能为空");
        }
        if (!param.getPassword().equals(param.getConfirmPassword())) {
            throw new ApiException("两次密码输入不一致");
        }
        if (!"888888".equals(param.getVerifyCode())) {
            String verifyCode = jedisService.get(VERIFY_CODE_KEY + param.getLoginName());
            if (verifyCode == null) {
                throw new ApiException("验证码已过期");
            } else if (!verifyCode.equals(param.getVerifyCode())) {
                throw new ApiException("验证码错误");
            }
        }
        User exists = accountService.findUserByLoginName(param.getLoginName());
        if (exists != null) {
            throw new ApiException("用户名已存在");
        }
        //注册用户
        User user = new User();
        user.setLoginName(param.getLoginName());
        user.setPlainPassword(param.getPassword());
        accountService.registerUser(user);
    }

    /**
     * 发送验证码 todo
     */
    public void sendVerifyCode(VerifyCodeParam param) {
        if (StringUtils.isBlank(param.getLoginName())) throw new ApiException("手机号或邮箱不能为空");
        if (param.getType() == null) throw new ApiException("验证码类型不能为空");
        MailTemplateEnum mte = null;
        //注册用户
        if (param.getType().equals(1)) {
            User user = accountService.checkLoginName(param.getLoginName());
            if (user != null) throw new ApiException("用户名已被注册");
            mte = MailTemplateEnum.REG_USER;
        }
        //重置密码
        else if (param.getType().equals(2)) {
            mte = MailTemplateEnum.RESET_PWD;
        } else {
            throw new ApiException("验证码类型非法");
        }
        //验证码
        String verifyCode = RandomNumUtil.getRandNumber(6);
        //发送验证码到邮箱
        if (Validator.isEmail(param.getLoginName())) {
            //验证码有效时间2分钟
            jedisService.setex(VERIFY_CODE_KEY + param.getLoginName(), 2 * 60, verifyCode);
            Map<String, Object> params = new HashMap<>();
            params.put("verifyCode", verifyCode);
            if (!mailService.sendCaptcha(param.getLoginName(), mte, params)) {
                throw new ApiException("验证码发送失败，请联系FreeMan客服");
            }
        }
        //发送验证码到手机
        else if (Validator.isHongKongMobile(param.getLoginName()) || Validator.isMobile(param.getLoginName())) {

        } else {
            throw new ApiException("用户名必须为邮箱或手机号");
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
            throw new ApiException("用户名或错误错误次数过多，请联系FREEMAN客服或找回密码");
        }
        if (StringUtils.isBlank(loginName)) {
            throw new ApiException("用户名不能为空");
        }
        if (StringUtils.isBlank(password)) {
            throw new ApiException("密码不能为空");
        }
        User user = accountService.login(loginName, password);
        if (user == null) {
            this.recordErrorCount(loginName);
            throw new ApiException("用户名或密码错误");
        } else {
            this.cleanErrorCount(loginName);
        }
        String userJson = jedisService.get(CacheKey.USER_ID_KEY + user.getId().toString());
        User history = JSONObject.parseObject(userJson, User.class);
        if (history != null && StringUtils.isNotBlank(history.getToken())) {
            jedisService.delete(CacheKey.TOKEN_KEY + history.getToken());
        }
        UserEntity userEntity = BeanMapper.map(user, UserEntity.class);
        userEntity.setToken(token);
        jedisService.setex(CacheKey.USER_ID_KEY + userEntity.getId().toString(), CONVERSATION_KEEP_TIMEOUT, JSONObject.toJSONString(userEntity));
        jedisService.setex(CacheKey.TOKEN_KEY + token, CONVERSATION_KEEP_TIMEOUT, JSONObject.toJSONString(userEntity));
    }

    public UserInfo profile(String token) {
        String userJson = jedisService.get(CacheKey.TOKEN_KEY + token);
        if (StringUtils.isBlank(userJson)) {
            throw new ApiException("用户未登录");
        }
        UserInfo userInfo = JSONObject.parseObject(userJson, UserInfo.class);
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
        if (userInfo.getId() == null) throw new ApiException("非法参数");
        if (userInfo.getAreaId() == null) throw new ApiException("小区地址不能为空");
        User user = accountService.getUser(userInfo.getId());
        if (user == null) throw new ApiException("用户信息不存在");
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