package com.gonghak98.v2.report.domain.abeek.design;

import com.gonghak98.v2.report.domain.abeek.AreaType;
import com.gonghak98.v2.report.domain.abeek.dto.RequirementResult;
import com.gonghak98.v2.report.domain.course.DesignCourse;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Design {

    private final DesignCourse basicDesignCourse;
    private final List<DesignCourse> elementDesignCourses;
    private final List<DesignCourse> comprehensiveDesignCourses;
    private final Set<Long> designCourseIds;

    private final double minDesignPoint;

    public Design(DesignCourse basicDesignCourse,
                  List<DesignCourse> elementDesignCourses,
                  List<DesignCourse> comprehensiveDesignCourses,
                  double minDesignPoint) {
        this.basicDesignCourse = basicDesignCourse;
        this.elementDesignCourses = elementDesignCourses;
        this.comprehensiveDesignCourses = comprehensiveDesignCourses;
        this.minDesignPoint = minDesignPoint;

        this.designCourseIds = new HashSet<>();
        if (basicDesignCourse != null) {
            this.designCourseIds.add(basicDesignCourse.getCourseId());
        }
        elementDesignCourses.forEach(c -> this.designCourseIds.add(c.getCourseId()));
        comprehensiveDesignCourses.forEach(c -> this.designCourseIds.add(c.getCourseId()));
    }

    public void checkAllCourses(List<CompletedCourse> studentCourses, RequirementResult requirementResult) {
        double designPointSum = 0.0;
        boolean isBasicPassed = false;
        boolean isComprehensivePassed = false;
        for (CompletedCourse course : studentCourses) {
            if (basicDesignCourse.isEqual(course.getId())) {
                isBasicPassed = true;
                designPointSum += basicDesignCourse.getDesignPoint();
            }
            for (DesignCourse elementDesignCourse : elementDesignCourses) {
                if (elementDesignCourse.isEqual(course.getId())) {
                    designPointSum += elementDesignCourse.getDesignPoint();
                }
            }
            for (DesignCourse comprehensiveDesignCourse : comprehensiveDesignCourses) {
                if (comprehensiveDesignCourse.isEqual(course.getId())) {
                    designPointSum += comprehensiveDesignCourse.getDesignPoint();
                    isComprehensivePassed = true;
                }
            }
        }
        boolean isAllSatisfied = isBasicPassed && isComprehensivePassed && (designPointSum >= minDesignPoint);

        requirementResult.passResults().put(AreaType.DESIGN, isAllSatisfied);
    }

    public List<CompletedCourse> getRelatedCourses(List<CompletedCourse> completedCourses) {
        return completedCourses.stream()
                               .filter(c -> isDesignCourse(c.getId()))
                               .toList();
    }

    private boolean isDesignCourse(Long courseId) {
        return designCourseIds.contains(courseId);
    }

    public Double getRequiredPoints() {
        return minDesignPoint;
    }
}
