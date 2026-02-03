package com.gonghak98.v2.certification.acceptance.electricinformation;

import static com.gonghak98.v2.certification.common.CertificationApiRequest.ABEEK_결과_조회_요청;
import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class MajorCertificationAcceptanceTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Nested
    @DisplayName("실험 교과목 조건 시나리오")
    class LabCourseScenario {

        @ParameterizedTest
        @CsvSource({
            "5611, '디지털논리회로', 2025, 1",
            "9658, '전기회로실험', 2025, 2",
            "8076, '기초광학및실험', 2025, 1",
            "9661, '전자소자공정실험', 2025, 1",
            "9666, '마이크로컴퓨터실험', 2025, 1"
        })
        @DisplayName("이수한 실험 과목을 1개 제출 시, 전공 영역의 인정학점, 최소이수학점, 통과여부를 조회할 수 있다.")
        void 전공영역의_인정학점_최소이수학점_이수여부_조회(int courseId, String courseName, int year, int semester) {
            //given
            final String requestBody = createRequestBody(courseId, courseName, year, semester);

            //when
            final ExtractableResponse<Response> response = ABEEK_결과_조회_요청(requestBody);

            //then
            final JsonPath result = response.jsonPath();
            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            assertThat(result.getDouble("major.userPoint")).isEqualTo(3.0);
            assertThat(result.getDouble("major.minPoint")).isEqualTo(45);
            assertThat(result.getBoolean("major.isPassed")).isFalse();
        }

        @ParameterizedTest
        @CsvSource({
            "5611, '디지털논리회로', 2025, 1",
            "9658, '전기회로실험', 2025, 2",
            "8076, '기초광학및실험', 2025, 1",
            "9661, '전자소자공정실험', 2025, 1",
            "9666, '마이크로컴퓨터실험', 2025, 1"
        })
        @DisplayName("이수한 실험 과목을 1개 제출 시, 전공 영역의 실험 교과목 조건의 통과여부 및 상세내용을 조회할 수 있다.")
        void 전공영역의_실험교과목조건_통과여부_상세내용_조회(int courseId, String courseName, int year, int semester) {
            //given
            final String requestBody = createRequestBody(courseId, courseName, year, semester);

            //when
            final ExtractableResponse<Response> response = ABEEK_결과_조회_요청(requestBody);

            //then
            final JsonPath result = response.jsonPath();
            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            assertThat(result.getBoolean("major.labCourse.isPassed")).isTrue();
            assertThat(result.getString("major.labCourse.description")).isEqualTo("실험교과목을 1과목 이상 이수");
            assertThat(result.getString("major.labCourse.courses"))
                .isEqualTo("디지털논리회로, 전기회로실험, 기초광학및실험, 전자소자공정실험, 마이크로컴퓨터실험");
        }

        private String createRequestBody(int courseId, String courseName, int year, int semester) {
            return String.format("""
                                     [
                                        { "courseId": %d, "courseName": %s, "year": %d, "semester": %d }
                                     ]
                                 """, courseId, courseName, year, semester);
        }
    }
}
