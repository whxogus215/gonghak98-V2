package com.gonghak98.v2.requirement.major;

import static org.assertj.core.api.Assertions.assertThat;

import com.gonghak98.v2.completedcourse.CompletedCourse;
import com.gonghak98.v2.course.Course;
import com.gonghak98.v2.course.DesignCourse;
import com.gonghak98.v2.requirement.constant.RequirementType;
import com.gonghak98.v2.requirement.vo.CheckResult;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class MajorRequirementTest {

    @Nested
    class 전자정보통신공학과 {

        @DisplayName("전공영역은 설계, 실험, 일반, 선후수 세부요건을 검사할 수 있다.")
        @CsvSource(value = {"디지털논리회로", "전기회로실험", "기초광학및실험", "전자소자공정실험", "마이크로컴퓨터실험"})
        @ParameterizedTest
        void 전공영역_검사(String studentLabCourseName) {
            //given
            CompletedCourse completedCourse = CompletedCourse.builder().name(studentLabCourseName).point(3).build();

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

            Set<String> essentialLabCourses = Set.of("디지털논리회로", "전기회로실험", "기초광학및실험", "전자소자공정실험", "마이크로컴퓨터실험");
            LabMajor labMajor = new LabMajor(essentialLabCourses, 1);

            Set<String> GENERAL_COURSES = Set.of(
                "전기회로", "신호및시스템", "기초설계", "물리전자공학", "전자회로1",
                "통신이론", "전자기1", "기초반도체", "데이터통신", "디지털신호처리",
                "광전자공학", "컴퓨터네트워크", "디지털통신시스템", "음성처리", "영상처리"
            );
            GeneralMajor generalMajor = new GeneralMajor(GENERAL_COURSES, 24);

            MajorRequirement majorRequirement = new MajorRequirement(designMajor, labMajor, generalMajor);

            //when
            CheckResult checkResult = majorRequirement.check(List.of(completedCourse));

            //then
            assertThat(checkResult.passResults().get(RequirementType.DESIGN)).isFalse();
            assertThat(checkResult.passResults().get(RequirementType.LAB)).isTrue();
            assertThat(checkResult.passResults().get(RequirementType.GENERAL)).isFalse();
            assertThat(completedCourse.isPassed()).isTrue();
        }
    }
}
