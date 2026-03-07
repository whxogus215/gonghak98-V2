package com.gonghak98.v2.report.domain.abeek.prerequisite;

import com.gonghak98.v2.report.domain.student.CompletedCourse;

public class PrerequisiteChecker {

    private PrerequisiteChecker() {
    }

    public static boolean isSatisfiedPrerequisite(CompletedCourse before, CompletedCourse after) {
        return before.compareTo(after) < 0; // 선수 과목이 후수 과목보다 더 앞서야 선후수 조건을 만족
    }
}
