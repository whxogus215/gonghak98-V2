package com.gonghak98.v2.report.domain.abeek.major;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class GeneralMajor {

    private final Set<String> courseCodes;

    private final double minPoint;

    @JsonCreator
    public GeneralMajor(@JsonProperty("courseIds") final Set<String> courseCodes,
                        @JsonProperty("minPoint") final double minPoint) {
        this.courseCodes = courseCodes;
        this.minPoint = minPoint;
    }

    public boolean check(List<CompletedCourse> completedCourses) {
        double pointSum = 0.0;
        for (CompletedCourse course : completedCourses) {
            for (String courseId : courseCodes) {
                if (Objects.equals(courseId, course.getCode())) {
                    pointSum += course.getCredit();
                }
            }
        }
        return pointSum >= minPoint;
    }
    
    public List<CompletedCourse> getRelatedCourses(List<CompletedCourse> completedCourses) {
        return completedCourses.stream()
                .filter(course -> courseCodes.contains(course.getCode()))
                .toList();
    }
}
