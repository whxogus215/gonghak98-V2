package com.gonghak98.v2.audit.domain.rule;

import com.gonghak98.v2.audit.domain.dto.AuditCompletedCourse;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RequirementRule implements Rule {

    private final String name;

    private final Set<String> targetCourseCodes;

    private final int conditionValue;

    private final RuleType ruleType;

    @Override
    public boolean isSatisfied(List<AuditCompletedCourse> auditCompletedCourses) {
        return ruleType.check(targetCourseCodes, conditionValue, auditCompletedCourses);
    }

    @Override
    public Set<String> getTargetCourseCodes() {
        return targetCourseCodes;
    }
}
