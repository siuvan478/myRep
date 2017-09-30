package com.asgab.service;

/**
 * 功能：
 * 作者：Siuvan(Siuvan@lianj.com)
 * 日期：2017年09月30日 下午 3:59
 * 版权所有：广东联结网络技术有限公司 版权所有(C) 2016-2018
 */
public class ApiException extends RuntimeException {

    private static final long serialVersionUID = -1241286664420051279L;
    private Integer errorCode;
    private String message;
    private Throwable throwable;

    public ApiException() {
    }

    public ApiException(String message) {
        this.message = message;
        this.errorCode = 414;
    }

    public ApiException(int errorCode, Throwable throwable) {
        this.errorCode = Integer.valueOf(errorCode);
        this.throwable = throwable;
    }

    public ApiException(String message, Throwable throwable) {
        this.message = message;
        this.throwable = throwable;
    }

    public ApiException(int errorCode, String message) {
        this.message = message;
        this.errorCode = Integer.valueOf(errorCode);
    }

    public Integer getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Throwable getThrowable() {
        return this.throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}