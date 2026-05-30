package com.gonghak98.v2.audit.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.gonghak98.v2.audit.application.QualificationAuditRepository;
import com.gonghak98.v2.audit.domain.constant.AbeekType;
import com.gonghak98.v2.audit.domain.dto.AuditCompletedCourse;
import com.gonghak98.v2.audit.domain.dto.QualificationResult;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class DepartmentQualificationAuditTest {

    @Autowired
    private QualificationAuditRepository qualificationAuditRepository;

    @Nested
    class 항공우주공학과 {

        private final String departmentName = "항공우주공학과";

        @Nested
        class ProGyoyang {

            @Test
            @DisplayName("2020년도 전문교양 - 필수 과목을 모두 이수하면 검사에 통과한다.")
            void proGyoyangTest2020() {
                // given
                short entranceYear = 2020;
                QualificationAudit qualificationAudit = qualificationAuditRepository.findQualificationAudit(departmentName, entranceYear);

                List<AuditCompletedCourse> studentCourses = List.of(
                    AuditCompletedCourse.builder().code("010352").name("English Listening Practice 1").credit(2.0).year(entranceYear).semester(1)
                                        .abeekType(AbeekType.GYOYANG).build(),
                    AuditCompletedCourse.builder().code("010354").name("English Reading Practice 1").credit(2.0).year(entranceYear).semester(2)
                                        .abeekType(AbeekType.GYOYANG).build(),
                    AuditCompletedCourse.builder().code("009067").name("문제해결을 위한 글쓰기와 발표").credit(3.0).year(entranceYear + 1).semester(1)
                                        .abeekType(AbeekType.GYOYANG).build(),
                    AuditCompletedCourse.builder().code("009068").name("서양철학:쟁점과 토론").credit(3.0).year(entranceYear + 1).semester(2)
                                        .abeekType(AbeekType.GYOYANG).build(),
                    AuditCompletedCourse.builder().code("009489").name("세계사:인간과문명").credit(3.0).year(entranceYear).semester(1).abeekType(AbeekType.GYOYANG)
                                        .build(),
                    AuditCompletedCourse.builder().code("008364").name("세종사회봉사1").credit(1.0).year(entranceYear).semester(2).abeekType(AbeekType.GYOYANG)
                                        .build()
                );

                // when
                QualificationResult result = qualificationAudit.getQualificationResult(studentCourses);

                // then
                assertThat(result).isNotNull();
                assertThat(result.passResults().get(AbeekType.GYOYANG)).isTrue();
            }

            @Test
            @DisplayName("2021년도 전문교양 - 필수 과목을 모두 이수하면 검사에 통과한다.")
            void proGyoyangTest2021() {
                // given
                short entranceYear = 2021;
                QualificationAudit qualificationAudit = qualificationAuditRepository.findQualificationAudit(departmentName, entranceYear);

                List<AuditCompletedCourse> studentCourses = List.of(
                    AuditCompletedCourse.builder().code("010352").name("English Listening Practice 1").credit(2.0).year(entranceYear).semester(1)
                                        .abeekType(AbeekType.GYOYANG).build(),
                    AuditCompletedCourse.builder().code("010354").name("English Reading Practice 1").credit(2.0).year(entranceYear).semester(2)
                                        .abeekType(AbeekType.GYOYANG).build(),
                    AuditCompletedCourse.builder().code("009067").name("문제해결을 위한 글쓰기와 발표").credit(3.0).year(entranceYear + 1).semester(1)
                                        .abeekType(AbeekType.GYOYANG).build(),
                    AuditCompletedCourse.builder().code("009068").name("서양철학:쟁점과 토론").credit(3.0).year(entranceYear + 1).semester(2)
                                        .abeekType(AbeekType.GYOYANG).build(),
                    AuditCompletedCourse.builder().code("009489").name("세계사:인간과문명").credit(3.0).year(entranceYear).semester(1).abeekType(AbeekType.GYOYANG)
                                        .build(),
                    AuditCompletedCourse.builder().code("011182").name("대학생활과진로탐색").credit(1.0).year(entranceYear).semester(2).abeekType(AbeekType.GYOYANG)
                                        .build()
                );

                // when
                QualificationResult result = qualificationAudit.getQualificationResult(studentCourses);

                // then
                assertThat(result).isNotNull();
                assertThat(result.passResults().get(AbeekType.GYOYANG)).isTrue();
            }

            @ParameterizedTest
            @ValueSource(shorts = {2022, 2023, 2024, 2025})
            @DisplayName("2022~2025년도 전문교양 - 필수 과목을 모두 이수하고 선택 과목 중 2과목 이상 이수하면 검사에 통과한다.")
            void proGyoyangTest2022to2025(short entranceYear) {
                // given
                QualificationAudit qualificationAudit = qualificationAuditRepository.findQualificationAudit(departmentName, entranceYear);

                List<AuditCompletedCourse> studentCourses = List.of(
                    // 인증필수
                    AuditCompletedCourse.builder()
                                        .code("009067")
                                        .name("문제해결을위한글쓰기와발표")
                                        .credit(3.0)
                                        .year(entranceYear)
                                        .semester(1)
                                        .abeekType(AbeekType.GYOYANG)
                                        .build(),
                    AuditCompletedCourse.builder()
                                        .code("009068")
                                        .name("서양철학:쟁점과토론")
                                        .credit(3.0)
                                        .year(entranceYear)
                                        .semester(2)
                                        .abeekType(AbeekType.GYOYANG)
                                        .build(),
                    AuditCompletedCourse.builder()
                                        .code("011304")
                                        .name("대학영어")
                                        .credit(2.0)
                                        .year(entranceYear)
                                        .semester(2)
                                        .abeekType(AbeekType.GYOYANG)
                                        .build(),
                    // 인증선택 중 하나 (예: 세계사)
                    AuditCompletedCourse.builder()
                                        .code("011307")
                                        .name("세계사")
                                        .credit(3.0)
                                        .year(entranceYear)
                                        .semester(1)
                                        .abeekType(AbeekType.GYOYANG)
                                        .build(),
                    AuditCompletedCourse.builder()
                                        .code("011305")
                                        .name("동서양의사상과윤리")
                                        .credit(3.0)
                                        .year(entranceYear)
                                        .semester(2)
                                        .abeekType(AbeekType.GYOYANG)
                                        .build()
                );

                // when
                QualificationResult result = qualificationAudit.getQualificationResult(studentCourses);

                // then
                assertThat(result).isNotNull();
                assertThat(result.passResults().get(AbeekType.GYOYANG)).isTrue();
            }
        }

        @Nested
        class MSC {

            @ParameterizedTest
            @ValueSource(shorts = {2020, 2021})
            @DisplayName("2020~2021년도 MSC - 필수 과목을 모두 이수하고 선택 과목을 1개 이상 이수하면 검사에 통과한다.")
            void mscAreaPassTest2020to2021(short entranceYear) {
                // given
                QualificationAudit qualificationAudit = qualificationAuditRepository.findQualificationAudit(departmentName, entranceYear);

                List<AuditCompletedCourse> studentCourses = List.of(
                    // 인증필수
                    AuditCompletedCourse.builder().code("010140").name("일변수미적분학").credit(3.0).year(entranceYear).semester(1).abeekType(AbeekType.MSC).build(),
                    AuditCompletedCourse.builder().code("010141").name("다변수미적분학").credit(3.0).year(entranceYear).semester(2).abeekType(AbeekType.MSC).build(),
                    AuditCompletedCourse.builder().code("000304").name("공업수학1").credit(3.0).year(entranceYear + 1).semester(1).abeekType(AbeekType.MSC).build(),
                    AuditCompletedCourse.builder().code("000307").name("공업수학2").credit(3.0).year(entranceYear + 1).semester(2).abeekType(AbeekType.MSC).build(),
                    AuditCompletedCourse.builder().code("002647").name("일반물리학및실험1").credit(3.0).year(entranceYear).semester(1).abeekType(AbeekType.MSC).build(),
                    AuditCompletedCourse.builder().code("002649").name("일반물리학및실험2").credit(3.0).year(entranceYear).semester(2).abeekType(AbeekType.MSC).build(),
                    AuditCompletedCourse.builder().code("002703").name("일반화학").credit(3.0).year(entranceYear + 1).semester(1).abeekType(AbeekType.MSC).build(),
                    AuditCompletedCourse.builder().code("009799").name("소프트웨어기초코딩").credit(3.0).year(entranceYear).semester(1).abeekType(AbeekType.MSC).build(),
                    AuditCompletedCourse.builder().code("009791").name("고급프로그래밍입문-C").credit(3.0).year(entranceYear).semester(2).abeekType(AbeekType.MSC)
                                        .build(),

                    // 인증선택 중 하나 (예: 선형대수학)
                    AuditCompletedCourse.builder().code("001727").name("선형대수학").credit(3.0).year(entranceYear + 1).semester(2).abeekType(AbeekType.MSC).build()
                );

                // when
                QualificationResult result = qualificationAudit.getQualificationResult(studentCourses);

                // then
                assertThat(result).isNotNull();
                assertThat(result.passResults().get(AbeekType.MSC)).isTrue();
            }

            @ParameterizedTest
            @ValueSource(shorts = {2022, 2023})
            @DisplayName("2022~2023년도 MSC - 필수 과목을 모두 이수하고 선택 과목을 1개 이상 이수하면 검사에 통과한다.")
            void mscAreaPassTest2022to2023(short entranceYear) {
                // given
                QualificationAudit qualificationAudit = qualificationAuditRepository.findQualificationAudit(departmentName, entranceYear);

                List<AuditCompletedCourse> studentCourses = List.of(
                    // 인증필수
                    AuditCompletedCourse.builder().code("001357").name("미적분학1").credit(3.0).year(entranceYear).semester(1).abeekType(AbeekType.MSC).build(),
                    AuditCompletedCourse.builder().code("001362").name("미적분학2").credit(3.0).year(entranceYear).semester(2).abeekType(AbeekType.MSC).build(),
                    AuditCompletedCourse.builder().code("000304").name("공업수학1").credit(3.0).year(entranceYear).semester(1).abeekType(AbeekType.MSC).build(),
                    AuditCompletedCourse.builder().code("000307").name("공업수학2").credit(3.0).year(entranceYear).semester(2).abeekType(AbeekType.MSC).build(),
                    AuditCompletedCourse.builder().code("002638").name("일반물리학1").credit(3.0).year(entranceYear).semester(1).abeekType(AbeekType.MSC).build(),
                    AuditCompletedCourse.builder().code("002641").name("일반물리학2").credit(3.0).year(entranceYear).semester(2).abeekType(AbeekType.MSC).build(),
                    AuditCompletedCourse.builder().code("002705").name("일반화학1").credit(3.0).year(entranceYear + 1).semester(1).abeekType(AbeekType.MSC).build(),
                    AuditCompletedCourse.builder().code("011298").name("SW기초코딩").credit(3.0).year(entranceYear).semester(1).abeekType(AbeekType.MSC).build(),
                    AuditCompletedCourse.builder().code("011299").name("프로그래밍활용-C").credit(3.0).year(entranceYear).semester(2).abeekType(AbeekType.MSC).build(),

                    // 인증선택 중 하나 (예: 선형대수학)
                    AuditCompletedCourse.builder().code("001727").name("선형대수학").credit(3.0).year(entranceYear + 1).semester(2).abeekType(AbeekType.MSC).build()
                );

                // when
                QualificationResult result = qualificationAudit.getQualificationResult(studentCourses);

                // then
                assertThat(result).isNotNull();
                assertThat(result.passResults().get(AbeekType.MSC)).isTrue();
            }

            @ParameterizedTest
            @ValueSource(shorts = {2024, 2025})
            @DisplayName("2024~2025년도 MSC - 필수 과목을 모두 이수하고 선택 과목을 1개 이상 이수하면 검사에 통과한다.")
            void mscAreaPassTest2024to2025(short entranceYear) {
                // given
                QualificationAudit qualificationAudit = qualificationAuditRepository.findQualificationAudit(departmentName, entranceYear);

                List<AuditCompletedCourse> studentCourses = List.of(
                    // 인증필수
                    AuditCompletedCourse.builder().code("001357").name("미적분학1").credit(3.0).year(entranceYear).semester(1).abeekType(AbeekType.MSC).build(),
                    AuditCompletedCourse.builder().code("001362").name("미적분학2").credit(3.0).year(entranceYear).semester(2).abeekType(AbeekType.MSC).build(),
                    AuditCompletedCourse.builder().code("000304").name("공업수학1").credit(3.0).year(entranceYear).semester(1).abeekType(AbeekType.MSC).build(),
                    AuditCompletedCourse.builder().code("000307").name("공업수학2").credit(3.0).year(entranceYear).semester(2).abeekType(AbeekType.MSC).build(),
                    AuditCompletedCourse.builder().code("002638").name("일반물리학1").credit(3.0).year(entranceYear).semester(1).abeekType(AbeekType.MSC).build(),
                    AuditCompletedCourse.builder().code("002705").name("일반화학1").credit(3.0).year(entranceYear + 1).semester(1).abeekType(AbeekType.MSC).build(),
                    AuditCompletedCourse.builder().code("011298").name("SW기초코딩").credit(3.0).year(entranceYear).semester(1).abeekType(AbeekType.MSC).build(),
                    AuditCompletedCourse.builder().code("011300").name("고급프로그래밍활용").credit(3.0).year(entranceYear).semester(2).abeekType(AbeekType.MSC).build(),

                    // 인증선택 중 하나 (예: 선형대수학)
                    AuditCompletedCourse.builder().code("001727").name("선형대수학").credit(3.0).year(entranceYear + 1).semester(2).abeekType(AbeekType.MSC).build()
                );

                // when
                QualificationResult result = qualificationAudit.getQualificationResult(studentCourses);

                // then
                assertThat(result).isNotNull();
                assertThat(result.passResults().get(AbeekType.MSC)).isTrue();
            }
        }

        @Nested
        class Design {

            @ParameterizedTest
            @ValueSource(shorts = {2020, 2021, 2022, 2023, 2024, 2025})
            @DisplayName("설계 - 기초설계, 요소 설계 3과목 이상, 종합설계를 이수하면 검사에 통과한다.")
            void designAreaPassTest(short entranceYear) {
                // given
                QualificationAudit qualificationAudit = qualificationAuditRepository.findQualificationAudit(departmentName, entranceYear);

                List<AuditCompletedCourse> studentCourses = List.of(
                    AuditCompletedCourse.builder()
                                        .code("007620")
                                        .name("기초설계")
                                        .credit(3.0)
                                        .year(entranceYear)
                                        .semester(1)
                                        .abeekType(AbeekType.DESIGN)
                                        .designCredit(3.0)
                                        .build(),
                    AuditCompletedCourse.builder()
                                        .code("006887")
                                        .name("유도제어시스템설계")
                                        .credit(3.0)
                                        .year(entranceYear + 3)
                                        .semester(2)
                                        .abeekType(AbeekType.DESIGN)
                                        .designCredit(1.0)
                                        .build(),
                    AuditCompletedCourse.builder()
                                        .code("008115")
                                        .name("로켓공학및설계")
                                        .credit(3.0)
                                        .year(entranceYear + 3)
                                        .semester(1)
                                        .abeekType(AbeekType.DESIGN)
                                        .designCredit(1.0)
                                        .build(),
                    AuditCompletedCourse.builder()
                                        .code("010662")
                                        .name("메카트로닉스종합설계")
                                        .credit(3.0)
                                        .year(entranceYear + 2)
                                        .semester(1)
                                        .abeekType(AbeekType.DESIGN)
                                        .designCredit(1.0)
                                        .build(),
                    AuditCompletedCourse.builder()
                                        .code("009836")
                                        .name("종합설계2")
                                        .credit(3.0)
                                        .year(entranceYear + 3)
                                        .semester(2)
                                        .abeekType(AbeekType.DESIGN)
                                        .designCredit(3.0)
                                        .build()
                );

                // when
                QualificationResult result = qualificationAudit.getQualificationResult(studentCourses);

                // then
                assertThat(result).isNotNull();
                assertThat(result.passResults().get(AbeekType.DESIGN)).isTrue();
            }
        }
    }
}
