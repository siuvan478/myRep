package com.asgab.service;

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