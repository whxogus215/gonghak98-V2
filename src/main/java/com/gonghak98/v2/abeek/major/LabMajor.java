package com.gonghak98.v2.abeek.major;

import com.gonghak98.v2.student.CompletedCourse;
import com.gonghak98.v2.course.Course;
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
