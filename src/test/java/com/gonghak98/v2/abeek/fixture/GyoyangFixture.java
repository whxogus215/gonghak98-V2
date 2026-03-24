package com.gonghak98.v2.abeek.fixture;

import com.gonghak98.v2.report.domain.abeek.gyoyang.Gyoyang;
import com.gonghak98.v2.report.domain.abeek.gyoyang.ProGyoyang;
import com.gonghak98.v2.report.domain.course.Course;
import java.util.List;

public class GyoyangFixture {

    public static Gyoyang createProGyoyang() {

        List<Course> essentialCourses = List.of(
            Course.builder().id(9067L).name("문제해결을위한글쓰기와발표").point(3.0).build(),
            Course.builder().id(9068L).name("서양철학:쟁점과토론").point(3.0).build(),
            Course.builder().id(11304L).name("대학영어").point(2.0).build()
        );

        List<Course> electiveCourses = List.of(
            Course.builder().id(11307L).name("세계사").point(3.0).build(),
            Course.builder().id(11305L).name("동서양의사상과윤리").point(3.0).build(),
            Course.builder().id(11313L).name("경제학").point(3.0).build(),
            Course.builder().id(11312L).name("경영학").point(3.0).build(),
            Course.builder().id(11317L).name("컴퓨터게임과메타버스").point(3.0).build(),
            Course.builder().id(11316L).name("융합예술의이해").point(3.0).build()
        );

        return new ProGyoyang(essentialCourses, electiveCourses, 14);
    }
}
