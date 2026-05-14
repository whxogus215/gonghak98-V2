package com.gonghak98.v2.fake;

import com.gonghak98.v2.audit.domain.rule.Rule;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class TestRequirementRule implements Rule {

    @Override
    public boolean isSatisfied(List<CompletedCourse> completedCourses) {
        return true;
    }

    @Override
    public Set<String> getTargetCourseCodes() {
        return Collections.emptySet();
    }
}
