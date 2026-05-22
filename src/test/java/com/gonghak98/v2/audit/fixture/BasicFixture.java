package com.gonghak98.v2.audit.fixture;

import com.gonghak98.v2.audit.domain.abeek.Basic;
import com.gonghak98.v2.audit.domain.constant.AbeekType;
import com.gonghak98.v2.audit.domain.rule.RequirementRule;
import com.gonghak98.v2.audit.domain.rule.RuleType;
import com.gonghak98.v2.core.domain.course.Course;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BasicFixture {

    public static Basic 전자정보통신공학과_BASIC_생성() {
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
                             essentialCourses.size(),
                             RuleType.MUST_TAKE_ALL
                         )),
                         27);
    }

    public static Basic 항공우주공학과_BASIC_생성() {
        Set<Course> essentialCourses = Set.of(
            Course.builder().code("001357").name("미적분학1").build(),
            Course.builder().code("001362").name("미적분학2").build(),
            Course.builder().code("000304").name("공업수학1").build(),
            Course.builder().code("000307").name("공업수학2").build(),
            Course.builder().code("002638").name("일반물리학1").build(),
            Course.builder().code("002705").name("일반화학1").build(),
            Course.builder().code("011298").name("SW기초코딩").build(),
            Course.builder().code("011300").name("고급프로그래밍활용").build()
        );
        Set<Course> electiveCourses = Set.of(
            Course.builder().code("001727").name("선형대수학").build(),
            Course.builder().code("004102").name("수치해석").build()
        );

        return new Basic(AbeekType.MSC,
                         List.of(
                             new RequirementRule(
                                 "MSC_BASIC_ESSENTIAL",
                                 essentialCourses.stream().map(Course::getCode).collect(Collectors.toSet()),
                                 essentialCourses.size(),
                                 RuleType.MUST_TAKE_ALL
                             ),
                             new RequirementRule(
                                 "MSC_BASIC_ELECTIVE",
                                 electiveCourses.stream().map(Course::getCode).collect(Collectors.toSet()),
                                 1,
                                 RuleType.MIN_COUNT
                             ),
                             new RequirementRule(
                                 "MSC_MIN_CREDIT",
                                 Stream.concat(essentialCourses.stream(), electiveCourses.stream()).map(Course::getCode).collect(Collectors.toSet()),
                                 1,
                                 RuleType.MIN_CREDIT
                             )
                         ),
                         27);
    }

    public static Basic 소프트웨어학과_BASIC_생성() {
        Set<Course> essentialCourses = Set.of(
            Course.builder().code("001357").name("미적분학1").build(),
            Course.builder().code("001725").name("선형대수").build(),
            Course.builder().code("007330").name("확률및통계").build(),
            Course.builder().code("009912").name("C프로그래밍및실습").build(),
            Course.builder().code("009913").name("고급C프로그래밍및실습").build(),
            Course.builder().code("011300").name("고급프로그래밍활용").build(),
            Course.builder().code("011320").name("인공지능과빅데이터").build(),
            Course.builder().code("011678").name("기초전자물리").build(),
            Course.builder().code("010224").name("창의SW기초설계").build()
        );

        return new Basic(AbeekType.BSM,
                         List.of(new RequirementRule(
                             "BSM_BASIC",
                             essentialCourses.stream().map(Course::getCode).collect(Collectors.toSet()),
                             essentialCourses.size(),
                             RuleType.MUST_TAKE_ALL
                         )),
                         27);
    }
}
