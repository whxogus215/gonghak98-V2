package com.gonghak98.v2.common.exception;

import org.springframework.http.HttpStatus;

public interface BaseExceptionType {

    HttpStatus httpStatus();

    String errorCode();

    String errorMessage();
}
