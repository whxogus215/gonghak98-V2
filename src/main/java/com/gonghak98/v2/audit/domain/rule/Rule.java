package com.gonghak98.v2.audit.domain.rule;

import com.gonghak98.v2.core.domain.course.CompletedCourse;
import java.util.List;
import java.util.Set;

public interface Rule {

    boolean isSatisfied(List<CompletedCourse> completedCourses);

    Set<String> getTargetCourseCodes();
}
