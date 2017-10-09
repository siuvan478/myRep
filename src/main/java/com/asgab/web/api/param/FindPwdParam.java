package com.asgab.web.api.param;

import java.io.Serializable;

/**
 * 找回密码参数
 */
public class FindPwdParam implements Serializable {
    private static final long serialVersionUID = 3774683785421118868L;

    private String loginName; //用户名
    private String verifyCode; //验证码
    private String newPassword; //新密码
    private String confirmPassword; //确认密码

    public FindPwdParam() {
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
}