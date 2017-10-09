package com.asgab.web.api.param;

import java.io.Serializable;

/**
 * 获取验证码参数
 */
public class VerifyCodeParam implements Serializable {
    private static final long serialVersionUID = 5168372083672098534L;

    private String loginName;//登录名
    private Integer type; //验证码类型 1=注册 2=找回密码

    public VerifyCodeParam() {
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
