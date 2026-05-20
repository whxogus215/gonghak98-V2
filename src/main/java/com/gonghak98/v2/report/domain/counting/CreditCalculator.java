package com.gonghak98.v2.report.domain.counting;

import com.gonghak98.v2.audit.domain.constant.AbeekType;
import com.gonghak98.v2.audit.domain.dto.AuditCompletedCourse;
import com.gonghak98.v2.report.domain.counting.dto.CountingResult;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class CreditCalculator {

    private CreditCalculator() {
    }

    public static CountingResult calculateCredits(Map<AbeekType, List<AuditCompletedCourse>> coursesByAbeekType,
                                                  Map<AbeekType, Double> requiredCredits) {
        Map<AbeekType, AreaCreditSummary> summaries = new EnumMap<>(AbeekType.class);

        for (AbeekType abeekType : AbeekType.values()) {
            if (!coursesByAbeekType.containsKey(abeekType)) {
                continue;
            }
            List<AuditCompletedCourse> mappedAbeekTypeCourses = coursesByAbeekType.get(abeekType);
            double completedCredits = calculateTotalCredits(mappedAbeekTypeCourses, abeekType);
            double requiredCredit = requiredCredits.getOrDefault(abeekType, 0.0);

            CreditCountResult creditCountResult = new CreditCountResult(completedCredits, requiredCredit);
            AreaCreditSummary summary = new AreaCreditSummary(abeekType, creditCountResult, mappedAbeekTypeCourses);

            summaries.put(abeekType, summary);
        }

        return new CountingResult(summaries);
    }

    private static double calculateTotalCredits(List<AuditCompletedCourse> courses, AbeekType abeekType) {
        if (abeekType.equals(AbeekType.DESIGN)) {
            return courses.stream()
                          .mapToDouble(AuditCompletedCourse::designCredit)
                          .sum();
        } else {
            return courses.stream()
                          .mapToDouble(AuditCompletedCourse::credit)
                          .sum();
        }
    }
}
