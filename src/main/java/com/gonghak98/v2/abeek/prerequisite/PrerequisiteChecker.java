package com.gonghak98.v2.abeek.prerequisite;

import com.gonghak98.v2.student.CompletedCourse;

public class PrerequisiteChecker {

    public static boolean isSatisfiedPrerequisite(CompletedCourse before,CompletedCourse after) {
        return before.getYear() > after.getYear() || before.getSemester() > after.getSemester();
    }
}
