package com.gonghak98.v2.file.infrastructure;

import com.gonghak98.v2.file.infrastructure.exception.ExcelFileException;
import com.gonghak98.v2.file.service.FileService;
import com.gonghak98.v2.file.service.dto.FileData;
import com.gonghak98.v2.file.service.dto.FileResponse;
import com.gonghak98.v2.report.domain.course.SemesterConst;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ExcelFileService implements FileService {

    private static final int YEAR_COL_NUM = 2;
    private static final int SEMESTER_COL_NUM = 3;
    private static final int COURSE_ID_COL_NUM = 4;
    private static final int COURSE_NAME_COL_NUM = 5;
    private static final int POINT_COL_NUM = 9;

    private final int firstRow;

    public ExcelFileService(@Value("${excel.template.first-row}") int firstRow) {
        this.firstRow = firstRow;
    }

    @Override
    public FileResponse getFileData(MultipartFile file) throws ExcelFileException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        validateExcelFileFormat(file, extension); //업로드 파일 검증

        //엑셀 내용 검증
        Workbook workbook = createWorkbook(file, extension);
        Sheet worksheet = workbook.getSheetAt(0);
        DataFormatter dataFormatter = new DataFormatter();
        validateExcelContent(worksheet, dataFormatter);

        //추출한 데이터를 FileResponse로 변환
        return extractData(worksheet, dataFormatter);
    }

    private FileResponse extractData(Sheet worksheet, DataFormatter dataFormatter) {
        List<FileData> fileDatas = new ArrayList<>();  // 저장할 엔티티 리스트 생성

        for (int i = firstRow; i < worksheet.getPhysicalNumberOfRows(); i++) { //데이터 추출
            Row row = worksheet.getRow(i);

            int year = Integer.parseInt(dataFormatter.formatCellValue(row.getCell(YEAR_COL_NUM - 1))) % 100;
            int semester = SemesterConst.getSemester(dataFormatter.formatCellValue(row.getCell(SEMESTER_COL_NUM - 1))).getValue();

            String parsedCourseId = dataFormatter.formatCellValue(row.getCell(COURSE_ID_COL_NUM - 1));
            int courseId = convertCourseId(parsedCourseId);

            String courseName = dataFormatter.formatCellValue(row.getCell(COURSE_NAME_COL_NUM - 1));

            double point = Double.parseDouble(dataFormatter.formatCellValue(row.getCell(POINT_COL_NUM - 1)));

            fileDatas.add(new FileData(courseId, courseName, year, semester, point));
        }
        return new FileResponse(fileDatas);
    }

    //업로드 파일 검증
    private void validateExcelFileFormat(MultipartFile file, String extension) throws ExcelFileException {
        if (file.isEmpty()) {
            throw new ExcelFileException("파일이 비어 있습니다.");
        }
        if (!extension.equals("xlsx") && !extension.equals("xls")) { //엑셀파일이 아니면
            throw new ExcelFileException("엑셀 파일만 업로드 해주세요.");
        }
    }

    private void validateExcelContent(Sheet workSheet, DataFormatter dataFormatter) throws ExcelFileException {
        if (workSheet == null) {
            throw new ExcelFileException("엑셀파일이 비어있습니다.");
        }
        Row row = workSheet.getRow(0);
        if (row == null) {
            throw new ExcelFileException("엑셀파일이 비어있습니다.");
        }
        String data = dataFormatter.formatCellValue(row.getCell(0));
        if (!data.equals("기이수성적")) {
            throw new ExcelFileException("기이수성적 엑셀파일을 업로드 해주세요.");
        }
    }

    private Workbook createWorkbook(MultipartFile file, String extension) {
        try (InputStream is = file.getInputStream()) {
            if (extension.equals("xlsx")) {
                return new XSSFWorkbook(is);
            } else if (extension.equals("xls")) {
                return new HSSFWorkbook(is);
            }
        } catch (IOException e) {
            throw new ExcelFileException("엑셀 파일 추출 과정에서 오류가 발생했습니다.");
        }
        throw new ExcelFileException("지원하지 않는 엑셀 파일 형식입니다 : " + extension);
    }

    private int convertCourseId(String parsedCourseId) {
        if (!Character.isDigit(parsedCourseId.charAt(0))) {
            if (parsedCourseId.charAt(0) == 'P') {
                parsedCourseId = '0' + parsedCourseId.substring(1);
            } else {
                return 0;
            }
        }
        return Integer.parseInt(parsedCourseId);
    }
}
