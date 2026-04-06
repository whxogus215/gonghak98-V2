package com.gonghak98.v2.report.domain.abeek.major;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.List;
import java.util.Set;

public class LabMajor {

    private final Set<String> courseCodes;

    private final int minCount;

    @JsonCreator
    public LabMajor(@JsonProperty("courseIds") final Set<String> courseCodes,
                    @JsonProperty("minCount") final int minCount) {
        this.courseCodes = courseCodes;
        this.minCount = minCount;
    }

    public boolean check(List<CompletedCourse> completedCourses) {
        int count = 0;
        for (CompletedCourse course : completedCourses) {
            for (String courseId : courseCodes) {
                if (courseId.equals(course.getCode())) {
                    count++;
                }
            }
        }
        return count >= minCount;
    }

    public List<CompletedCourse> getRelatedCourses(List<CompletedCourse> completedCourses) {
        return completedCourses.stream()
                               .filter(course -> courseCodes.contains(course.getCode()))
                               .toList();
    }
}
