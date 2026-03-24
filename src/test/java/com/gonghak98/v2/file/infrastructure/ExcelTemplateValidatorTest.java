package com.gonghak98.v2.file.infrastructure;

import static com.gonghak98.v2.utils.FileUtils.업로드_파일_생성;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.gonghak98.v2.file.exception.ExcelFileException;
import com.gonghak98.v2.file.exception.ExcelFileExceptionType;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(ExcelTemplateProperties.class)
@ContextConfiguration(classes = {ExcelTemplateValidator.class}, initializers = ConfigDataApplicationContextInitializer.class)
public class ExcelTemplateValidatorTest {

    @Autowired
    private ExcelTemplateValidator validator;

    @Test
    @DisplayName("세종대학교 기이수성적표 양식과 일치할 경우, 검증에 성공한다.")
    void validateSuccessTest() throws IOException {
        //given
        MockMultipartFile testFile = 업로드_파일_생성("file/기이수성적조회.xlsx");
        Sheet workSheet = new XSSFWorkbook(testFile.getInputStream()).getSheetAt(0);

        //when & then
        assertThatCode(() -> validator.validate(workSheet)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("세종대학교 기이수성적표 양식(제목)과 다를 경우, 검증에 실패한다.")
    void validateFailTest1() throws IOException {
        //given
        MockMultipartFile testFile = 업로드_파일_생성("file/제목이_다른_성적표.xlsx");
        Sheet workSheet = new XSSFWorkbook(testFile.getInputStream()).getSheetAt(0);

        //when & then
        assertThatThrownBy(() -> validator.validate(workSheet))
            .isInstanceOf(ExcelFileException.class)
            .extracting("exceptionType")
            .isEqualTo(ExcelFileExceptionType.INVALID_STUDENT);
    }

    @Test
    @DisplayName("세종대학교 기이수성적표 양식(라벨)과 다를 경우, 검증에 실패한다.")
    void validateFailTest2() throws IOException {
        //given
        MockMultipartFile testFile = 업로드_파일_생성("file/라벨순서가_다른_성적표.xlsx");
        Sheet workSheet = new XSSFWorkbook(testFile.getInputStream()).getSheetAt(0);

        //when & then
        assertThatThrownBy(() -> validator.validate(workSheet))
            .isInstanceOf(ExcelFileException.class)
            .extracting("exceptionType")
            .isEqualTo(ExcelFileExceptionType.INVALID_STUDENT);
    }
}
