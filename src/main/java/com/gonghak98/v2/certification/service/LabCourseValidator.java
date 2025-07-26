package com.gonghak98.v2.certification.service;

import com.gonghak98.v2.certification.domain.LabCourseRule;
import com.gonghak98.v2.course.domain.CompletedCourse;
import java.util.List;

public class LabCourseValidator {

    public boolean validate(List<CompletedCourse> courses, LabCourseRule rule) {
        int count = 0;
        for (CompletedCourse course : courses) {
            if (rule.essentialLabCourseNames().contains(course.getCourseName())) {
                count++;
            }
        }
        return count >= rule.minimumCount();
    }
}
