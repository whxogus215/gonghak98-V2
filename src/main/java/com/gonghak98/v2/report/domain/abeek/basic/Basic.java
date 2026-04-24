package com.gonghak98.v2.report.domain.abeek.basic;

import com.gonghak98.v2.report.domain.abeek.AbeekType;
import com.gonghak98.v2.report.domain.abeek.dto.AreaCheckResult;
import com.gonghak98.v2.report.domain.abeek.rule.RequirementRule;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Basic {

    private final AbeekType abeekType;
    private final List<RequirementRule> rules;
    private final double minCredit;

    public void checkAllCourses(List<CompletedCourse> completedCourses, AreaCheckResult areaCheckResult) {
        boolean isSatisfied = rules.stream()
                                   .allMatch(rule -> rule.isSatisfied(completedCourses));

        areaCheckResult.passResults().put(abeekType, isSatisfied);
    }

    public List<CompletedCourse> getRelatedCourses(List<CompletedCourse> completedCourses) {
        return completedCourses.stream()
                               .filter(course -> course.getAbeekType().equals(abeekType))
                               .toList();
    }

    public AbeekType getBasicAreaType() {
        return abeekType;
    }

    public Double getRequiredCredits() {
        return minCredit;
    }
}
