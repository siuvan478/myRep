package com.asgab.service.api;

import com.asgab.core.mail.MailTemplateEnum;
import com.asgab.entity.User;
import com.asgab.service.ApiException;
import com.asgab.service.JedisService;
import com.asgab.service.MailService;
import com.asgab.service.account.AccountService;
import com.asgab.util.Digests;
import com.asgab.util.JsonMapper;
import com.asgab.util.RandomNumUtil;
import com.asgab.util.Validator;
import com.asgab.web.api.param.FindPwdParam;
import com.asgab.web.api.param.UserRegParam;
import com.asgab.web.api.param.VerifyCodeParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能：
 * 作者：Siuvan(Siuvan@lianj.com)
 * 日期：2017年09月30日 下午 4:06
 * 版权所有：广东联结网络技术有限公司 版权所有(C) 2016-2018
 */
@Component
@Transactional
public class UserWebService {

    @Resource
    private MailService mailService;

    @Resource
    private AccountService accountService;

    @Autowired
    private JedisService jedisService;

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
        String verifyCode = jedisService.get(param.getLoginName());
        if (verifyCode == null) {
            throw new ApiException("验证码已过期");
        } else if (!verifyCode.equals(param.getVerifyCode())) {
            throw new ApiException("验证码错误");
        } else {
            //更新密码
            user.setPlainPassword(param.getNewPassword());
            accountService.updateUser(user);
        }
    }

    /**
     * 注册用户
     */
    public void register(UserRegParam param) {
        if (StringUtils.isBlank(param.getLoginName())) {
            throw new ApiException("手机号或邮箱不能为空");
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

        String verifyCode = jedisService.get(param.getLoginName());
        if (verifyCode == null) {
            throw new ApiException("验证码已过期");
        } else if (!verifyCode.equals(param.getVerifyCode())) {
            throw new ApiException("验证码错误");
        } else {
            //注册用户
            User user = new User();
            user.setLoginName(param.getLoginName());
            user.setPlainPassword(param.getPassword());
            accountService.registerUser(user);
        }
    }

    /**
     * 发送验证码 todo
     */
    public void sendVerifyCode(VerifyCodeParam param) {
        if (StringUtils.isBlank(param.getLoginName())) {
            throw new ApiException("手机号或邮箱不能为空");
        }
        if (param.getType() == null) {
            throw new ApiException("验证码类型不能为空");
        }
        MailTemplateEnum mte = null;
        //注册用户
        if (param.getType().equals(1)) {
            User user = accountService.checkLoginName(param.getLoginName());
            if (user != null) {
                throw new ApiException("用户名已被注册");
            }
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
            jedisService.setex(param.getLoginName(), 2 * 60, verifyCode);
            Map<String, Object> params = new HashMap<>();
            params.put("verifyCode", verifyCode);
            if (!mailService.sendCaptcha(param.getLoginName(), mte, params)) {
                throw new ApiException("验证码发送失败，请联系FreeMan客服");
            }
        }
        //发送验证码到手机
        else if (Validator.isHongKongMobile(param.getLoginName()) || Validator.isMobile(param.getLoginName())) {

        }
    }

    /**
     * 用户登录
     *
     * @param loginName
     * @param password
     */
    public void login(String loginName, String password, String token) {
        if (StringUtils.isBlank(loginName)) {
            throw new ApiException("用户名不能为空");
        }
        if (StringUtils.isBlank(password)) {
            throw new ApiException("密码不能为空");
        }
        User user = accountService.login(loginName, password);
        if (user == null) {
            throw new ApiException("用户名或密码错误");
        }
        byte[] hashUserKey = Digests.sha1(user.getId().toString().getBytes(), "freeman".getBytes(), 1024);
        String userKey = "";
        jedisService.setex(token, CONVERSATION_KEEP_TIMEOUT, JsonMapper.nonEmptyMapper().toJson(user));

    }

    private static int CONVERSATION_KEEP_TIMEOUT = 60 * 60 * 24 * 15;
}