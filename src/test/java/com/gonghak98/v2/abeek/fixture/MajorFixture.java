package com.gonghak98.v2.abeek.fixture;

import com.gonghak98.v2.report.domain.abeek.major.GeneralMajor;
import com.gonghak98.v2.report.domain.abeek.major.LabMajor;
import com.gonghak98.v2.report.domain.course.Course;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MajorFixture {

    private static final String 조회_불가능한_학수번호 = "999999";

    public static GeneralMajor createGeneralMajor() {
        List<Course> generalCourses = List.of(
            Course.builder().code("004114").name("전기회로").credit(3).build(),
            Course.builder().code("005246").name("신호및시스템").credit(3).build(),
            Course.builder().code("007620").name("기초설계").credit(3).build(),
            Course.builder().code("004111").name("물리전자공학").credit(3).build(),
            Course.builder().code("007453").name("전자회로1").credit(3).build(),
            Course.builder().code("004474").name("통신이론").credit(3).build(),
            Course.builder().code("009649").name("전자기1").credit(3).build(),
            Course.builder().code("007806").name("기초반도체").credit(3).build(),
            Course.builder().code("004699").name("데이터통신").credit(3).build(),
            Course.builder().code("004600").name("디지털신호처리").credit(3).build(),
            Course.builder().code("004829").name("광전자공학").credit(3).build(),
            Course.builder().code("003284").name("컴퓨터네트워크").credit(3).build(),
            Course.builder().code("008086").name("디지털통신시스템").credit(3).build(),
            Course.builder().code("006294").name("음성처리").credit(3).build(),
            Course.builder().code("006132").name("영상처리").credit(3).build()
        );
        Set<String> courseIds = generalCourses.stream().map(Course::getCode).collect(Collectors.toSet());
        int minPoint = 24;

        return new GeneralMajor(courseIds, minPoint);
    }

    public static LabMajor createLabMajor() {
        List<Course> essentialLabCourses = List.of(
            Course.builder().code("005611").name("디지털논리회로").credit(3).build(),
            Course.builder().code("009658").name("전기회로실험").credit(3).build(),
            Course.builder().code("008076").name("기초광학및실험").credit(3).build(),
            Course.builder().code(조회_불가능한_학수번호).name("전자소자공정실험").credit(3).build(),
            Course.builder().code("009666").name("마이크로컴퓨터실험").credit(3).build()
        );
        Set<String> courseIds = essentialLabCourses.stream().map(Course::getCode).collect(Collectors.toSet());

        int minCount = 1;

        return new LabMajor(courseIds, minCount);
    }
}
