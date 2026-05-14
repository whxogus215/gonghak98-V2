package com.gonghak98.v2.audit.domain;

import com.gonghak98.v2.audit.domain.constant.AbeekType;
import com.gonghak98.v2.audit.domain.dto.AuditResult;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.List;

public interface Auditable {

    AuditResult audit(List<CompletedCourse> courses);

    Double getRequiredCredits();

    AbeekType getAbeekType();
}
