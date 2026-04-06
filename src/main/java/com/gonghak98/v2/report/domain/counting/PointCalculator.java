package com.gonghak98.v2.report.domain.counting;

import com.gonghak98.v2.report.domain.abeek.AbeekType;
import com.gonghak98.v2.report.domain.counting.dto.CountingResult;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class PointCalculator {

    private PointCalculator() {}

    public static CountingResult calculateCredits(Map<AbeekType, List<CompletedCourse>> coursesByArea,
                                                  Map<AbeekType, Double> requiredPoints) {
        Map<AbeekType, AreaCreditSummary> summaries = new EnumMap<>(AbeekType.class);

        for (AbeekType abeekType : AbeekType.values()) {
            if (!coursesByArea.containsKey(abeekType)) {
                continue;
            }
            List<CompletedCourse> areaCourses = coursesByArea.get(abeekType);
            double completedCredits = calculateTotalCredits(areaCourses);
            double requiredPoint = requiredPoints.getOrDefault(abeekType, 0.0);

            PointCountResult pointCountResult = new PointCountResult(completedCredits, requiredPoint);
            AreaCreditSummary summary = new AreaCreditSummary(abeekType, pointCountResult, areaCourses);

            summaries.put(abeekType, summary);
        }

        return new CountingResult(summaries);
    }

    private static double calculateTotalCredits(List<CompletedCourse> courses) {
        return courses.stream()
                      .mapToDouble(CompletedCourse::getPoint)
                      .sum();
    }
}
