package com.gonghak98.v2.audit.domain.rule;

import com.gonghak98.v2.audit.domain.dto.AuditCompletedCourse;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public enum RuleType {

    MIN_CREDIT {
        @Override
        public boolean check(Set<String> targetCourseCodes, int conditionValue, List<AuditCompletedCourse> auditCompletedCourses) {
            double totalCredits = auditCompletedCourses.stream()
                                                       .filter(course -> targetCourseCodes.contains(course.code()))
                                                       .mapToDouble(AuditCompletedCourse::credit)
                                                       .sum();

            return totalCredits >= conditionValue;
        }
    },

    MIN_COUNT {
        @Override
        public boolean check(Set<String> targetCourseCodes, int conditionValue, List<AuditCompletedCourse> auditCompletedCourses) {
            long count = auditCompletedCourses.stream()
                                              .filter(course -> targetCourseCodes.contains(course.code()))
                                              .count();

            return count >= conditionValue;
        }
    },

    MUST_TAKE_ALL {
        @Override
        public boolean check(Set<String> targetCourseCodes, int conditionValue, List<AuditCompletedCourse> auditCompletedCourses) {
            final Set<String> completedCodes = auditCompletedCourses.stream()
                                                                    .map(AuditCompletedCourse::code)
                                                                    .collect(Collectors.toSet());
            return completedCodes.containsAll(targetCourseCodes);
        }
    };

    public abstract boolean check(Set<String> targetCourseCodes, int conditionValue, List<AuditCompletedCourse> auditCompletedCourses);
}
