package com.gonghak98.v2.audit.domain;

import com.gonghak98.v2.audit.domain.abeek.AbeekAreaAudit;
import com.gonghak98.v2.audit.domain.constant.AbeekType;
import com.gonghak98.v2.audit.domain.dto.AbeekAreaAuditResult;
import com.gonghak98.v2.audit.domain.dto.NonPassResult;
import com.gonghak98.v2.audit.domain.dto.PrerequisiteAuditResult;
import com.gonghak98.v2.audit.domain.dto.QualificationResult;
import com.gonghak98.v2.audit.domain.prerequisite.PrerequisiteAudit;
import com.gonghak98.v2.report.domain.counting.CreditCalculator;
import com.gonghak98.v2.report.domain.counting.dto.CountingResult;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
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

    public QualificationResult getQualificationResult(List<CompletedCourse> userCourses) {
        AbeekAreaAuditResult abeekAreaAuditResult = abeekAreaAudit.auditAbeekArea(userCourses);
        Map<AbeekType, Double> requiredCredits = abeekAreaAudit.getRequiredCredits();

        PrerequisiteAuditResult prerequisiteAuditResult = prerequisiteAudit.auditPrerequisite(userCourses);

        Map<AbeekType, List<CompletedCourse>> coursesByAbeekType = categorizeCompletedCourseByAbeekType(userCourses);
        // TODO : counting 도메인과의 순환참조 해결하기
        CountingResult creditCountingResult = CreditCalculator.calculateCredits(coursesByAbeekType, requiredCredits);

        // TODO : counting 의존관계 제거 후, 리팩토링하기
        Map<AbeekType, Boolean> passResults = new EnumMap<>(AbeekType.class);

        // ABEEK 영역 검사를 먼저하고, 선후수 검사를 마지막에 진행 -> 선후수 조건을 만족하지 못할 경우, 미이수로 처리하기 때문
        passResults.putAll(abeekAreaAuditResult.passResults());
        List<NonPassResult> nonPassResults = new ArrayList<>(abeekAreaAuditResult.nonPassResults());

        for (Entry<AbeekType, Boolean> prerequisiteEntry : prerequisiteAuditResult.passResults().entrySet()) {
            AbeekType targetAbeekType = prerequisiteEntry.getKey();
            if (passResults.get(targetAbeekType) == Boolean.TRUE && prerequisiteEntry.getValue() == Boolean.FALSE) {
                passResults.put(targetAbeekType,Boolean.FALSE);
            }
        }
        nonPassResults.addAll(prerequisiteAuditResult.nonPassResults());

        return new QualificationResult(passResults,
                                       nonPassResults,
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
}
