package com.gonghak98.v2.file.exception;

import com.gonghak98.v2.common.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum ExcelFileExceptionType implements BaseExceptionType {

    INVALID_EXCEL_FILE_TYPE(HttpStatus.BAD_REQUEST, "올바른 기이수 성적 파일을 업로드해주세요."),
    EMPTY_EXCEL_FILE(HttpStatus.BAD_REQUEST, "기이수 성적 파일을 업로드해주세요."),
    RETRY_EXCEL_FILE(HttpStatus.BAD_REQUEST, "업로드 과정에서 오류가 발생했습니다. 다시 시도해주세요."),
    EXCEED_EXCEL_FILE_SIZE(HttpStatus.PAYLOAD_TOO_LARGE, "업로드 파일 크기는 최대 50KB를 초과할 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String errorMessage;

    ExcelFileExceptionType(HttpStatus httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    @Override
    public HttpStatus httpStatus() {
        return httpStatus;
    }

    @Override
    public String errorCode() {
        return this.name();
    }

    @Override
    public String errorMessage() {
        return errorMessage;
    }
}
