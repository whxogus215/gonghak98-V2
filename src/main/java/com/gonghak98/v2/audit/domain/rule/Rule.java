package com.gonghak98.v2.audit.domain.rule;

import com.gonghak98.v2.audit.domain.dto.AuditCompletedCourse;
import java.util.List;
import java.util.Set;

public interface Rule {

    boolean isSatisfied(List<AuditCompletedCourse> auditCompletedCourses);

    Set<String> getTargetCourseCodes();
}
