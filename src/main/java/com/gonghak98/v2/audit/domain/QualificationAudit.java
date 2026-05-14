package com.gonghak98.v2.audit.domain;

import com.gonghak98.v2.audit.domain.constant.AbeekType;
import com.gonghak98.v2.audit.domain.dto.AuditResult;
import com.gonghak98.v2.audit.domain.dto.QualificationResult;
import com.gonghak98.v2.report.domain.counting.CreditCalculator;
import com.gonghak98.v2.report.domain.counting.dto.CountingResult;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class QualificationAudit {

    private final List<Auditable> areas;

    public QualificationResult getQualificationResult(List<CompletedCourse> userCourses) {
        AuditResult auditResult = areas.stream()
                                       .map(area -> area.audit(userCourses))
                                       .reduce(new AuditResult(new EnumMap<>(AbeekType.class), new ArrayList<>()), AuditResult::merge);

        Map<AbeekType, List<CompletedCourse>> coursesByAbeekType = categorizeCompletedCourseByAbeekType(userCourses);
        Map<AbeekType, Double> requiredCredits = collectRequiredCredits();
        CountingResult creditCountingResult = CreditCalculator.calculateCredits(coursesByAbeekType, requiredCredits);

        return new QualificationResult(
            auditResult.passResults(),
            auditResult.nonPassResults(),
            creditCountingResult.creditSummaries()
        );
    }

    private Map<AbeekType, List<CompletedCourse>> categorizeCompletedCourseByAbeekType(List<CompletedCourse> completedCourses) {
        Map<AbeekType, List<CompletedCourse>> coursesByAbeekType = new EnumMap<>(AbeekType.class);
        for (CompletedCourse course : completedCourses) {
            AbeekType abeekType = course.getAbeekType();
            if (abeekType == AbeekType.DESIGN) {
                // 설계 기이수 과목은 전공 영역에도 포함
                coursesByAbeekType.computeIfAbsent(AbeekType.MAJOR, k -> new ArrayList<>()).add(course);
            }
            coursesByAbeekType.computeIfAbsent(abeekType, k -> new ArrayList<>()).add(course);
        }
        return coursesByAbeekType;
    }

    private Map<AbeekType, Double> collectRequiredCredits() {
        Map<AbeekType, Double> requiredCredits = new EnumMap<>(AbeekType.class);
        for (Auditable area : areas) {
            requiredCredits.put(area.getAbeekType(), area.getRequiredCredits());
        }
        return requiredCredits;
    }
}
