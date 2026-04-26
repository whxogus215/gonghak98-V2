package com.gonghak98.v2.report.domain.abeek.major;

import com.gonghak98.v2.report.domain.abeek.AbeekType;
import com.gonghak98.v2.report.domain.abeek.dto.AreaCheckResult;
import com.gonghak98.v2.report.domain.abeek.rule.Rule;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Major {

    private final List<Rule> rules;
    private final double minCredit;

    public void checkAllCourses(List<CompletedCourse> completedCourses, AreaCheckResult areaCheckResult) {
        boolean isSatisfied = rules.stream()
                                   .allMatch(rule -> rule.isSatisfied(completedCourses));

        double totalCredit = completedCourses.stream()
                                             .filter(course -> (course.getAbeekType() == AbeekType.MAJOR) || (course.getAbeekType() == AbeekType.DESIGN))
                                             .mapToDouble(CompletedCourse::getCredit)
                                             .sum();

        areaCheckResult.passResults().put(AbeekType.MAJOR, isSatisfied && (totalCredit >= minCredit));
    }

    public Double getRequiredCredits() {
        return minCredit;
    }
}
