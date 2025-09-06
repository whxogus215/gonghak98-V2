package com.gonghak98.v2.file.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.gonghak98.v2.file.infrastructure.exception.ExcelFileException;
import com.gonghak98.v2.file.service.FileService;
import com.gonghak98.v2.file.service.dto.FileResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
class ExcelFileServiceTest {

    private static final int TEST_FILE_ROW_SIZE = 31;
    private FileService fileService;

    @BeforeEach
    void setUp() {
        int firstRow = 4;
        fileService = new ExcelFileService(firstRow);
    }

    @Test
    @DisplayName("사용자가 업로드한 파일의 확장자가 엑셀(xlsx, xls)이 아니면 예외가 발생한다.")
    void fileServiceTest() {
        //given
        MockMultipartFile testFile = new MockMultipartFile("테스트", "테스트".getBytes());

        //when & then
        assertThatThrownBy(() -> fileService.getFileData(testFile))
            .isInstanceOf(ExcelFileException.class)
            .hasMessageStartingWith("엑셀 파일만 업로드 해주세요");
    }

    @ParameterizedTest
    @ValueSource(strings = {"xlsx", "xls"})
    @DisplayName("사용자가 비어있는 파일을 업로드하면 예외가 발생한다.")
    void fileServiceTest2(final String extension) {
        //given
        MockMultipartFile testFile = new MockMultipartFile("테스트." + extension, new byte[0]);

        //when & then
        assertThatThrownBy(() -> fileService.getFileData(testFile))
            .isInstanceOf(ExcelFileException.class)
            .hasMessageStartingWith("파일이 비어 있습니다");
    }

    @Test
    @DisplayName("사용자가 올바른 기이수 성적파일을 업로드하면, 예외가 발생하지 않는다.")
    void validateWorkbookTest1() throws IOException {
        //given
        MockMultipartFile testFile = 업로드_파일_생성("src/test/resources/file/기이수성적조회.xlsx");

        //when & then
        assertThatCode(() -> fileService.getFileData(testFile))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("사용자가 잘못된 기이수 성적파일을 업로드하면, 예외가 발생한다.")
    void validateWorkbookTest2() throws IOException {
        //given
        MockMultipartFile testFile = 업로드_파일_생성("src/test/resources/file/수강신청내역조회.xlsx");

        //when & then
        assertThatThrownBy(() -> fileService.getFileData(testFile))
            .isInstanceOf(ExcelFileException.class)
            .hasMessageStartingWith("기이수성적 엑셀파일을 업로드 해주세요");
    }

    @Test
    @DisplayName("파일에서 데이터를 가져와서 과목정보를 갖는 DTO를 생성한다.")
    void getUserCoursesFromFileTest() throws IOException {
        //given
        MockMultipartFile testFile = 업로드_파일_생성("src/test/resources/file/기이수성적조회.xlsx");

        //when
        FileResponse response = fileService.getFileData(testFile);

        //then
        assertThat(response.fileDatas()).hasSize(TEST_FILE_ROW_SIZE);
        assertThat(response.fileDatas()).allSatisfy(data -> {
            assertThat(data.courseId()).isNotZero();
            assertThat(data.semester()).isNotZero();
            assertThat(data.year()).isNotZero();
        });
    }

    private static MockMultipartFile 업로드_파일_생성(String filePath) throws IOException {
        String fileName = "기이수성적조회";
        File file = new File(filePath);
        return new MockMultipartFile(fileName, file.getName(), "xlsx", new FileInputStream(file));
    }
}
