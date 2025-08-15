package com.gonghak98.v2.abeek.major;

import static org.assertj.core.api.Assertions.assertThat;

import com.gonghak98.v2.student.CompletedCourse;
import com.gonghak98.v2.course.Course;
import com.gonghak98.v2.course.DesignCourse;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DesignMajorTest {

    @ParameterizedTest
    @MethodSource("providePassingDesignCourseCombinations")
    @DisplayName("기초설계 및 캡스톤디자인AㆍB 중 하나를 포함하여 설계 9학점 이상 이수해야 설계 영역 조건을 만족한다.")
    void 기초설계_캡스톤_1개_포함_설계_9학점_이상(List<CompletedCourse> studentCourses) {
        //given
        Course basicCourse = Course.builder().id(7620).name("기초설계").build();
        List<Course> elementCourses = List.of(
            Course.builder().id(7721).name("전자소자설계").build(), // 전자소자설계 -> 반도체소자설계
            Course.builder().id(9650).name("데이터통신설계").build(),
            Course.builder().id(6935).name("정보시스템설계").build(),
            Course.builder().id(9662).name("전자회로설계").build(),
            Course.builder().id(7585).name("통신시스템설계").build(),
            Course.builder().id(9663).name("멀티미디어설계").build()
        );
        List<Course> comprehensiveCourses = List.of(
            Course.builder().id(9947).name("캡스톤디자인A").build(),
            Course.builder().id(9948).name("캡스톤디자인B").build()
        );

        DesignCourse basicDesignCourse = DesignCourse.builder().course(basicCourse).designPoint(3.0).build();
        List<DesignCourse> elementDesignCourses = elementCourses.stream()
                                                                .map(c -> DesignCourse.builder().course(c).designPoint(2.0).build())
                                                                .toList();
        List<DesignCourse> comprehensiveDesignCourses = comprehensiveCourses.stream()
                                                                            .map(c -> DesignCourse.builder().course(c).designPoint(3.0).build())
                                                                            .toList();

        DesignMajor designMajor = new DesignMajor(basicDesignCourse, elementDesignCourses, comprehensiveDesignCourses, 9.0);

        //when
        boolean isPassed = designMajor.check(studentCourses);

        //then
        assertThat(isPassed).isTrue();
    }

    private static Stream<Arguments> providePassingDesignCourseCombinations() {
        List<CompletedCourse> basic = List.of(
            CompletedCourse.builder().id(7620).name("기초설계").point(3).build()
        );

        List<CompletedCourse> elements1 = List.of(
            CompletedCourse.builder().id(9650).name("데이터통신설계").point(3).build(),
            CompletedCourse.builder().id(9663).name("멀티미디어설계").point(3).build()
        );

        List<CompletedCourse> elements2 = List.of(
            CompletedCourse.builder().id(7721).name("반도체소자설계").point(3).build(),
            CompletedCourse.builder().id(9662).name("전자회로설계").point(3).build()
        );

        List<CompletedCourse> comprehensive1 = List.of(
            CompletedCourse.builder().id(9947).name("캡스톤디자인A").point(3).build());
        List<CompletedCourse> comprehensive2 = List.of(
            CompletedCourse.builder().id(9948).name("캡스톤디자인B").point(3).build());

        return Stream.of(
            Arguments.of(Stream.concat(Stream.concat(basic.stream(), elements1.stream()), comprehensive1.stream()).toList()),
            Arguments.of(Stream.concat(Stream.concat(basic.stream(), elements2.stream()), comprehensive2.stream()).toList())
        );
    }
}
