package com.gonghak98.v2.audit.domain.prerequisite;

import com.gonghak98.v2.audit.domain.dto.PrerequisiteAuditResult;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.List;

public interface PrerequisiteAuditable {

    PrerequisiteAuditResult audit(List<CompletedCourse> courses);
}
