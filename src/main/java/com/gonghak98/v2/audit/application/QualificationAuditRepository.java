package com.gonghak98.v2.audit.application;

import com.gonghak98.v2.audit.domain.QualificationAudit;
import com.gonghak98.v2.audit.domain.constant.AbeekType;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.List;
import java.util.Map;

public interface QualificationAuditRepository {

    QualificationAudit findQualificationAudit(String departmentName, Short entranceYear);

    Map<String, AbeekType> findAbeekTypeOfCompletedCourse(List<CompletedCourse> completedCourses, String departmentName);
}
