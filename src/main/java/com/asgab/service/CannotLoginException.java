package com.asgab.service;

import org.apache.shiro.authc.AccountException;

public class CannotLoginException extends AccountException {

    public CannotLoginException() {
    }

    public CannotLoginException(String message) {
        super(message);
    }

    public CannotLoginException(Throwable cause) {
        super(cause);
    }

    public CannotLoginException(String message, Throwable cause) {
        super(message, cause);
    }
}
