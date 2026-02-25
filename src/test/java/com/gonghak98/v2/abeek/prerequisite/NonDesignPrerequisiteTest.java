package com.gonghak98.v2.abeek.prerequisite;

import static org.assertj.core.api.Assertions.assertThat;

import com.gonghak98.v2.report.domain.abeek.AreaType;
import com.gonghak98.v2.report.domain.abeek.NonPassMessage;
import com.gonghak98.v2.report.domain.abeek.dto.RequirementResult;
import com.gonghak98.v2.report.domain.abeek.prerequisite.NonDesignPrerequisite;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class NonDesignPrerequisiteTest {

    @Nested
    class 선후수_조건을_만족하지_않는_경우 {

        @DisplayName("필수 선수과목을 이수하지 않았을 때")
        @Test
        void 선후수_조건_검사1() {
            //given
            RequirementResult requirementResult = new RequirementResult(new EnumMap<>(AreaType.class), new HashMap<>());
            CompletedCourse beforeCourse = CompletedCourse.builder().id(1).build();
            CompletedCourse afterCourse = CompletedCourse.builder().id(2).build();

            Map<Integer, Integer> prerequisiteCourseIds = new HashMap<>();
            prerequisiteCourseIds.put(afterCourse.getId(), beforeCourse.getId());
            NonDesignPrerequisite nonDesignPrerequisite = new NonDesignPrerequisite(prerequisiteCourseIds);

            //when
            nonDesignPrerequisite.check(List.of(afterCourse), requirementResult);

            //then
            assertThat(requirementResult.nonPassResults()).containsEntry(afterCourse.getId(), NonPassMessage.NOT_SATISFIED_PREREQUISITE);
        }

        @DisplayName("필수 선수과목을 후수 과목보다 나중에 들었을 때")
        @CsvSource({"2025, 2, 2025, 1", "2025, 1, 2024, 1", "2025, 2, 2024, 1"})
        @ParameterizedTest
        void 선후수_조건_검사2(int beforeYear, int beforeSemester, int afterYear, int afterSemester) {
            //given
            RequirementResult requirementResult = new RequirementResult(new EnumMap<>(AreaType.class), new HashMap<>());
            CompletedCourse beforeCourse = CompletedCourse.builder().id(1).year(beforeYear).semester(beforeSemester).build();
            CompletedCourse afterCourse = CompletedCourse.builder().id(2).year(afterYear).semester(afterSemester).build();

            Map<Integer, Integer> prerequisiteCourseIds = new HashMap<>();
            prerequisiteCourseIds.put(afterCourse.getId(), beforeCourse.getId());
            NonDesignPrerequisite nonDesignPrerequisite = new NonDesignPrerequisite(prerequisiteCourseIds);

            //when
            nonDesignPrerequisite.check(List.of(afterCourse), requirementResult);

            //then
            assertThat(requirementResult.nonPassResults()).containsEntry(afterCourse.getId(), NonPassMessage.NOT_SATISFIED_PREREQUISITE);
        }
    }
}
