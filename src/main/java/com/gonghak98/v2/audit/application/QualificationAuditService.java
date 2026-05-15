package com.gonghak98.v2.audit.application;

import com.gonghak98.v2.audit.domain.QualificationAudit;
import com.gonghak98.v2.audit.domain.constant.AbeekType;
import com.gonghak98.v2.audit.domain.dto.QualificationResult;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QualificationAuditService {

    private final QualificationAuditRepository qualificationAuditRepository;

    public QualificationResult getQualificationAudit(String departmentName, Short entranceYear, List<CompletedCourse> completedCourses) {
        QualificationAudit qualificationAudit = qualificationAuditRepository.findQualificationAudit(departmentName, entranceYear);
        return qualificationAudit.getQualificationResult(completedCourses);
    }

    public void addAbeekTypeToCompletedCourse(List<CompletedCourse> completedCourses, String departmentName) {
        Map<String, AbeekType> abeekTypeOfCompletedCourses = qualificationAuditRepository.findAbeekTypeOfCompletedCourse(completedCourses, departmentName);

        for (CompletedCourse course : completedCourses) {
            AbeekType mappedType = abeekTypeOfCompletedCourses.getOrDefault(course.getCode(), AbeekType.NONE);
            course.setAbeekType(mappedType);
        }
    }
}
