package com.gonghak98.v2.requirement.major;

import com.gonghak98.v2.completedcourse.CompletedCourse;
import com.gonghak98.v2.course.Course;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LabMajor {

    private final List<Course> essentialLabCourses;

    private final int minCount;

    public boolean check(List<CompletedCourse> courses) {
        int count = 0;
        for (CompletedCourse course : courses) {
            for (Course essentialLabCourse : essentialLabCourses) {
                if (essentialLabCourse.isEqual(course.getId())) {
                    course.pass();
                    count++;
                }
            }
        }
        return count >= minCount;
    }

}
