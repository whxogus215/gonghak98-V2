package com.gonghak98.v2.abeek.prerequisite;

import static org.assertj.core.api.Assertions.assertThat;

import com.gonghak98.v2.report.domain.abeek.AbeekType;
import com.gonghak98.v2.report.domain.abeek.NonPassMessage;
import com.gonghak98.v2.report.domain.abeek.dto.RequirementResult;
import com.gonghak98.v2.report.domain.abeek.prerequisite.NonDesignPrerequisite;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class NonDesignPrerequisiteTest {
    
    @Nested
    class 선후수_조건을_만족하는_경우 {

        @DisplayName("필수 선수과목을 먼저 듣고, 후수 과목을 들었을 때")
        @CsvSource({"2025, 1, 2025, 2", "2025, 2, 2026, 1"})
        @ParameterizedTest
        void 선후수_조건_검사1(int beforeYear, int beforeSemester, int afterYear, int afterSemester) {
            //given
            RequirementResult requirementResult = new RequirementResult(new EnumMap<>(AbeekType.class), new HashMap<>());
            CompletedCourse beforeCourse = CompletedCourse.builder().code(1L).year(beforeYear).semester(beforeSemester).build();
            CompletedCourse afterCourse = CompletedCourse.builder().code(2L).year(afterYear).semester(afterSemester).build();

            Map<Long, Long> prerequisiteCourseIds = new HashMap<>();
            prerequisiteCourseIds.put(afterCourse.getCode(), beforeCourse.getCode());
            NonDesignPrerequisite nonDesignPrerequisite = new NonDesignPrerequisite(prerequisiteCourseIds);

            //when
            nonDesignPrerequisite.check(List.of(beforeCourse, afterCourse), requirementResult);

            //then
            assertThat(requirementResult.nonPassResults()).doesNotContainEntry(afterCourse.getCode(), NonPassMessage.NOT_SATISFIED_PREREQUISITE);
        }
    }
    
    @Nested
    class 선후수_조건을_만족하지_않는_경우 {

        private RequirementResult requirementResult;
        private Map<Long, Long> prerequisiteCourseIds;
        private NonDesignPrerequisite nonDesignPrerequisite;

        @BeforeEach
        void setUp() {
            requirementResult = new RequirementResult(new EnumMap<>(AbeekType.class), new HashMap<>());
            prerequisiteCourseIds = new HashMap<>();
            nonDesignPrerequisite = new NonDesignPrerequisite(prerequisiteCourseIds);
        }

        @DisplayName("필수 선수과목을 이수하지 않았을 때")
        @Test
        void 선후수_조건_검사1() {
            //given
            CompletedCourse beforeCourse = CompletedCourse.builder().code(1L).build();
            CompletedCourse afterCourse = CompletedCourse.builder().code(2L).build();

            prerequisiteCourseIds.put(afterCourse.getCode(), beforeCourse.getCode());

            //when
            nonDesignPrerequisite.check(List.of(afterCourse), requirementResult);

            //then
            assertThat(requirementResult.nonPassResults()).containsEntry(afterCourse.getCode(), NonPassMessage.NOT_SATISFIED_PREREQUISITE);
        }

        @DisplayName("필수 선수과목을 후수 과목보다 나중에 들었을 때")
        @CsvSource({"2025, 2, 2025, 1", "2025, 1, 2024, 1", "2025, 2, 2024, 1"})
        @ParameterizedTest
        void 선후수_조건_검사2(int beforeYear, int beforeSemester, int afterYear, int afterSemester) {
            //given
            CompletedCourse beforeCourse = CompletedCourse.builder().code(1L).year(beforeYear).semester(beforeSemester).build();
            CompletedCourse afterCourse = CompletedCourse.builder().code(2L).year(afterYear).semester(afterSemester).build();

            prerequisiteCourseIds.put(afterCourse.getCode(), beforeCourse.getCode());

            //when
            nonDesignPrerequisite.check(List.of(afterCourse), requirementResult);

            //then
            assertThat(requirementResult.nonPassResults()).containsEntry(afterCourse.getCode(), NonPassMessage.NOT_SATISFIED_PREREQUISITE);
        }

        @DisplayName("후수 과목을 필수 선수 과목과 동시에 들었을 때")
        @Test
        void 선후수_조건_검사3() {
            //given
            int year = 2026;
            int semester = 1;

            CompletedCourse beforeCourse = CompletedCourse.builder().code(1L).year(year).semester(semester).build();
            CompletedCourse afterCourse = CompletedCourse.builder().code(2L).year(year).semester(semester).build();

            prerequisiteCourseIds.put(afterCourse.getCode(), beforeCourse.getCode());

            //when
            nonDesignPrerequisite.check(List.of(beforeCourse, afterCourse), requirementResult);

            //then
            assertThat(requirementResult.nonPassResults()).containsEntry(afterCourse.getCode(), NonPassMessage.NOT_SATISFIED_PREREQUISITE);
        }
    }
}
