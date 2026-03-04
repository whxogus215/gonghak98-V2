package com.gonghak98.v2.file.exception;

import com.gonghak98.v2.common.exception.BaseException;
import com.gonghak98.v2.common.exception.BaseExceptionType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExcelFileException extends BaseException {

    private final ExcelFileExceptionType exceptionType;

    @Override
    public BaseExceptionType exceptionType() {
        return exceptionType;
    }
}
