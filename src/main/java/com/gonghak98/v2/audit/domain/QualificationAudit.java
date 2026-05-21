package com.gonghak98.v2.audit.domain;

import com.gonghak98.v2.audit.domain.abeek.AbeekAreaAudit;
import com.gonghak98.v2.audit.domain.constant.AbeekType;
import com.gonghak98.v2.audit.domain.dto.AbeekAreaAuditResult;
import com.gonghak98.v2.audit.domain.dto.AuditCompletedCourse;
import com.gonghak98.v2.audit.domain.dto.NonPassResult;
import com.gonghak98.v2.audit.domain.dto.PrerequisiteAuditResult;
import com.gonghak98.v2.audit.domain.dto.QualificationResult;
import com.gonghak98.v2.audit.domain.prerequisite.PrerequisiteAudit;
import com.gonghak98.v2.audit.domain.counting.CreditCalculator;
import com.gonghak98.v2.audit.domain.counting.dto.CountingResult;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class QualificationAudit {

    private final AbeekAreaAudit abeekAreaAudit;
    private final PrerequisiteAudit prerequisiteAudit;

    public QualificationResult getQualificationResult(List<AuditCompletedCourse> auditCompletedCourses) {
        AbeekAreaAuditResult abeekAreaAuditResult = abeekAreaAudit.auditAbeekArea(auditCompletedCourses);
        Map<AbeekType, Double> requiredCredits = abeekAreaAudit.getRequiredCredits();

        PrerequisiteAuditResult prerequisiteAuditResult = prerequisiteAudit.auditPrerequisite(auditCompletedCourses);

        Map<AbeekType, List<AuditCompletedCourse>> coursesByAbeekType = categorizeCompletedCourseByAbeekType(auditCompletedCourses);

        CountingResult creditCountingResult = CreditCalculator.calculateCredits(coursesByAbeekType, requiredCredits);
        Map<AbeekType, Boolean> passResults = new EnumMap<>(AbeekType.class);

        // ABEEK 영역 검사를 먼저하고, 선후수 검사를 마지막에 진행 -> 선후수 조건을 만족하지 못할 경우, 미이수로 처리하기 때문
        passResults.putAll(abeekAreaAuditResult.passResults());
        List<NonPassResult> nonPassResults = new ArrayList<>(abeekAreaAuditResult.nonPassResults());

        for (Entry<AbeekType, Boolean> prerequisiteEntry : prerequisiteAuditResult.passResults().entrySet()) {
            AbeekType targetAbeekType = prerequisiteEntry.getKey();
            if (passResults.get(targetAbeekType) == Boolean.TRUE && prerequisiteEntry.getValue() == Boolean.FALSE) {
                passResults.put(targetAbeekType, Boolean.FALSE);
            }
        }
        nonPassResults.addAll(prerequisiteAuditResult.nonPassResults());

        return new QualificationResult(passResults,
                                       nonPassResults,
                                       creditCountingResult.creditSummaries()
        );
    }

    private Map<AbeekType, List<AuditCompletedCourse>> categorizeCompletedCourseByAbeekType(List<AuditCompletedCourse> completedCourses) {
        Map<AbeekType, List<AuditCompletedCourse>> coursesByAbeekType = new EnumMap<>(AbeekType.class);
        for (AuditCompletedCourse course : completedCourses) {
            AbeekType abeekType = course.abeekType();
            if (abeekType == AbeekType.DESIGN) {
                // 설계 기이수 과목은 전공 영역에도 포함
                coursesByAbeekType.computeIfAbsent(AbeekType.MAJOR, k -> new ArrayList<>()).add(course);
            }
            coursesByAbeekType.computeIfAbsent(abeekType, k -> new ArrayList<>()).add(course);
        }
        return coursesByAbeekType;
    }
}
