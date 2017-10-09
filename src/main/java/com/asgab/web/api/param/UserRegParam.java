package com.asgab.web.api.param;

import java.io.Serializable;

/**
 * 注册用户参数
 */
public class UserRegParam implements Serializable {
    private static final long serialVersionUID = -8320733747366480833L;

    private String loginName; //用户名
    private String verifyCode; //验证码
    private String password; //密码
    private String confirmPassword; //确认密码

    public UserRegParam() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
