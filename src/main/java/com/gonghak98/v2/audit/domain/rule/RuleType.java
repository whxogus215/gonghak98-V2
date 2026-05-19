package com.gonghak98.v2.audit.domain.rule;

import com.gonghak98.v2.core.domain.course.CompletedCourse;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public enum RuleType {

    MIN_CREDIT {
        @Override
        public boolean check(Set<String> targetCourseCodes, int conditionValue, List<CompletedCourse> completedCourses) {
            double totalCredits = completedCourses.stream()
                .filter(course -> targetCourseCodes.contains(course.getCode()))
                .mapToDouble(CompletedCourse::getCredit)
                .sum();

            return totalCredits >= conditionValue;
        }
    },

    MIN_COUNT {
        @Override
        public boolean check(Set<String> targetCourseCodes, int conditionValue, List<CompletedCourse> completedCourses) {
            long count = completedCourses.stream()
                .filter(course -> targetCourseCodes.contains(course.getCode()))
                .count();

            return count >= conditionValue;
        }
    },

    MUST_TAKE_ALL {
        @Override
        public boolean check(Set<String> targetCourseCodes, int conditionValue, List<CompletedCourse> completedCourses) {
            final Set<String> completedCodes = completedCourses.stream()
                                                        .map(CompletedCourse::getCode)
                                                        .collect(Collectors.toSet());
            return completedCodes.containsAll(targetCourseCodes);
        }
    };

    public abstract boolean check(Set<String> targetCourseCodes, int conditionValue, List<CompletedCourse> completedCourses);
}
