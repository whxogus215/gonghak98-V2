package com.gonghak98.v2.audit.fixture;

import com.gonghak98.v2.audit.domain.abeek.Basic;
import com.gonghak98.v2.audit.domain.constant.AbeekType;
import com.gonghak98.v2.audit.domain.rule.RequirementRule;
import com.gonghak98.v2.audit.domain.rule.RuleType;
import com.gonghak98.v2.report.domain.course.Course;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

        return new Basic(AbeekType.MSC,
                         List.of(new RequirementRule(
                             "MSC_BASIC",
                             essentialCourses.stream().map(Course::getCode).collect(Collectors.toSet()),
                             9,
                             RuleType.MUST_TAKE_ALL
                         )),
                         27);
    }
}
