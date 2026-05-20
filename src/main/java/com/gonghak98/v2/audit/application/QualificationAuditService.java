package com.gonghak98.v2.audit.application;

import com.gonghak98.v2.audit.domain.QualificationAudit;
import com.gonghak98.v2.audit.domain.constant.AbeekType;
import com.gonghak98.v2.audit.domain.dto.AuditCompletedCourse;
import com.gonghak98.v2.audit.domain.dto.CourseAuditInfo;
import com.gonghak98.v2.audit.domain.dto.QualificationResult;
import com.gonghak98.v2.core.domain.course.CompletedCourse;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QualificationAuditService {

    private final QualificationAuditRepository qualificationAuditRepository;

    public QualificationResult getQualificationAudit(String departmentName, Short entranceYear, List<CompletedCourse> completedCourses) {

        Map<String, CourseAuditInfo> infos = qualificationAuditRepository.findCourseAuditInfos(completedCourses, departmentName);

        List<AuditCompletedCourse> auditCompletedCourses = completedCourses.stream()
                                                                           .map(course -> {
                                                                               CourseAuditInfo info = infos.getOrDefault(
                                                                                   course.getCode(),
                                                                                   new CourseAuditInfo(AbeekType.NONE, 0.0)
                                                                               );
                                                                               return AuditCompletedCourse.from(course, info.abeekType(), info.designCredit());
                                                                           })
                                                                           .toList();

        QualificationAudit qualificationAudit = qualificationAuditRepository.findQualificationAudit(departmentName, entranceYear);
        return qualificationAudit.getQualificationResult(auditCompletedCourses);
    }
}
