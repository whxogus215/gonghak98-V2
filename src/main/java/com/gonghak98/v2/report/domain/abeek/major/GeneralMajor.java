package com.gonghak98.v2.report.domain.abeek.major;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.List;

public class GeneralMajor {

    private final List<Integer> courseIds;

    private final double minPoint;

    @JsonCreator
    public GeneralMajor(@JsonProperty("courseIds") final List<Integer> courseIds,
                        @JsonProperty("minPoint") final double minPoint) {
        this.courseIds = courseIds;
        this.minPoint = minPoint;
    }

    public boolean check(List<CompletedCourse> completedCourses) {
        double pointSum = 0.0;
        for (CompletedCourse course : completedCourses) {
            for (Integer courseId : courseIds) {
                if (courseId == course.getId()) {
                    pointSum += course.getPoint();
                }
            }
        }
        return pointSum >= minPoint;
    }
}
