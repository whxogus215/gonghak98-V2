package com.gonghak98.v2.report.domain.abeek.basic;

import com.gonghak98.v2.report.domain.abeek.AbeekType;
import com.gonghak98.v2.report.domain.abeek.dto.RequirementResult;
import com.gonghak98.v2.report.domain.abeek.rule.RequirementRule;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Basic {

    private final AbeekType abeekType;
    private final List<RequirementRule> rules;
    private final double minCredit;

    public void checkAllCourses(List<CompletedCourse> completedCourses, RequirementResult requirementResult) {
        boolean isSatisfied = rules.stream()
                                   .allMatch(rule -> rule.isSatisfied(completedCourses));

        requirementResult.passResults().put(abeekType, isSatisfied);
    }

    public List<CompletedCourse> getRelatedCourses(List<CompletedCourse> completedCourses) {
        Set<String> allTargetCourseCodes = rules.stream()
                                                .flatMap(rule -> rule.getTargetCourseCodes().stream())
                                                .collect(Collectors.toSet());
        return completedCourses.stream()
                               .filter(course -> allTargetCourseCodes.contains(course.getCode()))
                               .toList();
    }

    public AbeekType getBasicAreaType() {
        return abeekType;
    }

    public Double getRequiredPoints() {
        return minCredit;
    }
}
