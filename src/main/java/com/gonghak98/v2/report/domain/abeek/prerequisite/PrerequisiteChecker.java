package com.gonghak98.v2.report.domain.abeek.prerequisite;

import com.gonghak98.v2.report.domain.student.CompletedCourse;

public class PrerequisiteChecker {

    private PrerequisiteChecker() {
    }

    public static boolean isSatisfiedPrerequisite(CompletedCourse before, CompletedCourse after) {
        return before.getYear() > after.getYear() || before.getSemester() > after.getSemester();
    }
}
