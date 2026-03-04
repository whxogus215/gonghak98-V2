package com.gonghak98.v2.common.exception;

public record ExceptionResponse(int httpStatusCode,
                                String errorCode,
                                String errorMessage) {

}
