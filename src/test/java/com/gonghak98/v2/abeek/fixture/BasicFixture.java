package com.gonghak98.v2.abeek.fixture;

import com.gonghak98.v2.report.domain.abeek.basic.Basic;
import com.gonghak98.v2.report.domain.abeek.basic.msc.MscBasic;
import com.gonghak98.v2.report.domain.course.Course;
import java.util.Set;

public class BasicFixture {

    public static Basic createMscBasic() {

        Set<Course> essentialCourses = Set.of(
            Course.builder().id(11300).name("고급프로그래밍활용").build(),
            Course.builder().id(7330).name("확률및통계").build(),
            Course.builder().id(9912).name("C프로그래밍및실습").build(),
            Course.builder().id(1357).name("미적분학1").build(),
            Course.builder().id(304).name("공업수학1").build(),
            Course.builder().id(9913).name("고급C프로그래밍및실습").build(),
            Course.builder().id(1725).name("선형대수").build(),
            Course.builder().id(11320).name("인공지능과빅데이터").build(),
            Course.builder().id(11678).name("기초전자물리").build()
        );

        return new MscBasic(essentialCourses);
    }
}
