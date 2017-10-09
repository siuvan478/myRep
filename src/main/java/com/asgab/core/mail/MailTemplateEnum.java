package com.asgab.core.mail;

public enum MailTemplateEnum {

    REG_USER("regUser.ftl", "[Freeman] Welcome join"),
    RESET_PWD("resetPwd.ftl", "[Freeman] Reset password verify code");

    public String templateName;
    public String title;

    MailTemplateEnum(String templateName, String title) {
        this.templateName = templateName;
        this.title = title;
    }

    public String getTemplateName() {
        return templateName;
    }

    public String getTitle() {
        return title;
    }
}
