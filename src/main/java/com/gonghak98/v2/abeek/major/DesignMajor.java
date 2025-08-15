package com.gonghak98.v2.abeek.major;

import com.gonghak98.v2.student.CompletedCourse;
import com.gonghak98.v2.course.DesignCourse;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DesignMajor {

    private final DesignCourse basicDesignCourse;
    private final List<DesignCourse> elementDesignCourses;
    private final List<DesignCourse> comprehensiveDesignCourses;

    private final double minDesignPoint;

    public boolean check(List<CompletedCourse> studentCourses) {
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

        return isBasicPassed && isComprehensivePassed && designPointSum >= minDesignPoint;
    }
}
