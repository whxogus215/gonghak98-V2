package com.gonghak98.v2.audit.domain.abeek;

import static com.gonghak98.v2.audit.fixture.DesignFixture.createDesign;
import static org.assertj.core.api.Assertions.assertThat;

import com.gonghak98.v2.audit.domain.constant.AbeekType;
import com.gonghak98.v2.audit.domain.dto.AbeekAreaAuditResult;
import com.gonghak98.v2.core.domain.course.CompletedCourse;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DesignTest {

    @ParameterizedTest
    @MethodSource("provideAllSatisfiedDesignCourseCombinations")
    @DisplayName("기초설계 및 캡스톤디자인AㆍB 중 하나를 포함하여 설계 9학점 이상 이수해야 설계 영역 조건을 만족한다.")
    void 기초설계_캡스톤_1개_포함_설계_9학점_이상(List<CompletedCourse> studentCourses) {
        //given
        Design design = createDesign();

        //when
        AbeekAreaAuditResult auditResult = design.audit(studentCourses);

        //then
        assertThat(auditResult.passResults().get(AbeekType.DESIGN)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("provideSufficientBasicAndElementDesignCourseNotComprehensiveDesignCourse")
    @DisplayName("기초설계 및 요소설계 2개를 이수해도 캡스톤디자인 A or B를 이수하지 않으면, 설계 영역 조건을 만족하지 못한다.")
    void 기초설계_요소설계_2개_캡스톤디자인_미이수(List<CompletedCourse> studentCourses) {
        //given
        Design design = createDesign();

        //when
        AbeekAreaAuditResult auditResult = design.audit(studentCourses);

        //then
        assertThat(auditResult.passResults().get(AbeekType.DESIGN)).isFalse();
    }

    private static List<CompletedCourse> createBasicDesignCourses() {
        return List.of(CompletedCourse.builder().code("007620").name("기초설계").build());
    }

    private static List<CompletedCourse> createElementDesignCourses() {
        return List.of(
            CompletedCourse.builder().code("007721").name("전자소자설계").build(),
            CompletedCourse.builder().code("009650").name("데이터통신설계").build(),
            CompletedCourse.builder().code("006935").name("정보시스템설계").build(),
            CompletedCourse.builder().code("009662").name("전자회로설계").build(),
            CompletedCourse.builder().code("007585").name("통신시스템설계").build(),
            CompletedCourse.builder().code("009663").name("멀티미디어설계").build());
    }

    private static List<CompletedCourse> createComprehensiveDesignCourses() {
        return List.of(
            CompletedCourse.builder().code("009947").name("캡스톤디자인A").build(),
            CompletedCourse.builder().code("009948").name("캡스톤디자인B").build());
    }

    private static Stream<Arguments> provideAllSatisfiedDesignCourseCombinations() {
        List<CompletedCourse> basicDesignCourses = createBasicDesignCourses();

        List<CompletedCourse> elementDesignCourses = createElementDesignCourses();
        List<CompletedCourse> elementDesignCourses1 = elementDesignCourses.subList(0, 2);
        List<CompletedCourse> elementDesignCourses2 = elementDesignCourses.subList(2, 4);

        List<CompletedCourse> comprehensiveDesignCourses = createComprehensiveDesignCourses();

        return Stream.of(
            Arguments.of(Stream.concat(Stream.concat(basicDesignCourses.stream(), elementDesignCourses1.stream()),
                                       Stream.of(comprehensiveDesignCourses.get(0))).toList()), // 캡스톤디자인A
            Arguments.of(Stream.concat(Stream.concat(basicDesignCourses.stream(), elementDesignCourses2.stream()),
                                       Stream.of(comprehensiveDesignCourses.get(1))).toList()) // 캡스톤디자인B
        );
    }

    private static Stream<Arguments> provideSufficientBasicAndElementDesignCourseNotComprehensiveDesignCourse() {
        List<CompletedCourse> basicDesignCourses = createBasicDesignCourses();

        List<CompletedCourse> elementDesignCourses = createElementDesignCourses();
        List<CompletedCourse> elementDesignCourses1 = elementDesignCourses.subList(0, 2);
        List<CompletedCourse> elementDesignCourses2 = elementDesignCourses.subList(2, 4);
        List<CompletedCourse> elementDesignCourses3 = elementDesignCourses.subList(4, 6);

        return Stream.of(
            Arguments.of(Stream.concat(basicDesignCourses.stream(), elementDesignCourses1.stream()).toList()),
            Arguments.of(Stream.concat(basicDesignCourses.stream(), elementDesignCourses2.stream()).toList()),
            Arguments.of(Stream.concat(basicDesignCourses.stream(), elementDesignCourses3.stream()).toList()));
    }
}
