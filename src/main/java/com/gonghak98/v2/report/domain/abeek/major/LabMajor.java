package com.gonghak98.v2.report.domain.abeek.major;

import com.gonghak98.v2.report.domain.student.CompletedCourse;
import com.gonghak98.v2.report.domain.course.Course;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LabMajor {

    private final List<Course> essentialLabCourses;

    private final int minCount;

    public boolean check(List<CompletedCourse> completedCourses) {
        int count = 0;
        for (CompletedCourse course : completedCourses) {
            for (Course essentialLabCourse : essentialLabCourses) {
                if (essentialLabCourse.isEqual(course.getId())) {
                    count++;
                }
            }
        }
        return count >= minCount;
    }

}
