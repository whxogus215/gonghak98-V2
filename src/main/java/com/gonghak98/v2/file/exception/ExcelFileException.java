package com.gonghak98.v2.file.exception;

import com.gonghak98.v2.common.exception.BaseException;
import com.gonghak98.v2.common.exception.BaseExceptionType;

public class ExcelFileException extends BaseException {

    private final ExcelFileExceptionType exceptionType;

    public ExcelFileException(ExcelFileExceptionType exceptionType) {
        super(exceptionType.errorMessage());
        this.exceptionType = exceptionType;
    }

    public ExcelFileException(ExcelFileExceptionType exceptionType, Throwable cause) {
        super(exceptionType.errorMessage(), cause);
        this.exceptionType = exceptionType;
    }

    @Override
    public BaseExceptionType exceptionType() {
        return exceptionType;
    }
}
