package com.gonghak98.v2.report.domain.abeek;

import com.gonghak98.v2.report.domain.abeek.basic.Basic;
import com.gonghak98.v2.report.domain.abeek.design.Design;
import com.gonghak98.v2.report.domain.abeek.dto.CheckResult;
import com.gonghak98.v2.report.domain.abeek.dto.RequirementResult;
import com.gonghak98.v2.report.domain.abeek.gyoyang.Gyoyang;
import com.gonghak98.v2.report.domain.abeek.major.Major;
import com.gonghak98.v2.report.domain.abeek.prerequisite.Prerequisite;
import com.gonghak98.v2.report.domain.counting.PointCalculator;
import com.gonghak98.v2.report.domain.counting.dto.CountingResult;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Abeek {

    private final Gyoyang gyoyang;
    private final Basic basic;
    private final Major major;
    private final Design design;
    private final Prerequisite prerequisite;

    public CheckResult checkAllCourses(List<CompletedCourse> completedCourses) {
        RequirementResult requirementResult = checkAreaRequirements(completedCourses);

        Map<AreaType, List<CompletedCourse>> coursesByArea = categorizeCompletedCourses(completedCourses);
        Map<AreaType, Double> requiredCredits = collectRequiredPoints();
        CountingResult creditCountingResult = PointCalculator.calculateCredits(coursesByArea, requiredCredits);

        return new CheckResult(
            requirementResult.passResults(),
            requirementResult.nonPassResults(),
            creditCountingResult.creditSummaries()
        );
    }

    private RequirementResult checkAreaRequirements(List<CompletedCourse> completedCourses) {
        RequirementResult requirementResult = new RequirementResult(new EnumMap<>(AreaType.class), new HashMap<>());

        gyoyang.checkAllCourses(completedCourses, requirementResult);
        basic.checkAllCourses(completedCourses, requirementResult);
        major.checkAllCourses(completedCourses, requirementResult);
        design.checkAllCourses(completedCourses, requirementResult);
        prerequisite.checkAllCourses(completedCourses, requirementResult);

        return requirementResult;
    }

    private Map<AreaType, List<CompletedCourse>> categorizeCompletedCourses(List<CompletedCourse> completedCourses) {
        Map<AreaType, List<CompletedCourse>> coursesByArea = new EnumMap<>(AreaType.class);

        coursesByArea.put(AreaType.GYOYANG, gyoyang.getRelatedCourses(completedCourses));
        coursesByArea.put(AreaType.MAJOR, major.getRelatedCourses(completedCourses));
        coursesByArea.put(AreaType.DESIGN, design.getRelatedCourses(completedCourses));

        AreaType basicAreaType = basic.getBasicAreaType();
        coursesByArea.put(basicAreaType, basic.getRelatedCourses(completedCourses));

        return coursesByArea;
    }

    private Map<AreaType, Double> collectRequiredPoints() {
        Map<AreaType, Double> requiredPoints = new EnumMap<>(AreaType.class);

        requiredPoints.put(AreaType.GYOYANG, gyoyang.getRequiredPoints());
        requiredPoints.put(AreaType.MAJOR, major.getRequiredPoints());
        requiredPoints.put(AreaType.DESIGN, design.getRequiredPoints());

        AreaType basicAreaType = basic.getBasicAreaType();
        requiredPoints.put(basicAreaType, basic.getRequiredPoints());

        return requiredPoints;
    }
}
