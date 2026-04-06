package com.gonghak98.v2.abeek.basic.msc;

import static com.gonghak98.v2.abeek.fixture.BasicFixture.createMscBasic;
import static org.assertj.core.api.Assertions.assertThat;

import com.gonghak98.v2.abeek.fixture.GivenObjectFixture;
import com.gonghak98.v2.report.domain.abeek.AbeekType;
import com.gonghak98.v2.report.domain.abeek.basic.Basic;
import com.gonghak98.v2.report.domain.abeek.dto.RequirementResult;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class MscBasicTest {

    @Nested
    class 전자정보통신공학과 {

        @DisplayName("지정된 MSC 교과목을 모두 이수하면, MSC 영역을 만족한다.")
        @MethodSource("provideAllEssentialMscCourses")
        @ParameterizedTest
        void MSC_영역_검사(List<CompletedCourse> studentCourses) {
            //given
            RequirementResult requirementResult = GivenObjectFixture.createCheckResult();
            Basic basic = createMscBasic();

            //when
            basic.checkAllCourses(studentCourses, requirementResult);

            //then
            assertThat(requirementResult.passResults().get(AbeekType.MSC)).isTrue();
        }

        @DisplayName("지정된 MSC 교과목을 모두 이수하지 못하면, MSC 영역을 만족하지 못한다.")
        @MethodSource("provideNotAllEssentialMscCourses")
        @ParameterizedTest
        void MSC_영역_검사2(List<CompletedCourse> studentCourses) {
            //given
            RequirementResult requirementResult = GivenObjectFixture.createCheckResult();
            Basic basic = createMscBasic();

            //when
            basic.checkAllCourses(studentCourses, requirementResult);

            //then
            assertThat(requirementResult.passResults().get(AbeekType.MSC)).isFalse();
        }

        private static Stream<Arguments> provideAllEssentialMscCourses() {
            List<CompletedCourse> essentialCourses = List.of(
                CompletedCourse.builder().code("011300").name("고급프로그래밍활용").build(),
                CompletedCourse.builder().code("007330").name("확률및통계").build(),
                CompletedCourse.builder().code("009912").name("C프로그래밍및실습").build(),
                CompletedCourse.builder().code("001357").name("미적분학1").build(),
                CompletedCourse.builder().code("000304").name("공업수학1").build(),
                CompletedCourse.builder().code("009913").name("고급C프로그래밍및실습").build(),
                CompletedCourse.builder().code("001725").name("선형대수").build(),
                CompletedCourse.builder().code("011320").name("인공지능과빅데이터").build(),
                CompletedCourse.builder().code("011678").name("기초전자물리").build()
            );

            List<CompletedCourse> otherCourses = List.of(
                CompletedCourse.builder().code("999999").name("테스트과목").build()
            );

            return Stream.of(
                Arguments.of(Stream.concat(essentialCourses.stream(), otherCourses.stream()).toList())
            );
        }

        private static Stream<Arguments> provideNotAllEssentialMscCourses() {
            List<CompletedCourse> essentialCourses = List.of(
                CompletedCourse.builder().code("011300").name("고급프로그래밍활용").build(),
                CompletedCourse.builder().code("007330").name("확률및통계").build(),
                CompletedCourse.builder().code("009912").name("C프로그래밍및실습").build(),
                CompletedCourse.builder().code("001357").name("미적분학1").build(),
                CompletedCourse.builder().code("000304").name("공업수학1").build()
            );

            List<CompletedCourse> otherCourses = List.of(
                CompletedCourse.builder().code("999999").name("테스트과목").build()
            );

            return Stream.of(
                Arguments.of(Stream.concat(essentialCourses.stream(), otherCourses.stream()).toList())
            );
        }
    }
}
