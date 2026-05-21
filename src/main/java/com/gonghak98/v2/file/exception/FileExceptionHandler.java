package com.gonghak98.v2.file.exception;

import com.gonghak98.v2.common.exception.BaseExceptionType;
import com.gonghak98.v2.common.exception.ExceptionResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class FileExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ExceptionResponse> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException exception) {
        final BaseExceptionType exceptionType = ExcelFileExceptionType.EXCEED_EXCEL_FILE_SIZE;
        return ResponseEntity.status(exceptionType.httpStatus())
                             .body(new ExceptionResponse(exceptionType.httpStatus().value(),
                                                         exceptionType.errorCode(),
                                                         exceptionType.errorMessage()));
    }
}
