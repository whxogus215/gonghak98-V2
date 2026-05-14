package com.gonghak98.v2.audit.domain;

import com.gonghak98.v2.audit.domain.constant.AbeekType;
import com.gonghak98.v2.audit.domain.dto.AuditResult;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Prerequisite implements Auditable {

    private final NonDesignPrerequisite nonDesignPrerequisite;
    private final DesignPrerequisite designPrerequisite;

    @Override
    public AuditResult audit(List<CompletedCourse> courses) {
        AuditResult nonDesignResult = nonDesignPrerequisite.audit(courses);
        AuditResult designResult = designPrerequisite.audit(courses);
        return AuditResult.merge(nonDesignResult, designResult);
    }

    @Override
    public Double getRequiredCredits() {
        return 0.0;
    }

    @Override
    public AbeekType getAbeekType() {
        return AbeekType.NONE;
    }
}
