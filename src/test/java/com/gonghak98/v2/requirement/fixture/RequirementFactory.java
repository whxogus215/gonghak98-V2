package com.gonghak98.v2.requirement.fixture;

import com.gonghak98.v2.course.Course;
import com.gonghak98.v2.course.DesignCourse;
import com.gonghak98.v2.requirement.major.DesignMajor;
import com.gonghak98.v2.requirement.major.GeneralMajor;
import com.gonghak98.v2.requirement.major.LabMajor;
import java.util.List;

public class RequirementFactory {

    private static final int 조회_불가능한_학수번호 = -1;

    public static GeneralMajor createGeneralMajor() {
        List<Course> generalCourses = List.of(
            Course.builder().id(4114).name("전기회로").build(),
            Course.builder().id(5246).name("신호및시스템").build(),
            Course.builder().id(7620).name("기초설계").build(),
            Course.builder().id(4111).name("물리전자공학").build(),
            Course.builder().id(7453).name("전자회로1").build(),
            Course.builder().id(4474).name("통신이론").build(),
            Course.builder().id(9649).name("전자기1").build(),
            Course.builder().id(7806).name("기초반도체").build(),
            Course.builder().id(4699).name("데이터통신").build(),
            Course.builder().id(4600).name("디지털신호처리").build(),
            Course.builder().id(4829).name("광전자공학").build(),
            Course.builder().id(3284).name("컴퓨터네트워크").build(),
            Course.builder().id(8086).name("디지털통신시스템").build(),
            Course.builder().id(6294).name("음성처리").build(),
            Course.builder().id(6132).name("영상처리").build()
        );
        int minPoint = 24;

        return new GeneralMajor(generalCourses, minPoint);
    }

    public static LabMajor createLabMajor() {
        List<Course> essentialLabCourses = List.of(
            Course.builder().id(5611).name("디지털논리회로").build(),
            Course.builder().id(9658).name("전기회로실험").build(),
            Course.builder().id(8076).name("기초광학및실험").build(),
            Course.builder().id(조회_불가능한_학수번호).name("전자소자공정실험").build(),
            Course.builder().id(9666).name("마이크로컴퓨터실험").build()
        );
        int minCount = 1;

        return new LabMajor(essentialLabCourses, minCount);
    }

    public static DesignMajor createDesignMajor() {
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

        return new DesignMajor(basicDesignCourse, elementDesignCourses, comprehensiveDesignCourses, 9.0);
    }
}
