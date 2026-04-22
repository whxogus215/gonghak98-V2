package com.gonghak98.v2.common.exception;

import com.gonghak98.v2.file.exception.ExcelFileExceptionType;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ExceptionResponse> handleBaseException(BaseException exception) {
        final BaseExceptionType exceptionType = exception.exceptionType();
        return ResponseEntity.status(exceptionType.httpStatus())
                             .body(new ExceptionResponse(exceptionType.httpStatus().value(),
                                                         exceptionType.errorCode(),
                                                         exceptionType.errorMessage()));
    }

    @ExceptionHandler(FileSizeLimitExceededException.class)
    public ResponseEntity<ExceptionResponse> handleFileSizeLimitExceededException(FileSizeLimitExceededException exception) {
        final BaseExceptionType exceptionType = ExcelFileExceptionType.EXCEED_EXCEL_FILE_SIZE;
        return ResponseEntity.status(exceptionType.httpStatus())
                             .body(new ExceptionResponse(exceptionType.httpStatus().value(),
                                                         exceptionType.errorCode(),
                                                         exceptionType.errorMessage()));
    }

    @ExceptionHandler(TypeMismatchException.class)
    public ResponseEntity<ExceptionResponse> handleMissingParams(TypeMismatchException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(new ExceptionResponse(HttpStatus.BAD_REQUEST.value(),
                                                         "TYPE_MISMATCH",
                                                         "요청이 올바른 형식이 아닙니다."));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception exception) {
        final BaseExceptionType exceptionType = CommonExceptionType.SERVER_ERROR;
        return ResponseEntity.status(exceptionType.httpStatus())
                             .body(new ExceptionResponse(exceptionType.httpStatus().value(),
                                                         exceptionType.errorCode(),
                                                         exceptionType.errorMessage()));
    }
}
