package com.gonghak98.v2.abeek.fixture;

import com.gonghak98.v2.report.domain.abeek.major.GeneralMajor;
import com.gonghak98.v2.report.domain.abeek.major.LabMajor;
import com.gonghak98.v2.report.domain.course.Course;
import java.util.List;

public class MajorFixture {

    private static final int 조회_불가능한_학수번호 = -1;

    public static GeneralMajor createGeneralMajor() {
        List<Course> generalCourses = List.of(
            Course.builder().id(4114).name("전기회로").point(3).build(),
            Course.builder().id(5246).name("신호및시스템").point(3).build(),
            Course.builder().id(7620).name("기초설계").point(3).build(),
            Course.builder().id(4111).name("물리전자공학").point(3).build(),
            Course.builder().id(7453).name("전자회로1").point(3).build(),
            Course.builder().id(4474).name("통신이론").point(3).build(),
            Course.builder().id(9649).name("전자기1").point(3).build(),
            Course.builder().id(7806).name("기초반도체").point(3).build(),
            Course.builder().id(4699).name("데이터통신").point(3).build(),
            Course.builder().id(4600).name("디지털신호처리").point(3).build(),
            Course.builder().id(4829).name("광전자공학").point(3).build(),
            Course.builder().id(3284).name("컴퓨터네트워크").point(3).build(),
            Course.builder().id(8086).name("디지털통신시스템").point(3).build(),
            Course.builder().id(6294).name("음성처리").point(3).build(),
            Course.builder().id(6132).name("영상처리").point(3).build()
        );
        List<Integer> courseIds = generalCourses.stream().map(Course::getId).toList();
        int minPoint = 24;

        return new GeneralMajor(courseIds, minPoint);
    }

    public static LabMajor createLabMajor() {
        List<Course> essentialLabCourses = List.of(
            Course.builder().id(5611).name("디지털논리회로").point(3).build(),
            Course.builder().id(9658).name("전기회로실험").point(3).build(),
            Course.builder().id(8076).name("기초광학및실험").point(3).build(),
            Course.builder().id(조회_불가능한_학수번호).name("전자소자공정실험").point(3).build(),
            Course.builder().id(9666).name("마이크로컴퓨터실험").point(3).build()
        );
        List<Integer> courseIds = essentialLabCourses.stream().map(Course::getId).toList();

        int minCount = 1;

        return new LabMajor(courseIds, minCount);
    }
}
