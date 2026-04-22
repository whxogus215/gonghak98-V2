package com.gonghak98.v2.report.domain.abeek.rule;

import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RequirementRule {

    private final String name;

    @Getter
    private final Set<String> targetCourseCodes;

    private final int conditionValue;

    private final RuleType ruleType;

    public boolean isSatisfied(List<CompletedCourse> completedCourses) {
        return ruleType.check(targetCourseCodes,conditionValue,completedCourses);
    }
}
