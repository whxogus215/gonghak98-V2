package com.gonghak98.v2.abeek.fixture;

import com.gonghak98.v2.report.domain.abeek.design.Design;
import com.gonghak98.v2.report.domain.course.Course;
import com.gonghak98.v2.report.domain.course.DesignCourse;
import java.util.List;

public class DesignFixture {

    public static Design createDesign() {
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
        double minDesignPoint = 9.0;

        return new Design(basicDesignCourse, elementDesignCourses, comprehensiveDesignCourses, minDesignPoint);
    }
}
