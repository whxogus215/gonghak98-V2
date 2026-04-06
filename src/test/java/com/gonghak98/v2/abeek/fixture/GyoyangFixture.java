package com.gonghak98.v2.abeek.fixture;

import com.gonghak98.v2.report.domain.abeek.gyoyang.Gyoyang;
import com.gonghak98.v2.report.domain.abeek.gyoyang.ProGyoyang;
import com.gonghak98.v2.report.domain.course.Course;
import java.util.List;

public class GyoyangFixture {

    public static Gyoyang createProGyoyang() {

        List<Course> essentialCourses = List.of(
            Course.builder().code("009067").name("문제해결을위한글쓰기와발표").credit(3.0).build(),
            Course.builder().code("009068").name("서양철학:쟁점과토론").credit(3.0).build(),
            Course.builder().code("011304").name("대학영어").credit(2.0).build()
        );

        List<Course> electiveCourses = List.of(
            Course.builder().code("011307").name("세계사").credit(3.0).build(),
            Course.builder().code("011305").name("동서양의사상과윤리").credit(3.0).build(),
            Course.builder().code("011313").name("경제학").credit(3.0).build(),
            Course.builder().code("011312").name("경영학").credit(3.0).build(),
            Course.builder().code("011317").name("컴퓨터게임과메타버스").credit(3.0).build(),
            Course.builder().code("011316").name("융합예술의이해").credit(3.0).build()
        );

        return new ProGyoyang(essentialCourses, electiveCourses, 14);
    }
}
