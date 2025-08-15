package com.gonghak98.v2.abeek.major;

import com.gonghak98.v2.student.CompletedCourse;
import com.gonghak98.v2.course.Course;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GeneralMajor {

    private final List<Course> essentialCourses;

    private final double minPoint;

    public boolean check(List<CompletedCourse> completedCourses) {
        double pointSum = 0.0;
        for (CompletedCourse course : completedCourses) {
            for (Course essentialCourse : essentialCourses) {
                if (essentialCourse.isEqual(course.getId())) {
                    pointSum += course.getPoint();
                }
            }
        }
        return pointSum >= minPoint;
    }
}
