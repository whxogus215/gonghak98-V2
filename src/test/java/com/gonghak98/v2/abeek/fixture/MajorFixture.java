package com.gonghak98.v2.abeek.fixture;

import com.gonghak98.v2.report.domain.abeek.major.GeneralMajor;
import com.gonghak98.v2.report.domain.abeek.major.LabMajor;
import com.gonghak98.v2.report.domain.course.Course;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MajorFixture {

    private static final long 조회_불가능한_학수번호 = -1L;

    public static GeneralMajor createGeneralMajor() {
        List<Course> generalCourses = List.of(
            Course.builder().id(4114L).name("전기회로").point(3).build(),
            Course.builder().id(5246L).name("신호및시스템").point(3).build(),
            Course.builder().id(7620L).name("기초설계").point(3).build(),
            Course.builder().id(4111L).name("물리전자공학").point(3).build(),
            Course.builder().id(7453L).name("전자회로1").point(3).build(),
            Course.builder().id(4474L).name("통신이론").point(3).build(),
            Course.builder().id(9649L).name("전자기1").point(3).build(),
            Course.builder().id(7806L).name("기초반도체").point(3).build(),
            Course.builder().id(4699L).name("데이터통신").point(3).build(),
            Course.builder().id(4600L).name("디지털신호처리").point(3).build(),
            Course.builder().id(4829L).name("광전자공학").point(3).build(),
            Course.builder().id(3284L).name("컴퓨터네트워크").point(3).build(),
            Course.builder().id(8086L).name("디지털통신시스템").point(3).build(),
            Course.builder().id(6294L).name("음성처리").point(3).build(),
            Course.builder().id(6132L).name("영상처리").point(3).build()
        );
        Set<Long> courseIds = generalCourses.stream().map(Course::getId).collect(Collectors.toSet());
        int minPoint = 24;

        return new GeneralMajor(courseIds, minPoint);
    }

    public static LabMajor createLabMajor() {
        List<Course> essentialLabCourses = List.of(
            Course.builder().id(5611L).name("디지털논리회로").point(3).build(),
            Course.builder().id(9658L).name("전기회로실험").point(3).build(),
            Course.builder().id(8076L).name("기초광학및실험").point(3).build(),
            Course.builder().id(조회_불가능한_학수번호).name("전자소자공정실험").point(3).build(),
            Course.builder().id(9666L).name("마이크로컴퓨터실험").point(3).build()
        );
        Set<Long> courseIds = essentialLabCourses.stream().map(Course::getId).collect(Collectors.toSet());

        int minCount = 1;

        return new LabMajor(courseIds, minCount);
    }
}
