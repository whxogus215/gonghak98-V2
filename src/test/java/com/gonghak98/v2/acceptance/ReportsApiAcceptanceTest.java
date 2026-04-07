package com.gonghak98.v2.acceptance;

import static com.gonghak98.v2.utils.FileUtils.인수테스트_업로드_파일_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.documentationConfiguration;

import io.restassured.RestAssured;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.Filter;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;
import io.restassured.specification.RequestSpecification;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;

@DisplayName("POST /api/reports")
@ActiveProfiles("acceptance")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith(RestDocumentationExtension.class)
class ReportsApiAcceptanceTest {

    @LocalServerPort
    private int port;

    private RequestSpecification spec;

    private MultiPartSpecification department;
    private MultiPartSpecification entranceYear;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        final Filter docConfig = documentationConfiguration(restDocumentation)
            .operationPreprocessors()
            .withResponseDefaults(prettyPrint());
        this.spec = new RequestSpecBuilder()
            .setPort(port)
            .addFilter(docConfig)
            .build();

        this.department = new MultiPartSpecBuilder("전자정보통신공학과")
            .controlName("departmentName")
            .charset("UTF-8").build();

        this.entranceYear = new MultiPartSpecBuilder("2025")
            .controlName("entranceYear")
            .charset("UTF-8").build();
    }

    @Nested
    class 기이수_성적파일_업로드_성공 {

        @Test
        @DisplayName("200 OK와 영역별 검사 결과가 반환된다.")
        void 성적_업로드_성공_테스트() throws IOException {
            //when
            final Response response = RestAssured.given(spec)
                                                 .filter(document("create-report", requestParts(
                                                     partWithName("departmentName").description("학과명 (예: 전자정보통신공학과)"),
                                                     partWithName("entranceYear").description("입학년도 (예: 2026)"),
                                                     partWithName("file").description("기이수 성적 파일 (.xlsx)")
                                                 )))
                                                 .multiPart(department)
                                                 .multiPart(entranceYear)
                                                 .multiPart("file", 인수테스트_업로드_파일_생성("/file/acceptance/졸업생_기이수성적조회.xlsx"))
                                                 .when().log().all()
                                                 .post("/api/reports")
                                                 .then().log().all()
                                                 .extract().response();

            //then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        }
    }

    @Nested
    class 기이수_성적파일_업로드_실패 {

        @Test
        @DisplayName("400 BadRequest와 에러 응답이 반환된다.")
        void 성적_업로드_예외_테스트() throws IOException {
            //when
            final Response response = RestAssured.given(spec)
                                                 .filter(document("create-fail-report", responseFields(
                                                     fieldWithPath("httpStatusCode").description("HTTP 상태 코드"),
                                                     fieldWithPath("errorCode").description("에러 코드"),
                                                     fieldWithPath("errorMessage").description("에러 메시지"))
                                                 ))
                                                 .multiPart(department)
                                                 .multiPart(entranceYear)
                                                 .multiPart("file", 인수테스트_업로드_파일_생성("/file/수강신청내역조회.xlsx"))
                                                 .when()
                                                 .post("/api/reports")
                                                 .then().log().all()
                                                 .extract().response();

            //then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }
    }
}
