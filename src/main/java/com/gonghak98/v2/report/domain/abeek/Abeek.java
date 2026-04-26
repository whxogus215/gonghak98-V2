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

        Map<AbeekType, List<CompletedCourse>> coursesByAbeekType = categorizeCompletedCourseByAbeekType(completedCourses);
        Map<AbeekType, Double> requiredCredits = collectRequiredCredits();
        CountingResult creditCountingResult = CreditCalculator.calculateCredits(coursesByAbeekType, requiredCredits);

        return new AbeekCheckResult(
            areaCheckResult.passResults(),
            areaCheckResult.nonPassResults(),
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
