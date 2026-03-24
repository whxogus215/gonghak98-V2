package com.gonghak98.v2.file.infrastructure;

import static com.gonghak98.v2.utils.FileUtils.업로드_파일_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.gonghak98.v2.file.exception.ExcelFileException;
import com.gonghak98.v2.file.exception.ExcelFileExceptionType;
import com.gonghak98.v2.file.service.FileService;
import com.gonghak98.v2.file.service.dto.FileResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
class ExcelFileServiceTest {

    private static final int TEST_FILE_ROW_SIZE = 31;
    private ExcelTemplateValidator mockValidator;
    private FileService fileService;

    @BeforeEach
    void setUp() {
        int firstRow = 4;
        mockValidator = Mockito.mock(ExcelTemplateValidator.class);
        fileService = new ExcelFileService(firstRow, mockValidator);
    }

    @Test
    @DisplayName("사용자가 업로드한 파일의 확장자가 엑셀(xlsx, xls)이 아니면 예외가 발생한다.")
    void fileServiceTest() {
        //given
        MockMultipartFile testFile = new MockMultipartFile("테스트", "테스트".getBytes());

        //when & then
        assertThatThrownBy(() -> fileService.getFileData(testFile))
            .isInstanceOf(ExcelFileException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"xlsx", "xls"})
    @DisplayName("사용자가 비어있는 파일을 업로드하면 예외가 발생한다.")
    void fileServiceTest2(final String extension) {
        //given
        MockMultipartFile testFile = new MockMultipartFile("테스트." + extension, new byte[0]);

        //when & then
        assertThatThrownBy(() -> fileService.getFileData(testFile))
            .isInstanceOf(ExcelFileException.class);
    }

    @Test
    @DisplayName("사용자가 확장자만 맞게 수정하고, 잘못된 파일을 업로드하면, 예외가 발생한다.")
    void fileServiceTest3() {
        //given
        MockMultipartFile testFile = 업로드_파일_생성("file/PDF샘플자료.xlsx");

        //when & then
        assertThatThrownBy(() -> fileService.getFileData(testFile))
            .isInstanceOf(ExcelFileException.class);
    }

    @Test
    @DisplayName("30KB 이하의 파일을 업로드하면, 예외가 발생하지 않는다.")
    void fileServiceTest4() {
        //given
        MockMultipartFile testFile = 업로드_파일_생성("file/기이수성적조회_200과목이수.xlsx");

        //when & then
        assertThatCode(() -> fileService.getFileData(testFile))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("30KB가 초과하는 파일을 업로드하면, 예외가 발생한다.")
    void fileServiceTest5() {
        //given
        MockMultipartFile testFile = 업로드_파일_생성("file/30KB_초과_샘플.xlsx");

        //when & then
        assertThatThrownBy(() -> fileService.getFileData(testFile))
            .isInstanceOf(ExcelFileException.class)
            .extracting("exceptionType")
            .isEqualTo(ExcelFileExceptionType.EXCEED_EXCEL_FILE_SIZE);
    }

    @Test
    @DisplayName("사용자가 올바른 기이수 성적파일을 업로드하면, 예외가 발생하지 않는다.")
    void validateWorkbookTest1() {
        //given
        MockMultipartFile testFile = 업로드_파일_생성("file/기이수성적조회.xlsx");

        //when & then
        assertThatCode(() -> fileService.getFileData(testFile))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("사용자가 잘못된 기이수 성적파일을 업로드하면, 예외가 발생한다.")
    void validateWorkbookTest2() {
        //given
        MockMultipartFile testFile = 업로드_파일_생성("file/수강신청내역조회.xlsx");

        //when & then
        assertThatThrownBy(() -> fileService.getFileData(testFile))
            .isInstanceOf(ExcelFileException.class);
    }

    @Test
    @DisplayName("파일에서 데이터를 가져와서 과목정보를 갖는 DTO를 생성한다.")
    void getUserCoursesFromFileTest() {
        //given
        MockMultipartFile testFile = 업로드_파일_생성("file/기이수성적조회.xlsx");

        //when
        FileResponse response = fileService.getFileData(testFile);

        //then
        assertThat(response.fileDatas()).hasSize(TEST_FILE_ROW_SIZE);
        assertThat(response.fileDatas()).allSatisfy(data -> {
            assertThat(data.courseId()).isNotZero();
            assertThat(data.semester()).isNotZero();
            assertThat(data.year()).isNotZero();
            assertThat(data.point()).isNotZero();
        });
    }
}
