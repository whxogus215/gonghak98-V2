package com.gonghak98.v2.audit.domain.dto;

import com.gonghak98.v2.audit.domain.constant.NonPassMessage;

public record NonPassResult(String courseCode,
                            String courseName,
                            int year,
                            int semester,
                            double credit,
                            NonPassMessage nonPassMessage) {

    public static NonPassResult of(AuditCompletedCourse course, NonPassMessage message) {
        return new NonPassResult(course.code(),
                                 course.name(),
                                 course.year(),
                                 course.semester(),
                                 course.credit(),
                                 message);
    }

}
