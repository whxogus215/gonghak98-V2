package com.gonghak98.v2.report.domain.abeek;

import com.gonghak98.v2.report.domain.abeek.basic.Basic;
import com.gonghak98.v2.report.domain.abeek.design.Design;
import com.gonghak98.v2.report.domain.abeek.dto.AbeekCheckResult;
import com.gonghak98.v2.report.domain.abeek.dto.AreaCheckResult;
import com.gonghak98.v2.report.domain.abeek.dto.NotCheckedResult;
import com.gonghak98.v2.report.domain.abeek.gyoyang.Gyoyang;
import com.gonghak98.v2.report.domain.abeek.major.Major;
import com.gonghak98.v2.report.domain.abeek.prerequisite.Prerequisite;
import com.gonghak98.v2.report.domain.counting.CreditCalculator;
import com.gonghak98.v2.report.domain.counting.dto.CountingResult;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

        Map<AbeekType, List<CompletedCourse>> coursesByArea = categorizeCompletedCourses(completedCourses, areaCheckResult);
        Map<AbeekType, Double> requiredCredits = collectRequiredCredits();
        CountingResult creditCountingResult = CreditCalculator.calculateCredits(coursesByArea, requiredCredits);

        return new AbeekCheckResult(
            areaCheckResult.passResults(),
            areaCheckResult.nonPassResults(),
            areaCheckResult.notCheckedResults(),
            creditCountingResult.creditSummaries()
        );
    }

    private AreaCheckResult checkAreaRequirements(List<CompletedCourse> completedCourses) {
        AreaCheckResult areaCheckResult = new AreaCheckResult(new EnumMap<>(AbeekType.class),
                                                              new ArrayList<>(),
                                                              new ArrayList<>());

        gyoyang.checkAllCourses(completedCourses, areaCheckResult);
        basic.checkAllCourses(completedCourses, areaCheckResult);
        major.checkAllCourses(completedCourses, areaCheckResult);
        design.checkAllCourses(completedCourses, areaCheckResult);
        prerequisite.checkAllCourses(completedCourses, areaCheckResult);

        return areaCheckResult;
    }

    private Map<AbeekType, List<CompletedCourse>> categorizeCompletedCourses(List<CompletedCourse> completedCourses,
                                                                             AreaCheckResult areaCheckResult) {
        Map<AbeekType, List<CompletedCourse>> coursesByArea = new EnumMap<>(AbeekType.class);

        coursesByArea.put(AbeekType.GYOYANG, gyoyang.getRelatedCourses(completedCourses));
        coursesByArea.put(AbeekType.MAJOR, major.getRelatedCourses(completedCourses));
        coursesByArea.put(AbeekType.DESIGN, design.getRelatedCourses(completedCourses));

        AbeekType basicAbeekType = basic.getBasicAreaType();
        coursesByArea.put(basicAbeekType, basic.getRelatedCourses(completedCourses));

        // 기이수 과목 중 Pass/NonPass에 속하지 않는 과목들을 존재하지 않는 과목으로 분류 (NonChecked)
        Set<CompletedCourse> categorizedCompletedCourses = coursesByArea.values().stream()
                                                                        .flatMap(Collection::stream)
                                                                        .collect(Collectors.toSet());
        for (CompletedCourse course : completedCourses) {
            if (!categorizedCompletedCourses.contains(course)) {
                areaCheckResult.notCheckedResults().add(NotCheckedResult.from(course));
            }
        }
        return coursesByArea;
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
