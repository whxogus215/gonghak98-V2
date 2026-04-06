package com.gonghak98.v2.report.domain.abeek.major;

import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LabMajor {

    private final Set<String> courseCodes;

    private final int minCount;

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
