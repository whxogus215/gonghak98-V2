package com.gonghak98.v2.audit.domain;

import com.gonghak98.v2.audit.domain.abeek.AbeekAuditable;
import com.gonghak98.v2.audit.domain.constant.AbeekType;
import com.gonghak98.v2.audit.domain.dto.AbeekAreaAuditResult;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Prerequisite implements AbeekAuditable {

    private final NonDesignPrerequisite nonDesignPrerequisite;
    private final DesignPrerequisite designPrerequisite;

    @Override
    public AbeekAreaAuditResult audit(List<CompletedCourse> courses) {
        AbeekAreaAuditResult nonDesignResult = nonDesignPrerequisite.audit(courses);
        AbeekAreaAuditResult designResult = designPrerequisite.audit(courses);
        return AbeekAreaAuditResult.merge(nonDesignResult, designResult);
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
