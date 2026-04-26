package com.gonghak98.v2.report.domain.abeek;

import com.gonghak98.v2.report.domain.abeek.basic.Basic;
import com.gonghak98.v2.report.domain.abeek.design.Design;
import com.gonghak98.v2.report.domain.abeek.dto.AbeekCheckResult;
import com.gonghak98.v2.report.domain.abeek.dto.AreaCheckResult;
import com.gonghak98.v2.report.domain.abeek.gyoyang.Gyoyang;
import com.gonghak98.v2.report.domain.abeek.major.Major;
import com.gonghak98.v2.report.domain.abeek.prerequisite.Prerequisite;
import com.gonghak98.v2.report.domain.counting.CreditCalculator;
import com.gonghak98.v2.report.domain.counting.dto.CountingResult;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Abeek {

    private final Gyoyang gyoyang;
    private final Basic basic;
    private final Major major;
    private final Design design;
    private final Prerequisite prerequisite;

    public AbeekCheckResult checkAllCourses(List<CompletedCourse> completedCourses) {
        AreaCheckResult areaCheckResult = checkAreaRequirements(completedCourses);

        Map<AbeekType, List<CompletedCourse>> coursesByAbeekType = completedCourses.stream()
                                                                                   .collect(Collectors.groupingBy(CompletedCourse::getAbeekType));
        Map<AbeekType, Double> requiredCredits = collectRequiredCredits();
        CountingResult creditCountingResult = CreditCalculator.calculateCredits(coursesByAbeekType, requiredCredits);

        return new AbeekCheckResult(
            areaCheckResult.passResults(),
            areaCheckResult.nonPassResults(),
            creditCountingResult.creditSummaries()
        );
    }

    private AreaCheckResult checkAreaRequirements(List<CompletedCourse> completedCourses) {
        AreaCheckResult areaCheckResult = new AreaCheckResult(new EnumMap<>(AbeekType.class),
                                                              new ArrayList<>());

        gyoyang.checkAllCourses(completedCourses, areaCheckResult);
        basic.checkAllCourses(completedCourses, areaCheckResult);
        major.checkAllCourses(completedCourses, areaCheckResult);
        design.checkAllCourses(completedCourses, areaCheckResult);
        prerequisite.checkAllCourses(completedCourses, areaCheckResult);

        return areaCheckResult;
    }

    private Map<AbeekType, Double> collectRequiredCredits() {
        Map<AbeekType, Double> requiredPoints = new EnumMap<>(AbeekType.class);

        requiredPoints.put(AbeekType.GYOYANG, gyoyang.getRequiredCredits());
        requiredPoints.put(AbeekType.MAJOR, major.getRequiredCredits());
        requiredPoints.put(AbeekType.DESIGN, design.getRequiredCredits());

        AbeekType basicAbeekType = basic.getBasicAreaType();
        requiredPoints.put(basicAbeekType, basic.getRequiredCredits());

        return requiredPoints;
    }
}
