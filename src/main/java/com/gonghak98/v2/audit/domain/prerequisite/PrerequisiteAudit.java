package com.gonghak98.v2.audit.domain.prerequisite;

import com.gonghak98.v2.audit.domain.constant.AbeekType;
import com.gonghak98.v2.audit.domain.dto.PrerequisiteAuditResult;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PrerequisiteAudit {

    private final List<PrerequisiteAuditable> prerequisites;

    public PrerequisiteAuditResult auditPrerequisite(List<CompletedCourse> userCourses) {
        return prerequisites.stream()
                            .map(preq -> preq.audit(userCourses))
                            .reduce(new PrerequisiteAuditResult(new EnumMap<>(AbeekType.class), new ArrayList<>()), PrerequisiteAuditResult::merge);
    }
}
