package com.gonghak98.v2.report.domain.counting;

import com.gonghak98.v2.report.domain.abeek.AreaType;
import com.gonghak98.v2.report.domain.counting.dto.CountingResult;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class PointCalculator {

    private PointCalculator() {}

    public static CountingResult calculateCredits(Map<AreaType, List<CompletedCourse>> coursesByArea,
                                                  Map<AreaType, Double> requiredPoints) {
        Map<AreaType, AreaCreditSummary> summaries = new EnumMap<>(AreaType.class);

        for (AreaType areaType : AreaType.values()) {
            List<CompletedCourse> areaCourses = coursesByArea.getOrDefault(areaType, List.of());
            double completedCredits = calculateTotalCredits(areaCourses);
            double requiredPoint = requiredPoints.getOrDefault(areaType, 0.0);

            PointCountResult pointCountResult = new PointCountResult(completedCredits, requiredPoint);
            AreaCreditSummary summary = new AreaCreditSummary(areaType, pointCountResult, areaCourses);

            summaries.put(areaType, summary);
        }

        return new CountingResult(summaries);
    }

    private static double calculateTotalCredits(List<CompletedCourse> courses) {
        return courses.stream()
                      .mapToDouble(CompletedCourse::getPoint)
                      .sum();
    }
}
