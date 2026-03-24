package com.gonghak98.v2.file.infrastructure;

import com.gonghak98.v2.file.exception.ExcelFileException;
import com.gonghak98.v2.file.exception.ExcelFileExceptionType;
import java.util.List;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;

@Component
public class ExcelTemplateValidator {

    private final ExcelTemplateProperties properties;
    private final DataFormatter dataFormatter;

    public ExcelTemplateValidator(ExcelTemplateProperties properties) {
        this.properties = properties;
        this.dataFormatter = new DataFormatter();
    }

    public void validate(Sheet workSheet) {
        if (workSheet == null) {
            throw new ExcelFileException(ExcelFileExceptionType.EMPTY_EXCEL_FILE);
        }
        validateTitle(workSheet);
        validateHeaders(workSheet);
    }

    private void validateTitle(Sheet workSheet) {
        Row titleRow = workSheet.getRow(0);
        if (titleRow == null) {
            throw new ExcelFileException(ExcelFileExceptionType.INVALID_STUDENT);
        }
        String actualTitle = dataFormatter.formatCellValue(titleRow.getCell(0));
        if (!properties.getTitle().equals(actualTitle)) {
            throw new ExcelFileException(ExcelFileExceptionType.INVALID_STUDENT);
        }
    }

    private void validateHeaders(Sheet workSheet) {
        Row headerRow = workSheet.getRow(properties.getHeaderRowIndex());
        if (headerRow == null) {
            throw new ExcelFileException(ExcelFileExceptionType.INVALID_STUDENT);
        }

        List<String> defaultHeaders = properties.getDefaultHeaders();
        for (int i = 0; i < defaultHeaders.size(); i++) {
            String expectedHeader = defaultHeaders.get(i);
            String actualHeader = dataFormatter.formatCellValue(headerRow.getCell(i));
            if (!expectedHeader.equals(actualHeader)) {
                throw new ExcelFileException(ExcelFileExceptionType.INVALID_STUDENT);
            }
        }
    }
}
