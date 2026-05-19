package com.gonghak98.v2.audit.domain.prerequisite;

import com.gonghak98.v2.core.domain.course.CompletedCourse;

public class PrerequisiteChecker {

    private PrerequisiteChecker() {
    }

    public static boolean isSatisfiedPrerequisite(CompletedCourse before, CompletedCourse after) {
        return before.compareTo(after) < 0; // 선수 과목이 후수 과목보다 더 앞서야 선후수 조건을 만족
    }

    public static boolean isSatisfiedDesignPrerequisite(CompletedCourse before, CompletedCourse after) {
        return before.compareTo(after) <= 0; // 설계 과목의 경우, 요소 설계와 종합 설계를 동시 수강이 가능
    }
}
