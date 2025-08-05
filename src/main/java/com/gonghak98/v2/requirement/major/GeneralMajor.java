package com.gonghak98.v2.requirement.major;

import com.gonghak98.v2.completedcourse.CompletedCourse;
import com.gonghak98.v2.course.Course;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GeneralMajor {

    private final List<Course> essentialCourses;

    private final double minPoint;

    public boolean check(List<CompletedCourse> courses) {
        double pointSum = 0.0;
        for (CompletedCourse course : courses) {
            for (Course essentialCourse : essentialCourses) {
                if (essentialCourse.isEqual(course.getId())) {
                    course.pass();
                    pointSum += course.getPoint();
                }
            }
        }
        return pointSum >= minPoint;
    }
}
