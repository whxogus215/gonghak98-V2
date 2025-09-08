package com.gonghak98.v2.abeek.prerequisite;

import static org.assertj.core.api.Assertions.assertThat;

import com.gonghak98.v2.report.domain.abeek.AreaType;
import com.gonghak98.v2.report.domain.abeek.NonPassMessage;
import com.gonghak98.v2.report.domain.abeek.dto.CheckResult;
import com.gonghak98.v2.abeek.fixture.PrerequisiteFactory;
import com.gonghak98.v2.report.domain.abeek.prerequisite.DesignPrerequisite;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DesignPrerequisiteTest {

    @Nested
    class 선후수_조건을_만족하지_않는_경우 {

        @DisplayName("기초설계를 이수하기 전에 요소설계를 먼저 들었을 때")
        @CsvSource({"2025, 1, 2025, 2", "2024, 1, 2025, 1", "2024, 1, 2025, 2"})
        @ParameterizedTest
        void 설계_선후수_조건_검사1(int beforeYear, int beforeSemester, int afterYear, int afterSemester) {
            //given
            CheckResult checkResult = new CheckResult(new EnumMap<>(AreaType.class), new HashMap<>());

            CompletedCourse elementCompletedCourse = CompletedCourse.builder().id(7721).year(beforeYear).semester(beforeSemester).build();
            CompletedCourse basicCompletedCourse = CompletedCourse.builder().id(7620).year(afterYear).semester(afterSemester).build();

            DesignPrerequisite designPrerequisite = PrerequisiteFactory.createDesignPrerequisite();

            //when
            designPrerequisite.check(List.of(basicCompletedCourse, elementCompletedCourse), checkResult);

            //then
            assertThat(checkResult.nonPassResults()).containsEntry(elementCompletedCourse.getId(), NonPassMessage.NOT_SATISFIED_PREREQUISITE);
            assertThat(checkResult.passResults()).containsEntry(AreaType.DESIGN, Boolean.FALSE);
        }

        @DisplayName("기초설계를 이수하기 전에 종합설계를 먼저 들었을 때")
        @CsvSource({"2025, 1, 2025, 2", "2024, 1, 2025, 1", "2024, 1, 2025, 2"})
        @ParameterizedTest
        void 설계_선후수_조건_검사2(int beforeYear, int beforeSemester, int afterYear, int afterSemester) {
            //given
            CheckResult checkResult = new CheckResult(new EnumMap<>(AreaType.class), new HashMap<>());

            CompletedCourse comprehensiveCompletedCourse = CompletedCourse.builder().id(9947).year(beforeYear).semester(beforeSemester).build();
            CompletedCourse basicCompletedCourse = CompletedCourse.builder().id(7620).year(afterYear).semester(afterSemester).build();

            DesignPrerequisite designPrerequisite = PrerequisiteFactory.createDesignPrerequisite();

            //when
            designPrerequisite.check(List.of(basicCompletedCourse, comprehensiveCompletedCourse), checkResult);

            //then
            assertThat(checkResult.nonPassResults()).containsEntry(comprehensiveCompletedCourse.getId(), NonPassMessage.NOT_SATISFIED_PREREQUISITE);
            assertThat(checkResult.passResults()).containsEntry(AreaType.DESIGN, Boolean.FALSE);
        }

        @DisplayName("요소설계 2과목 이상 이수하기 전에 종합설계를 들었을 때")
        @Test
        void 설계_선후수_조건_검사3() {
            //given
            CheckResult checkResult = new CheckResult(new EnumMap<>(AreaType.class), new HashMap<>());

            CompletedCourse basicCompletedCourse = CompletedCourse.builder().id(7620).year(2024).semester(2).build();
            CompletedCourse elementCompletedCourse = CompletedCourse.builder().id(7721).year(2025).semester(2).build();
            CompletedCourse comprehensiveCompletedCourse = CompletedCourse.builder().id(9947).year(2025).semester(2).build();

            DesignPrerequisite designPrerequisite = PrerequisiteFactory.createDesignPrerequisite();

            //when
            designPrerequisite.check(List.of(basicCompletedCourse, elementCompletedCourse, comprehensiveCompletedCourse), checkResult);

            //then
            assertThat(checkResult.passResults()).containsEntry(AreaType.DESIGN, Boolean.FALSE);
        }
    }
}
