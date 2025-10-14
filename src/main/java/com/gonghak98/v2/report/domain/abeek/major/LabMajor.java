package com.gonghak98.v2.report.domain.abeek.major;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.List;

public class LabMajor {

    private final List<Integer> courseIds;

    private final int minCount;

    public boolean check(List<CompletedCourse> completedCourses) {
        int count = 0;
        for (CompletedCourse course : completedCourses) {
            for (Integer courseId : courseIds) {
                if (courseId == course.getId()) {
                    count++;
                }
            }
        }
        return count >= minCount;
    }

    @JsonCreator
    public LabMajor(@JsonProperty("courseIds") final List<Integer> courseIds,
                    @JsonProperty("minCount") final int minCount) {
        this.courseIds = courseIds;
        this.minCount = minCount;
    }
}
