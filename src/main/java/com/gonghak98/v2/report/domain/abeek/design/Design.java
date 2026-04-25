package com.gonghak98.v2.report.domain.abeek.design;

import com.gonghak98.v2.report.domain.abeek.AbeekType;
import com.gonghak98.v2.report.domain.abeek.dto.AreaCheckResult;
import com.gonghak98.v2.report.domain.course.DesignCourse;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Design {

    private final DesignCourse basicDesignCourse;
    private final List<DesignCourse> elementDesignCourses;
    private final List<DesignCourse> comprehensiveDesignCourses;
    private final Set<String> courseCodes;

    private final double minDesignCredit;

    public Design(DesignCourse basicDesignCourse,
                  List<DesignCourse> elementDesignCourses,
                  List<DesignCourse> comprehensiveDesignCourses,
                  double minDesignCredit) {
        this.basicDesignCourse = basicDesignCourse;
        this.elementDesignCourses = elementDesignCourses;
        this.comprehensiveDesignCourses = comprehensiveDesignCourses;
        this.minDesignCredit = minDesignCredit;

        this.courseCodes = new HashSet<>();
        if (basicDesignCourse != null) {
            this.courseCodes.add(basicDesignCourse.getCourseCode());
        }
        elementDesignCourses.forEach(c -> this.courseCodes.add(c.getCourseCode()));
        comprehensiveDesignCourses.forEach(c -> this.courseCodes.add(c.getCourseCode()));
    }

    public void checkAllCourses(List<CompletedCourse> studentCourses, AreaCheckResult areaCheckResult) {
        double designPointSum = 0.0;
        boolean isBasicPassed = false;
        boolean isComprehensivePassed = false;
        for (CompletedCourse course : studentCourses) {
            if (basicDesignCourse.isEqual(course.getCode())) {
                isBasicPassed = true;
                designPointSum += basicDesignCourse.getDesignPoint();
                course.setDesignCredit(basicDesignCourse.getDesignPoint());
            }
            for (DesignCourse elementDesignCourse : elementDesignCourses) {
                if (elementDesignCourse.isEqual(course.getCode())) {
                    designPointSum += elementDesignCourse.getDesignPoint();
                    course.setDesignCredit(elementDesignCourse.getDesignPoint());
                }
            }
            for (DesignCourse comprehensiveDesignCourse : comprehensiveDesignCourses) {
                if (comprehensiveDesignCourse.isEqual(course.getCode())) {
                    designPointSum += comprehensiveDesignCourse.getDesignPoint();
                    course.setDesignCredit(comprehensiveDesignCourse.getDesignPoint());
                    isComprehensivePassed = true;
                }
            }
        }
        boolean isAllSatisfied = isBasicPassed && isComprehensivePassed && (designPointSum >= minDesignCredit);

        areaCheckResult.passResults().put(AbeekType.DESIGN, isAllSatisfied);
    }

    public List<CompletedCourse> getRelatedCourses(List<CompletedCourse> completedCourses) {
        return completedCourses.stream()
                               .filter(c -> courseCodes.contains(c.getCode()))
                               .toList();
    }

    public Double getRequiredCredits() {
        return minDesignCredit;
    }
}
