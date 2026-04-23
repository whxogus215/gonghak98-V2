package com.gonghak98.v2.report.domain.counting;

import com.gonghak98.v2.report.domain.abeek.AbeekType;
import com.gonghak98.v2.report.domain.counting.dto.CountingResult;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class CreditCalculator {

    private CreditCalculator() {
    }

    public static CountingResult calculateCredits(Map<AbeekType, List<CompletedCourse>> coursesByArea,
                                                  Map<AbeekType, Double> requiredPoints) {
        Map<AbeekType, AreaCreditSummary> summaries = new EnumMap<>(AbeekType.class);

        for (AbeekType abeekType : AbeekType.values()) {
            if (!coursesByArea.containsKey(abeekType)) {
                continue;
            }
            List<CompletedCourse> areaCourses = coursesByArea.get(abeekType);
            double completedCredits = calculateTotalCredits(areaCourses, abeekType);
            double requiredPoint = requiredPoints.getOrDefault(abeekType, 0.0);

            CreditCountResult creditCountResult = new CreditCountResult(completedCredits, requiredPoint);
            AreaCreditSummary summary = new AreaCreditSummary(abeekType, creditCountResult, areaCourses);

            summaries.put(abeekType, summary);
        }

        return new CountingResult(summaries);
    }

    private static double calculateTotalCredits(List<CompletedCourse> courses, AbeekType abeekType) {
        if (abeekType.equals(AbeekType.DESIGN)) {
            return courses.stream()
                          .mapToDouble(CompletedCourse::getDesignCredit)
                          .sum();
        } else {
            return courses.stream()
                          .mapToDouble(CompletedCourse::getCredit)
                          .sum();
        }
    }
}
