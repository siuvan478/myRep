package com.asgab.service.api;

import com.asgab.core.mail.MailTemplateEnum;
import com.asgab.entity.User;
import com.asgab.service.ApiException;
import com.asgab.service.EhCacheService;
import com.asgab.service.MailService;
import com.asgab.service.account.AccountService;
import com.asgab.util.RandomNumUtil;
import com.asgab.web.api.param.UserParam;
import org.apache.commons.lang3.StringUtils;
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
    private EhCacheService ehCacheService;

    @Resource
    private AccountService accountService;


    /**
     * 找回密码
     */
    public void findPassword(UserParam userParam) {
        final User user = accountService.findUserByLoginName(userParam.getLoginName());
        user.setPlainPassword(userParam.getNewPassword());
        accountService.updateUser(user);
    }

    /**
     * 校验验证码是否正确
     */
    public boolean checkVerifyCode(UserParam userParam) {
        if (StringUtils.isBlank(userParam.getLoginName())) {
            throw new ApiException("用户名不存在");
        }
        if (userParam.getFindMode() == null || (userParam.getFindMode().equals(1) &&
                userParam.getFindMode().equals(2))) {
            throw new ApiException("找回类型不合法");
        }
        final User user = accountService.findUserByLoginName(userParam.getLoginName());
        if (user == null) {
            throw new ApiException("用户名不存在");
        }
        boolean validFlag = false;
        //邮箱验证码
        if (userParam.getFindMode().equals(1)) {
            Object verifyCode = ehCacheService.get(user.getEmail());
            if (verifyCode == null) {
                throw new ApiException("验证码不正确");
            }
            validFlag = userParam.getVerifyCode().equals(verifyCode.toString());
        }
        return validFlag;
    }

    /**
     * 找回密码发送验证码
     */
    public void sendVerifyCode(UserParam userParam) throws ApiException {
        if (StringUtils.isBlank(userParam.getLoginName())) {
            throw new ApiException("用户名不存在");
        }
        if (userParam.getFindMode() == null || (userParam.getFindMode().equals(1) &&
                userParam.getFindMode().equals(2))) {
            throw new ApiException("找回类型不合法");
        }
        final User user = accountService.findUserByLoginName(userParam.getLoginName());
        if (user == null) {
            throw new ApiException("用户名不存在");
        }
        //邮箱验证码
        if (userParam.getFindMode().equals(1)) {
            if (StringUtils.isBlank(user.getEmail())) {
                throw new ApiException("未设置用户邮箱");
            }
            Map<String, Object> params = new HashMap<>();
            params.put("verifyCode", RandomNumUtil.getRandNumber(6));
            if (!mailService.sendCaptcha(user.getEmail(), MailTemplateEnum.RESET_PWD, params)) {
                throw new ApiException("验证码发送失败，请联系Freeman客服");
            }
            System.out.println(ehCacheService.get(user.getEmail()));
        }
    }
}