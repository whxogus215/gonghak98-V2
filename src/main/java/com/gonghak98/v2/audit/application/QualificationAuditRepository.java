package com.gonghak98.v2.audit.application;

import com.gonghak98.v2.audit.domain.QualificationAudit;
import com.gonghak98.v2.audit.domain.dto.CourseAuditInfo;
import com.gonghak98.v2.core.domain.course.CompletedCourse;
import java.util.List;
import java.util.Map;

public interface QualificationAuditRepository {

    QualificationAudit findQualificationAudit(String departmentName, Short entranceYear);

    Map<String, CourseAuditInfo> findCourseAuditInfos(List<CompletedCourse> completedCourses, String departmentName);
}
