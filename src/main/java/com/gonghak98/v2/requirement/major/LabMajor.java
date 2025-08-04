package com.gonghak98.v2.requirement.major;

import com.gonghak98.v2.completedcourse.CompletedCourse;
import java.util.List;
import java.util.Set;

public class LabMajor {

    private Set<String> essentialLabCourseNames;

    private int minCount;

    public LabMajor(Set<String> essentialLabCourseNames, int minCount) {
        this.essentialLabCourseNames = essentialLabCourseNames;
        this.minCount = minCount;
    }

    public boolean check(List<CompletedCourse> courses) {
        int count = 0;
        for (CompletedCourse course : courses) {
            if (essentialLabCourseNames.contains(course.getCourseName())) {
                course.pass();
                count++;
            }
        }
        return count >= minCount;
    }

}
