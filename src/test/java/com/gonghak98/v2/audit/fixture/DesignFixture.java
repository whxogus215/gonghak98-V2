package com.gonghak98.v2.audit.fixture;

import com.gonghak98.v2.audit.domain.abeek.Design;
import com.gonghak98.v2.core.domain.course.Course;
import com.gonghak98.v2.core.domain.course.DesignCourse;
import java.util.List;

public class DesignFixture {

    public static Design createDesign() {
        Course basicCourse = Course.builder().code("007620").name("기초설계").build();
        List<Course> elementCourses = List.of(
            Course.builder().code("007721").name("전자소자설계").build(), // 전자소자설계 -> 반도체소자설계
            Course.builder().code("009650").name("데이터통신설계").build(),
            Course.builder().code("006935").name("정보시스템설계").build(),
            Course.builder().code("009662").name("전자회로설계").build(),
            Course.builder().code("007585").name("통신시스템설계").build(),
            Course.builder().code("009663").name("멀티미디어설계").build()
        );
        List<Course> comprehensiveCourses = List.of(
            Course.builder().code("009947").name("캡스톤디자인A").build(),
            Course.builder().code("009948").name("캡스톤디자인B").build()
        );

        DesignCourse basicDesignCourse = DesignCourse.builder().course(basicCourse).designCredit(3.0).build();
        List<DesignCourse> elementDesignCourses = elementCourses.stream()
                                                                .map(c -> DesignCourse.builder().course(c).designCredit(2.0).build())
                                                                .toList();
        List<DesignCourse> comprehensiveDesignCourses = comprehensiveCourses.stream()
                                                                            .map(c -> DesignCourse.builder().course(c).designCredit(3.0).build())
                                                                            .toList();
        double minDesignPoint = 9.0;

        return new Design(basicDesignCourse, elementDesignCourses, comprehensiveDesignCourses, minDesignPoint);
    }
}
