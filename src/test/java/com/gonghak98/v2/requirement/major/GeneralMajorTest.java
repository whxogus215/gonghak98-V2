package com.gonghak98.v2.requirement.major;

import static com.gonghak98.v2.requirement.fixture.RequirementFactory.createGeneralMajor;
import static org.assertj.core.api.Assertions.assertThat;

import com.gonghak98.v2.completedcourse.CompletedCourse;
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
        assertThat(studentCourses).allMatch(CompletedCourse::isPassed);
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
        assertThat(studentCourses).allMatch(CompletedCourse::isPassed);
    }

    private static Stream<Arguments> providePassingGeneralCourseCombinations() {

        List<CompletedCourse> essential = List.of(
            CompletedCourse.builder().id(4114).name("전기회로").point(3).build(),
            CompletedCourse.builder().id(4111).name("물리전자공학").point(3).build(),
            CompletedCourse.builder().id(5246).name("신호및시스템").point(3).build(),
            CompletedCourse.builder().id(7620).name("기초설계").point(3).build()
        );

        List<CompletedCourse> elective1 = List.of(
            CompletedCourse.builder().id(4474).name("통신이론").point(3).build(),
            CompletedCourse.builder().id(4699).name("데이터통신").point(3).build(),
            CompletedCourse.builder().id(3284).name("컴퓨터네트워크").point(3).build(),
            CompletedCourse.builder().id(6132).name("영상처리").point(3).build()
        );
        List<CompletedCourse> elective2 = List.of(
            CompletedCourse.builder().id(7453).name("전자회로1").point(3).build(),
            CompletedCourse.builder().id(9649).name("전자기1").point(3).build(),
            CompletedCourse.builder().id(4829).name("광전자공학").point(3).build(),
            CompletedCourse.builder().id(8086).name("디지털통신시스템").point(3).build()
        );

        return Stream.of(
            Arguments.of(Stream.concat(essential.stream(), elective1.stream()).toList()),
            Arguments.of(Stream.concat(essential.stream(), elective2.stream()).toList())
        );
    }

    private static Stream<Arguments> provideNotPassingGeneralCourseCombinations() {

        List<CompletedCourse> essential = List.of(
            CompletedCourse.builder().id(4114).name("전기회로").point(3).build(),
            CompletedCourse.builder().id(4111).name("물리전자공학").point(3).build(),
            CompletedCourse.builder().id(5246).name("신호및시스템").point(3).build(),
            CompletedCourse.builder().id(7620).name("기초설계").point(3).build()
        );

        List<CompletedCourse> elective1 = List.of(
            CompletedCourse.builder().id(4474).name("통신이론").point(3).build(),
            CompletedCourse.builder().id(4699).name("데이터통신").point(3).build()
        );
        List<CompletedCourse> elective2 = List.of(
            CompletedCourse.builder().id(7453).name("전자회로1").point(3).build(),
            CompletedCourse.builder().id(9649).name("전자기1").point(3).build()
        );

        return Stream.of(
            Arguments.of(Stream.concat(essential.stream(), elective1.stream()).toList()),
            Arguments.of(Stream.concat(essential.stream(), elective2.stream()).toList())
        );
    }
}
