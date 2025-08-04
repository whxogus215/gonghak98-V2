package com.gonghak98.v2.requirement.major;

import com.gonghak98.v2.completedcourse.CompletedCourse;
import java.util.List;
import java.util.Set;

public class GeneralMajor {

    private final Set<String> essentialCourseNames;

    private final double minPoint;

    public GeneralMajor(Set<String> essentialCourseNames, double minPoint) {
        this.essentialCourseNames = essentialCourseNames;
        this.minPoint = minPoint;
    }

    public boolean check(List<CompletedCourse> courses) {
        double pointSum = 0.0;
        for (CompletedCourse course : courses) {
            if (essentialCourseNames.contains(course.getName())) {
                course.pass();
                pointSum += course.getPoint();
            }
        }
        return pointSum >= minPoint;
    }
}
