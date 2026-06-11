package com.gonghak98.v2.audit.domain.prerequisite;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.gonghak98.v2.audit.domain.constant.NonPassMessage;
import com.gonghak98.v2.audit.domain.dto.AuditCompletedCourse;
import com.gonghak98.v2.audit.domain.dto.NonPassResult;
import com.gonghak98.v2.audit.domain.dto.PrerequisiteAuditResult;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class NonDesignPrerequisiteAuditAuditTest {

    @Nested
    class 선후수_조건을_만족하는_경우 {

        @DisplayName("필수 선수과목을 먼저 듣고, 후수 과목을 들었을 때")
        @CsvSource({"2025, 1, 2025, 2", "2025, 2, 2026, 1"})
        @ParameterizedTest
        void 선후수_조건_검사1(int beforeYear, int beforeSemester, int afterYear, int afterSemester) {
            //given
            AuditCompletedCourse beforeCourse = AuditCompletedCourse.builder().code("000001").year(beforeYear).semester(beforeSemester).build();
            AuditCompletedCourse afterCourse = AuditCompletedCourse.builder().code("000002").year(afterYear).semester(afterSemester).build();

            Map<String, List<String>> prerequisiteCourseIds = new HashMap<>();
            prerequisiteCourseIds.put(afterCourse.code(), List.of(beforeCourse.code()));
            NonDesignPrerequisiteAudit nonDesignPrerequisiteAudit = new NonDesignPrerequisiteAudit(prerequisiteCourseIds);

            //when
            PrerequisiteAuditResult prerequisiteAuditResult = nonDesignPrerequisiteAudit.audit(List.of(beforeCourse, afterCourse));

            //then
            assertThat(prerequisiteAuditResult.nonPassResults()).doesNotContain(new NonPassResult(afterCourse.code(),
                                                                                                  afterCourse.name(),
                                                                                                  afterCourse.year(),
                                                                                                  afterCourse.semester(),
                                                                                                  afterCourse.credit(),
                                                                                                  NonPassMessage.NOT_SATISFIED_PREREQUISITE
            )).isEmpty();
        }

        @DisplayName("필수 선수과목이 2개일 때, 모두 후수 과목보다 먼저 들었을 때")
        @Test
        void 선후수_조건_검사2() {
            //given
            AuditCompletedCourse beforeCourse1 = AuditCompletedCourse.builder().code("000001").year(2025).semester(1).build();
            AuditCompletedCourse beforeCourse2 = AuditCompletedCourse.builder().code("000002").year(2025).semester(2).build();
            AuditCompletedCourse afterCourse = AuditCompletedCourse.builder().code("000003").year(2026).semester(1).build();

            Map<String, List<String>> prerequisiteCourseIds = new HashMap<>();
            prerequisiteCourseIds.put(afterCourse.code(), List.of(beforeCourse1.code(), beforeCourse2.code()));
            NonDesignPrerequisiteAudit nonDesignPrerequisiteAudit = new NonDesignPrerequisiteAudit(prerequisiteCourseIds);

            //when
            PrerequisiteAuditResult prerequisiteAuditResult = nonDesignPrerequisiteAudit.audit(List.of(beforeCourse1, beforeCourse2, afterCourse));

            //then
            assertThat(prerequisiteAuditResult.nonPassResults()).doesNotContain(new NonPassResult(afterCourse.code(),
                                                                                                  afterCourse.name(),
                                                                                                  afterCourse.year(),
                                                                                                  afterCourse.semester(),
                                                                                                  afterCourse.credit(),
                                                                                                  NonPassMessage.NOT_SATISFIED_PREREQUISITE
                                                                                )
            ).isEmpty();
        }
    }

    @Nested
    class 선후수_조건을_만족하지_않는_경우 {

        private Map<String, List<String>> prerequisiteCourseIds;
        private NonDesignPrerequisiteAudit nonDesignPrerequisiteAudit;

        @BeforeEach
        void setUp() {
            prerequisiteCourseIds = new HashMap<>();
            nonDesignPrerequisiteAudit = new NonDesignPrerequisiteAudit(prerequisiteCourseIds);
        }

        @DisplayName("필수 선수과목을 이수하지 않았을 때")
        @Test
        void 선후수_조건_검사1() {
            //given
            AuditCompletedCourse beforeCourse = AuditCompletedCourse.builder().code("000001").build();
            AuditCompletedCourse afterCourse = AuditCompletedCourse.builder().code("000002").build();

            prerequisiteCourseIds.put(afterCourse.code(), List.of(beforeCourse.code()));

            //when
            PrerequisiteAuditResult prerequisiteAuditResult = nonDesignPrerequisiteAudit.audit(List.of(afterCourse));

            //then
            assertThat(prerequisiteAuditResult.nonPassResults()).contains(new NonPassResult(
                                                                              afterCourse.code(),
                                                                              afterCourse.name(),
                                                                              afterCourse.year(),
                                                                              afterCourse.semester(),
                                                                              afterCourse.credit(),
                                                                              NonPassMessage.NOT_SATISFIED_PREREQUISITE
                                                                          )
            );
        }

        @DisplayName("필수 선수과목을 후수 과목보다 나중에 들었을 때")
        @CsvSource({"2025, 2, 2025, 1", "2025, 1, 2024, 1", "2025, 2, 2024, 1"})
        @ParameterizedTest
        void 선후수_조건_검사2(int beforeYear, int beforeSemester, int afterYear, int afterSemester) {
            //given
            AuditCompletedCourse beforeCourse = AuditCompletedCourse.builder().code("000001").year(beforeYear).semester(beforeSemester).build();
            AuditCompletedCourse afterCourse = AuditCompletedCourse.builder().code("000002").year(afterYear).semester(afterSemester).build();

            prerequisiteCourseIds.put(afterCourse.code(), List.of(beforeCourse.code()));

            //when
            PrerequisiteAuditResult prerequisiteAuditResult = nonDesignPrerequisiteAudit.audit(List.of(afterCourse));

            //then
            assertThat(prerequisiteAuditResult.nonPassResults()).contains(new NonPassResult(
                                                                              afterCourse.code(),
                                                                              afterCourse.name(),
                                                                              afterCourse.year(),
                                                                              afterCourse.semester(),
                                                                              afterCourse.credit(),
                                                                              NonPassMessage.NOT_SATISFIED_PREREQUISITE
                                                                          )
            );
        }

        @DisplayName("후수 과목을 필수 선수 과목과 동시에 들었을 때")
        @Test
        void 선후수_조건_검사3() {
            //given
            int year = 2026;
            int semester = 1;

            AuditCompletedCourse beforeCourse = AuditCompletedCourse.builder().code("000001").year(year).semester(semester).build();
            AuditCompletedCourse afterCourse = AuditCompletedCourse.builder().code("000002").year(year).semester(semester).build();

            prerequisiteCourseIds.put(afterCourse.code(), List.of(beforeCourse.code()));

            //when
            PrerequisiteAuditResult prerequisiteAuditResult = nonDesignPrerequisiteAudit.audit(List.of(beforeCourse, afterCourse));

            //then
            assertThat(prerequisiteAuditResult.nonPassResults()).contains(new NonPassResult(
                                                                              afterCourse.code(),
                                                                              afterCourse.name(),
                                                                              afterCourse.year(),
                                                                              afterCourse.semester(),
                                                                              afterCourse.credit(),
                                                                              NonPassMessage.NOT_SATISFIED_PREREQUISITE
                                                                          )
            );
        }

        @DisplayName("필수 선수 과목이 2개 이상일 때, 한 개라도 선후수 조건을 만족하지 못했을 때")
        @Test
        void 선후수_조건_검사4() {
            //given
            AuditCompletedCourse beforeCourse1 = AuditCompletedCourse.builder().code("000001").year(2025).semester(1).build();
            AuditCompletedCourse beforeCourse2 = AuditCompletedCourse.builder().code("000002").year(2026).semester(2).build();
            AuditCompletedCourse afterCourse = AuditCompletedCourse.builder().code("000003").year(2026).semester(1).build();

            prerequisiteCourseIds.put(afterCourse.code(), List.of(beforeCourse1.code(), beforeCourse2.code()));

            //when
            PrerequisiteAuditResult prerequisiteAuditResult = nonDesignPrerequisiteAudit.audit(List.of(beforeCourse1, beforeCourse2, afterCourse));

            //then
            assertThat(prerequisiteAuditResult.nonPassResults()).contains(new NonPassResult(
                                                                              afterCourse.code(),
                                                                              afterCourse.name(),
                                                                              afterCourse.year(),
                                                                              afterCourse.semester(),
                                                                              afterCourse.credit(),
                                                                              NonPassMessage.NOT_SATISFIED_PREREQUISITE
                                                                          )
            );
        }
    }
}
