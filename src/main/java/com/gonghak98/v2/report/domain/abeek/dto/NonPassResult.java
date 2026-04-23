package com.gonghak98.v2.report.domain.abeek.dto;

import com.gonghak98.v2.report.domain.abeek.NonPassMessage;
import com.gonghak98.v2.report.domain.student.CompletedCourse;

public record NonPassResult(String courseCode,
                            String courseName,
                            int year,
                            int semester,
                            double credit,
                            NonPassMessage nonPassMessage) {

    public static NonPassResult of(CompletedCourse course, NonPassMessage message) {
        return new NonPassResult(course.getCode(),
                                 course.getName(),
                                 course.getYear(),
                                 course.getSemester(),
                                 course.getCredit(),
                                 message);
    }

}
