package com.asgab.web.api.param;

import java.io.Serializable;

/**
 * 功能：
 * 作者：Siuvan(Siuvan@lianj.com)
 * 日期：2017年09月30日 下午 3:50
 * 版权所有：广东联结网络技术有限公司 版权所有(C) 2016-2018
 */
public class UserParam implements Serializable {
    private static final long serialVersionUID = 3774683785421118868L;

    private String loginName; //用户名
    private String verifyCode; //验证码
    private String newPassword; //新密码
    private String confirmPassword; //确认密码
    private Integer findMode;//找回方式 1=邮箱 2=手机

    public UserParam() {
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Integer getFindMode() {
        return findMode;
    }

    public void setFindMode(Integer findMode) {
        this.findMode = findMode;
    }
}