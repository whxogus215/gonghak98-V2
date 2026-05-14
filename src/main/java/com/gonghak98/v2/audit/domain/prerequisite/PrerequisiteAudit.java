package com.gonghak98.v2.audit.domain.prerequisite;

import com.gonghak98.v2.audit.domain.dto.PrerequisiteAuditResult;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PrerequisiteAudit implements PrerequisiteAuditable {

    private final NonDesignPrerequisiteAudit nonDesignPrerequisiteAudit;
    private final DesignPrerequisiteAudit designPrerequisiteAudit;

    @Override
    public PrerequisiteAuditResult auditPrerequisite(List<CompletedCourse> courses) {
        PrerequisiteAuditResult nonDesignResult = nonDesignPrerequisiteAudit.auditPrerequisite(courses);
        PrerequisiteAuditResult designResult = designPrerequisiteAudit.auditPrerequisite(courses);
        return PrerequisiteAuditResult.merge(nonDesignResult, designResult);
    }
}
