package com.gonghak98.v2.audit.domain.dto;

import com.gonghak98.v2.audit.domain.constant.NonPassMessage;
import com.gonghak98.v2.core.domain.course.CompletedCourse;

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
