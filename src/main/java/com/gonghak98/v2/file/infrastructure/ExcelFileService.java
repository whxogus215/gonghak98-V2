package com.gonghak98.v2.file.infrastructure;

import com.gonghak98.v2.file.exception.ExcelFileException;
import com.gonghak98.v2.file.exception.ExcelFileExceptionType;
import com.gonghak98.v2.file.service.FileService;
import com.gonghak98.v2.file.service.dto.FileData;
import com.gonghak98.v2.file.service.dto.FileResponse;
import com.gonghak98.v2.report.domain.course.SemesterConst;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ooxml.POIXMLException;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tika.Tika;
import org.apache.tika.io.TikaInputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class ExcelFileService implements FileService {

    private static final long MAX_FILE_SIZE = 30L * 1024;

    private static final int YEAR_COL_NUM = 2;
    private static final int SEMESTER_COL_NUM = 3;
    private static final int COURSE_ID_COL_NUM = 4;
    private static final int COURSE_NAME_COL_NUM = 5;
    private static final int POINT_COL_NUM = 9;

    private final int firstRow;
    private final ExcelTemplateValidator templateValidator;

    public ExcelFileService(@Value("${excel.template.first-row}") int firstRow,
                            ExcelTemplateValidator templateValidator) {
        this.firstRow = firstRow;
        this.templateValidator = templateValidator;
    }

    @Override
    public FileResponse getFileData(MultipartFile file) throws ExcelFileException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        validateExcelFileFormat(file, extension); //업로드 파일 검증

        //엑셀 내용 검증
        Sheet worksheet = createWorkSheet(file);
        templateValidator.validate(worksheet);

        //추출한 데이터를 FileResponse로 변환
        DataFormatter dataFormatter = new DataFormatter();
        return extractData(worksheet, dataFormatter);
    }

    //업로드 파일 검증

    private void validateExcelFileFormat(MultipartFile file, String extension) throws ExcelFileException {
        if (file.isEmpty()) {
            throw new ExcelFileException(ExcelFileExceptionType.EMPTY_EXCEL_FILE);
        }
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new ExcelFileException(ExcelFileExceptionType.EXCEED_EXCEL_FILE_SIZE);
        }
        if (!extension.equals("xlsx")) { //엑셀파일이 아닐 때
            throw new ExcelFileException(ExcelFileExceptionType.INVALID_EXCEL_FILE_TYPE);
        }

        try (InputStream is = file.getInputStream()) {
            Tika tika = new Tika();

            String mimeType;
            try (TikaInputStream tis = TikaInputStream.get(is)) {
                mimeType = tika.detect(tis);
            }
            if (!mimeType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                && !mimeType.equals("application/x-tika-ooxml")) {
                throw new ExcelFileException(ExcelFileExceptionType.INVALID_EXCEL_FILE_TYPE);
            }
        } catch (IOException e) {
            throw new ExcelFileException(ExcelFileExceptionType.RETRY_EXCEL_FILE);
        }
    }

    private Sheet createWorkSheet(MultipartFile file) {
        ZipSecureFile.setMinInflateRatio(0.01d);
        ZipSecureFile.setMaxEntrySize(10L * 1024 * 1024); // 해당 값은 MAX_FILE_SIZE와 최대 압축률의 값을 고려해서 설정해야 합니다.
        ZipSecureFile.setMaxTextSize(1_000_000L);

        try (InputStream is = file.getInputStream()) {
            return new XSSFWorkbook(is).getSheetAt(0);
        } catch (IOException e) {
            throw new ExcelFileException(ExcelFileExceptionType.RETRY_EXCEL_FILE);
        } catch (POIXMLException e) {
            throw new ExcelFileException(ExcelFileExceptionType.INVALID_EXCEL_FILE_TYPE);
        } catch (Exception e) {
            log.info("에러 메시지 {}", e.getMessage());
            // TODO 추후 로그 남기기
            throw new ExcelFileException(ExcelFileExceptionType.INVALID_EXCEL_FILE_TYPE);
        }
    }

    private FileResponse extractData(Sheet worksheet, DataFormatter dataFormatter) {
        List<FileData> fileDatas = new ArrayList<>();  // 저장할 엔티티 리스트 생성

        for (int i = firstRow; i < worksheet.getPhysicalNumberOfRows(); i++) { //데이터 추출
            Row row = worksheet.getRow(i);

            int year = Integer.parseInt(dataFormatter.formatCellValue(row.getCell(YEAR_COL_NUM - 1))) % 100;
            int semester = SemesterConst.getSemester(dataFormatter.formatCellValue(row.getCell(SEMESTER_COL_NUM - 1))).getValue();

            String courseCode = dataFormatter.formatCellValue(row.getCell(COURSE_ID_COL_NUM - 1));

            String courseName = dataFormatter.formatCellValue(row.getCell(COURSE_NAME_COL_NUM - 1));

            double point = Double.parseDouble(dataFormatter.formatCellValue(row.getCell(POINT_COL_NUM - 1)));

            fileDatas.add(new FileData(courseCode, courseName, year, semester, point));
        }
        return new FileResponse(fileDatas);
    }
}
