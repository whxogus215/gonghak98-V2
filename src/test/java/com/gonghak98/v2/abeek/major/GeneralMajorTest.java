package com.gonghak98.v2.abeek.major;

import static com.gonghak98.v2.abeek.fixture.MajorFixture.createGeneralMajor;
import static org.assertj.core.api.Assertions.assertThat;

import com.gonghak98.v2.report.domain.abeek.major.GeneralMajor;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class GeneralMajorTest {

    @ParameterizedTest
    @MethodSource("providePassingGeneralCourseCombinations")
    @DisplayName("일반영역 교과과정의 교과목을 24학점 이상 이수하면, 일반영역 이수조건을 만족한다.")
    void 일반영역_교과목_24학점_이상_이수(List<CompletedCourse> studentCourses) {
        //given
        GeneralMajor generalMajor = createGeneralMajor();

        //when
        boolean isPassed = generalMajor.check(studentCourses);

        //then
        assertThat(isPassed).isTrue();
    }

    @ParameterizedTest
    @MethodSource("provideNotPassingGeneralCourseCombinations")
    @DisplayName("일반영역 교과과정의 교과목을 24학점 미만 이수하면, 일반영역 이수조건을 만족하지 못한다.")
    void 일반영역_교과목_24학점_미만_이수(List<CompletedCourse> studentCourses) {
        //given
        GeneralMajor generalMajor = createGeneralMajor();

        //when
        boolean isPassed = generalMajor.check(studentCourses);

        //then
        assertThat(isPassed).isFalse();
    }

    private static Stream<Arguments> providePassingGeneralCourseCombinations() {

        List<CompletedCourse> essential = List.of(
            CompletedCourse.builder().code("004114").name("전기회로").credit(3).build(),
            CompletedCourse.builder().code("004111").name("물리전자공학").credit(3).build(),
            CompletedCourse.builder().code("005246").name("신호및시스템").credit(3).build(),
            CompletedCourse.builder().code("007620").name("기초설계").credit(3).build()
        );

        List<CompletedCourse> elective1 = List.of(
            CompletedCourse.builder().code("004474").name("통신이론").credit(3).build(),
            CompletedCourse.builder().code("004699").name("데이터통신").credit(3).build(),
            CompletedCourse.builder().code("003284").name("컴퓨터네트워크").credit(3).build(),
            CompletedCourse.builder().code("006132").name("영상처리").credit(3).build()
        );
        List<CompletedCourse> elective2 = List.of(
            CompletedCourse.builder().code("007453").name("전자회로1").credit(3).build(),
            CompletedCourse.builder().code("009649").name("전자기1").credit(3).build(),
            CompletedCourse.builder().code("004829").name("광전자공학").credit(3).build(),
            CompletedCourse.builder().code("008086").name("디지털통신시스템").credit(3).build()
        );

        return Stream.of(
            Arguments.of(Stream.concat(essential.stream(), elective1.stream()).toList()),
            Arguments.of(Stream.concat(essential.stream(), elective2.stream()).toList())
        );
    }

    private static Stream<Arguments> provideNotPassingGeneralCourseCombinations() {

        List<CompletedCourse> essential = List.of(
            CompletedCourse.builder().code("004114").name("전기회로").credit(3).build(),
            CompletedCourse.builder().code("004111").name("물리전자공학").credit(3).build(),
            CompletedCourse.builder().code("005246").name("신호및시스템").credit(3).build(),
            CompletedCourse.builder().code("007620").name("기초설계").credit(3).build()
        );

        List<CompletedCourse> elective1 = List.of(
            CompletedCourse.builder().code("004474").name("통신이론").credit(3).build(),
            CompletedCourse.builder().code("004699").name("데이터통신").credit(3).build()
        );
        List<CompletedCourse> elective2 = List.of(
            CompletedCourse.builder().code("007453").name("전자회로1").credit(3).build(),
            CompletedCourse.builder().code("009649").name("전자기1").credit(3).build()
        );

        return Stream.of(
            Arguments.of(Stream.concat(essential.stream(), elective1.stream()).toList()),
            Arguments.of(Stream.concat(essential.stream(), elective2.stream()).toList())
        );
    }
}
