package com.gonghak98.v2.report.domain.abeek.design;

import com.gonghak98.v2.report.domain.abeek.AreaType;
import com.gonghak98.v2.report.domain.abeek.dto.RequirementResult;
import com.gonghak98.v2.report.domain.course.DesignCourse;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Design {

    private final DesignCourse basicDesignCourse;
    private final List<DesignCourse> elementDesignCourses;
    private final List<DesignCourse> comprehensiveDesignCourses;

    private final double minDesignPoint;

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
        List<CompletedCourse> relatedCourses = new ArrayList<>();
        for (CompletedCourse course : completedCourses) {
            if (isDesignCourse(course.getId())) {
                relatedCourses.add(course);
            }
        }
        return relatedCourses;
    }

    private boolean isDesignCourse(int courseId) {
        if (basicDesignCourse.isEqual(courseId)) {
            return true;
        }
        for (DesignCourse designCourse : elementDesignCourses) {
            if (designCourse.isEqual(courseId)) {
                return true;
            }
        }
        for (DesignCourse designCourse : comprehensiveDesignCourses) {
            if (designCourse.isEqual(courseId)) {
                return true;
            }
        }
        return false;
    }

    public Double getRequiredPoints() {
        return minDesignPoint;
    }
}
