package com.asgab.web.api;

import java.io.Serializable;

/**
 * 功能：API Response
 * 作者：Siuvan(Siuvan@lianj.com)
 * 日期：2017年09月30日 下午 4:37
 * 版权所有：广东联结网络技术有限公司 版权所有(C) 2016-2018
 */
public class ApiResponse<T> implements Serializable {
    private static final long serialVersionUID = 3874592454624623318L;

    private Integer code;
    private String message = "";
    private T data;

    public ApiResponse() {
    }

    public ApiResponse(T data) {
        this.data = data;
        this.code = 200;
    }

    public ApiResponse(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public ApiResponse(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}