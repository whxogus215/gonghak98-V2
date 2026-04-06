package com.gonghak98.v2.abeek.prerequisite;

import static org.assertj.core.api.Assertions.assertThat;

import com.gonghak98.v2.abeek.fixture.DesignFixture;
import com.gonghak98.v2.abeek.fixture.PrerequisiteFixture;
import com.gonghak98.v2.report.domain.abeek.AbeekType;
import com.gonghak98.v2.report.domain.abeek.NonPassMessage;
import com.gonghak98.v2.report.domain.abeek.design.Design;
import com.gonghak98.v2.report.domain.abeek.dto.RequirementResult;
import com.gonghak98.v2.report.domain.abeek.prerequisite.DesignPrerequisite;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DesignPrerequisiteTest {

    @Nested
    class 선후수_조건을_만족하는_경우 {

        private RequirementResult requirementResult;
        private DesignPrerequisite designPrerequisite;
        private Design design;

        @BeforeEach
        void setUp() {
            requirementResult = new RequirementResult(new EnumMap<>(AbeekType.class), new HashMap<>());
            designPrerequisite = PrerequisiteFixture.createDesignPrerequisite();
            design = DesignFixture.createDesign();
        }

        @DisplayName("기초설계를 이수한 후에 요소설계를 들었을 때")
        @CsvSource({"2026, 1, 2026, 2", "2025, 2, 2026, 1"})
        @ParameterizedTest
        void 설계_선후수_조건_검사1(int beforeYear, int beforeSemester, int afterYear, int afterSemester) {
            //given
            CompletedCourse basicCompletedCourse = CompletedCourse.builder().code("007620").year(beforeYear).semester(beforeSemester).build();
            CompletedCourse elementCompletedCourse = CompletedCourse.builder().code("007721").year(afterYear).semester(afterSemester).build();

            //when
            designPrerequisite.check(getAllCompletedCourses(basicCompletedCourse, elementCompletedCourse), requirementResult);

            //then
            assertThat(requirementResult.nonPassResults()).doesNotContainEntry(elementCompletedCourse.getCode(), NonPassMessage.NOT_SATISFIED_PREREQUISITE);
        }

        @DisplayName("기초설계 -> 요소1 -> 요소 2 -> 종합설계 순으로 들었을 때")
        @Test
        void 설계_선후수_조건_검사2() {
            //given
            CompletedCourse basicCompletedCourse = CompletedCourse.builder().code("007620").year(2024).semester(2).build();
            CompletedCourse elementCompletedCourse = CompletedCourse.builder().code("007721").year(2025).semester(1).build();
            CompletedCourse elementCompletedCourse2 = CompletedCourse.builder().code("009650").year(2025).semester(2).build();
            CompletedCourse comprehensiveCompletedCourse = CompletedCourse.builder().code("009947").year(2026).semester(1).build();

            final List<CompletedCourse> allCompletedCourses = getAllCompletedCourses(basicCompletedCourse, elementCompletedCourse, elementCompletedCourse2, comprehensiveCompletedCourse);
            design.checkAllCourses(allCompletedCourses, requirementResult);

            //when
            designPrerequisite.check(allCompletedCourses, requirementResult);

            //then
            assertThat(requirementResult.nonPassResults()).doesNotContainEntry(elementCompletedCourse.getCode(), NonPassMessage.NOT_SATISFIED_PREREQUISITE);
            assertThat(requirementResult.passResults()).containsEntry(AbeekType.DESIGN, Boolean.TRUE);
        }

        @DisplayName("기초설계 -> 요소1 -> 요소 2 + 종합설계 순으로 들었을 때")
        @Test
        void 설계_선후수_조건_검사3() {
            //given
            CompletedCourse basicCompletedCourse = CompletedCourse.builder().code("007620").year(2024).semester(2).build();
            CompletedCourse elementCompletedCourse = CompletedCourse.builder().code("007721").year(2025).semester(1).build();
            CompletedCourse elementCompletedCourse2 = CompletedCourse.builder().code("009650").year(2026).semester(1).build();
            CompletedCourse comprehensiveCompletedCourse = CompletedCourse.builder().code("009947").year(2026).semester(1).build();

            final List<CompletedCourse> allCompletedCourses = getAllCompletedCourses(basicCompletedCourse, elementCompletedCourse, elementCompletedCourse2, comprehensiveCompletedCourse);
            design.checkAllCourses(allCompletedCourses, requirementResult);

            //when
            designPrerequisite.check(allCompletedCourses, requirementResult);

            //then
            assertThat(requirementResult.nonPassResults()).doesNotContainEntry(elementCompletedCourse.getCode(), NonPassMessage.NOT_SATISFIED_PREREQUISITE);
            assertThat(requirementResult.passResults()).containsEntry(AbeekType.DESIGN, Boolean.TRUE);
        }

        @DisplayName("기초설계 -> 요소1 + 요소 2 -> 종합설계 순으로 들었을 때")
        @Test
        void 설계_선후수_조건_검사4() {
            //given
            CompletedCourse basicCompletedCourse = CompletedCourse.builder().code("007620").year(2024).semester(2).build();
            CompletedCourse elementCompletedCourse = CompletedCourse.builder().code("007721").year(2025).semester(2).build();
            CompletedCourse elementCompletedCourse2 = CompletedCourse.builder().code("009650").year(2025).semester(2).build();
            CompletedCourse comprehensiveCompletedCourse = CompletedCourse.builder().code("009947").year(2026).semester(1).build();

            final List<CompletedCourse> allCompletedCourses = getAllCompletedCourses(basicCompletedCourse, elementCompletedCourse, elementCompletedCourse2, comprehensiveCompletedCourse);
            design.checkAllCourses(allCompletedCourses, requirementResult);

            //when
            designPrerequisite.check(allCompletedCourses, requirementResult);

            //then
            assertThat(requirementResult.nonPassResults()).doesNotContainEntry(elementCompletedCourse.getCode(), NonPassMessage.NOT_SATISFIED_PREREQUISITE);
            assertThat(requirementResult.passResults()).containsEntry(AbeekType.DESIGN, Boolean.TRUE);
        }
    }

    @Nested
    class 선후수_조건을_만족하지_않는_경우 {

        private RequirementResult requirementResult;
        private DesignPrerequisite designPrerequisite;

        @BeforeEach
        void setUp() {
            requirementResult = new RequirementResult(new EnumMap<>(AbeekType.class), new HashMap<>());
            designPrerequisite = PrerequisiteFixture.createDesignPrerequisite();
        }

        @DisplayName("기초설계를 이수하기 전에 요소설계를 먼저 들었을 때")
        @CsvSource({"2025, 1, 2025, 2", "2024, 1, 2025, 1", "2024, 1, 2025, 2"})
        @ParameterizedTest
        void 설계_선후수_조건_검사1(int beforeYear, int beforeSemester, int afterYear, int afterSemester) {
            //given
            CompletedCourse elementCompletedCourse = CompletedCourse.builder().code("007721").year(beforeYear).semester(beforeSemester).build();
            CompletedCourse basicCompletedCourse = CompletedCourse.builder().code("007620").year(afterYear).semester(afterSemester).build();

            //when
            designPrerequisite.check(getAllCompletedCourses(basicCompletedCourse, elementCompletedCourse), requirementResult);

            //then
            assertThat(requirementResult.nonPassResults()).containsEntry(elementCompletedCourse.getCode(), NonPassMessage.NOT_SATISFIED_PREREQUISITE);
            assertThat(requirementResult.passResults()).containsEntry(AbeekType.DESIGN, Boolean.FALSE);
        }

        @DisplayName("기초설계를 이수하기 전에 종합설계를 먼저 들었을 때")
        @CsvSource({"2025, 1, 2025, 2", "2024, 2, 2025, 1"})
        @ParameterizedTest
        void 설계_선후수_조건_검사2(int beforeYear, int beforeSemester, int afterYear, int afterSemester) {
            //given
            CompletedCourse comprehensiveCompletedCourse = CompletedCourse.builder().code("009947").year(beforeYear).semester(beforeSemester).build();
            CompletedCourse basicCompletedCourse = CompletedCourse.builder().code("007620").year(afterYear).semester(afterSemester).build();

            //when
            designPrerequisite.check(getAllCompletedCourses(basicCompletedCourse, comprehensiveCompletedCourse), requirementResult);

            //then
            assertThat(requirementResult.nonPassResults()).containsEntry(comprehensiveCompletedCourse.getCode(), NonPassMessage.NOT_SATISFIED_PREREQUISITE);
            assertThat(requirementResult.passResults()).containsEntry(AbeekType.DESIGN, Boolean.FALSE);
        }

        @DisplayName("요소설계 2과목 이상 이수하기 전에 종합설계를 들었을 때")
        @Test
        void 설계_선후수_조건_검사3() {
            //given
            CompletedCourse basicCompletedCourse = CompletedCourse.builder().code("007620").year(2024).semester(2).build();
            CompletedCourse elementCompletedCourse = CompletedCourse.builder().code("007721").year(2025).semester(2).build();
            CompletedCourse comprehensiveCompletedCourse = CompletedCourse.builder().code("009947").year(2025).semester(2).build();

            //when
            designPrerequisite.check(getAllCompletedCourses(basicCompletedCourse, elementCompletedCourse, comprehensiveCompletedCourse), requirementResult);

            //then
            assertThat(requirementResult.passResults()).containsEntry(AbeekType.DESIGN, Boolean.FALSE);
        }

        @DisplayName("기초설계와 요소설계를 동시에 수강했을 때")
        @Test
        void 설계_선후수_조건_동시수강_검사1() {
            //given
            CompletedCourse basicCompletedCourse = CompletedCourse.builder().code("007620").year(2024).semester(2).build();
            CompletedCourse elementCompletedCourse = CompletedCourse.builder().code("007721").year(2024).semester(2).build();

            //when
            designPrerequisite.check(getAllCompletedCourses(basicCompletedCourse, elementCompletedCourse), requirementResult);

            //then
            assertThat(requirementResult.passResults()).containsEntry(AbeekType.DESIGN, Boolean.FALSE);
        }

        @DisplayName("기초설계와 종합설계를 동시에 수강했을 때")
        @Test
        void 설계_선후수_조건_동시수강_검사2() {
            //given
            CompletedCourse basicCompletedCourse = CompletedCourse.builder().code("007620").year(2024).semester(2).build();
            CompletedCourse comprehensiveCompletedCourse = CompletedCourse.builder().code("009947").year(2024).semester(2).build();

            //when
            designPrerequisite.check(getAllCompletedCourses(basicCompletedCourse, comprehensiveCompletedCourse), requirementResult);

            //then
            assertThat(requirementResult.passResults()).containsEntry(AbeekType.DESIGN, Boolean.FALSE);
        }
    }

    private List<CompletedCourse> getAllCompletedCourses(CompletedCourse... course) {
        return new ArrayList<>(List.of(course));
    }
}
