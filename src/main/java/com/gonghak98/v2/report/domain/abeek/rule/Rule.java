package com.gonghak98.v2.report.domain.abeek.rule;

import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.List;
import java.util.Set;

public interface Rule {

    boolean isSatisfied(List<CompletedCourse> completedCourses);

    Set<String> getTargetCourseCodes();
}
