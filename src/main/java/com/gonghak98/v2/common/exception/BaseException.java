package com.gonghak98.v2.common.exception;

public abstract class BaseException extends RuntimeException {

    protected BaseException(String message) {
        super(message);
    }

    protected BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract BaseExceptionType exceptionType();
}
