package com.gonghak98.v2.abeek.gyoyang;

import static com.gonghak98.v2.abeek.fixture.GyoyangFixture.createProGyoyang;
import static org.assertj.core.api.Assertions.assertThat;

import com.gonghak98.v2.abeek.fixture.GivenObjectFixture;
import com.gonghak98.v2.report.domain.abeek.AbeekType;
import com.gonghak98.v2.report.domain.abeek.dto.RequirementResult;
import com.gonghak98.v2.report.domain.abeek.gyoyang.Gyoyang;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ProGyoyangTest {

    @Nested
    class 전자정보통신공학과 {

        @DisplayName("필수 과목을 모두 이수하고, 선택 교과목 중 2과목 이상을 포함해 14학점 이상 이수하면, 전문교양 영역을 만족한다.")
        @MethodSource("provideAllEssentialAndSufficientElectiveCourses")
        @ParameterizedTest
        void 전문교양_영역_검사(List<CompletedCourse> studentCourses) {
            //given
            RequirementResult requirementResult = GivenObjectFixture.createCheckResult();
            Gyoyang gyoyang = createProGyoyang();

            //when
            gyoyang.checkAllCourses(studentCourses, requirementResult);

            //then
            assertThat(requirementResult.passResults().get(AbeekType.GYOYANG)).isTrue();
        }

        @DisplayName("필수 과목을 모두 이수하지 않으면, 선택 교과목 중 2과목 이상을 포함해도, 전문교양 영역을 만족하지 못한다.")
        @MethodSource("provideInsufficientEssentialAndSufficientElectiveCourses")
        @ParameterizedTest
        void 전문교양_영역_검사_실패(List<CompletedCourse> studentCourses) {
            //given
            RequirementResult requirementResult = GivenObjectFixture.createCheckResult();
            Gyoyang gyoyang = createProGyoyang();

            //when
            gyoyang.checkAllCourses(studentCourses, requirementResult);

            //then
            assertThat(requirementResult.passResults().get(AbeekType.GYOYANG)).isFalse();
        }

        @DisplayName("필수 과목을 모두 이수해도, 선택 교과목 중 2과목 미만으로 이수하면, 전문교양 영역을 만족하지 못한다.")
        @MethodSource("provideAllEssentialAndInsufficientElectiveCourses")
        @ParameterizedTest
        void 전문교양_영역_검사_실패2(List<CompletedCourse> studentCourses) {
            //given
            RequirementResult requirementResult = GivenObjectFixture.createCheckResult();
            Gyoyang gyoyang = createProGyoyang();

            //when
            gyoyang.checkAllCourses(studentCourses, requirementResult);

            //then
            assertThat(requirementResult.passResults().get(AbeekType.GYOYANG)).isFalse();
        }

        private static List<CompletedCourse> createEssentialCourses() {
            return List.of(
                CompletedCourse.builder().code(9067L).name("문제해결을위한글쓰기와발표").build(),
                CompletedCourse.builder().code(9068L).name("서양철학:쟁점과토론").build(),
                CompletedCourse.builder().code(11304L).name("대학영어").build()
            );
        }

        private static List<CompletedCourse> createAllElectiveCourses() {
            return List.of(
                CompletedCourse.builder().code(11307L).name("세계사").build(),
                CompletedCourse.builder().code(11305L).name("동서양의사상과윤리").build(),
                CompletedCourse.builder().code(11313L).name("경제학").build(),
                CompletedCourse.builder().code(11312L).name("경영학").build(),
                CompletedCourse.builder().code(11317L).name("컴퓨터게임과메타버스").build(),
                CompletedCourse.builder().code(11316L).name("융합예술의이해").build()
            );
        }

        private static Stream<Arguments> provideAllEssentialAndSufficientElectiveCourses() {
            List<CompletedCourse> essentialCourses = createEssentialCourses();

            List<CompletedCourse> electiveCourses = createAllElectiveCourses();
            List<CompletedCourse> electiveCourses1 = electiveCourses.subList(0, 2);
            List<CompletedCourse> electiveCourses2 = electiveCourses.subList(2, 4);
            List<CompletedCourse> electiveCourses3 = electiveCourses.subList(4, 6);

            return Stream.of(
                Arguments.of(Stream.concat(essentialCourses.stream(), electiveCourses1.stream()).toList()),
                Arguments.of(Stream.concat(essentialCourses.stream(), electiveCourses2.stream()).toList()),
                Arguments.of(Stream.concat(essentialCourses.stream(), electiveCourses3.stream()).toList())
            );
        }

        private static Stream<Arguments> provideInsufficientEssentialAndSufficientElectiveCourses() {
            List<CompletedCourse> essentialCourses = createEssentialCourses().subList(0, 2);

            List<CompletedCourse> electiveCourses = createAllElectiveCourses();
            List<CompletedCourse> electiveCourses1 = electiveCourses.subList(0, 2);
            List<CompletedCourse> electiveCourses2 = electiveCourses.subList(2, 4);
            List<CompletedCourse> electiveCourses3 = electiveCourses.subList(4, 6);

            return Stream.of(
                Arguments.of(Stream.concat(essentialCourses.stream(), electiveCourses1.stream()).toList()),
                Arguments.of(Stream.concat(essentialCourses.stream(), electiveCourses2.stream()).toList()),
                Arguments.of(Stream.concat(essentialCourses.stream(), electiveCourses3.stream()).toList())
            );
        }

        private static Stream<Arguments> provideAllEssentialAndInsufficientElectiveCourses() {
            List<CompletedCourse> essentialCourses = createEssentialCourses();

            List<CompletedCourse> electiveCourses = createAllElectiveCourses();
            List<CompletedCourse> electiveCourses1 = electiveCourses.subList(0, 1);
            List<CompletedCourse> electiveCourses2 = electiveCourses.subList(1, 2);
            List<CompletedCourse> electiveCourses3 = electiveCourses.subList(2, 3);
            List<CompletedCourse> electiveCourses4 = electiveCourses.subList(3, 4);
            List<CompletedCourse> electiveCourses5 = electiveCourses.subList(4, 5);
            List<CompletedCourse> electiveCourses6 = electiveCourses.subList(5, 6);

            return Stream.of(
                Arguments.of(Stream.concat(essentialCourses.stream(), electiveCourses1.stream()).toList()),
                Arguments.of(Stream.concat(essentialCourses.stream(), electiveCourses2.stream()).toList()),
                Arguments.of(Stream.concat(essentialCourses.stream(), electiveCourses3.stream()).toList()),
                Arguments.of(Stream.concat(essentialCourses.stream(), electiveCourses4.stream()).toList()),
                Arguments.of(Stream.concat(essentialCourses.stream(), electiveCourses5.stream()).toList()),
                Arguments.of(Stream.concat(essentialCourses.stream(), electiveCourses6.stream()).toList())
            );
        }
    }
}
