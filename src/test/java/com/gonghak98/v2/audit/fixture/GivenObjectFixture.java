package com.gonghak98.v2.audit.fixture;

import com.gonghak98.v2.audit.domain.constant.AbeekType;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.ArrayList;
import java.util.List;

public class GivenObjectFixture {

    public static List<CompletedCourse> createCompletedCoursesWithThreeCredit(int number, AbeekType abeekType) {
        double credit = 3.0;
        List<CompletedCourse> courses = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            courses.add(CompletedCourse.builder()
                                       .code(String.format("%06d", i))
                                       .name("과목" + i)
                                       .credit(credit)
                                       .abeekType(abeekType)
                                       .build());
        }
        return courses;
    }
}
