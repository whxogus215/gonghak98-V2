package com.gonghak98.v2.abeek.fixture;

import com.gonghak98.v2.report.domain.abeek.basic.Basic;
import com.gonghak98.v2.report.domain.abeek.basic.msc.MscBasic;
import com.gonghak98.v2.report.domain.course.Course;
import java.util.Set;

public class BasicFixture {

    public static Basic createMscBasic() {

        Set<Course> essentialCourses = Set.of(
            Course.builder().code("011300").name("고급프로그래밍활용").build(),
            Course.builder().code("007330").name("확률및통계").build(),
            Course.builder().code("009912").name("C프로그래밍및실습").build(),
            Course.builder().code("001357").name("미적분학1").build(),
            Course.builder().code("000304").name("공업수학1").build(),
            Course.builder().code("009913").name("고급C프로그래밍및실습").build(),
            Course.builder().code("001725").name("선형대수").build(),
            Course.builder().code("011320").name("인공지능과빅데이터").build(),
            Course.builder().code("011678").name("기초전자물리").build()
        );

        return new MscBasic(essentialCourses);
    }
}
