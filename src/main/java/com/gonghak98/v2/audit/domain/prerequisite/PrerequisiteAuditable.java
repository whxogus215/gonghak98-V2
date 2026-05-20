package com.gonghak98.v2.audit.domain.prerequisite;

import com.gonghak98.v2.audit.domain.dto.AuditCompletedCourse;
import com.gonghak98.v2.audit.domain.dto.PrerequisiteAuditResult;
import java.util.List;

public interface PrerequisiteAuditable {

    PrerequisiteAuditResult audit(List<AuditCompletedCourse> courses);
}
