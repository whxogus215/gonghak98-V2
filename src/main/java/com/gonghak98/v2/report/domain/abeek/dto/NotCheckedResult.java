package com.gonghak98.v2.report.domain.abeek.dto;

import com.gonghak98.v2.report.domain.student.CompletedCourse;

public record NotCheckedResult(String courseCode,
                               String courseName,
                               int year,
                               int semester,
                               double credit) {

    public static NotCheckedResult from(CompletedCourse course) {
        return new NotCheckedResult(course.getCode(),
                                    course.getName(),
                                    course.getYear(),
                                    course.getSemester(),
                                    course.getCredit());
    }
}
