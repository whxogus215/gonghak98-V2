package com.gonghak98.v2.audit.domain.prerequisite;

import com.gonghak98.v2.audit.domain.constant.AbeekType;
import com.gonghak98.v2.audit.domain.constant.NonPassMessage;
import com.gonghak98.v2.audit.domain.dto.AuditCompletedCourse;
import com.gonghak98.v2.audit.domain.dto.NonPassResult;
import com.gonghak98.v2.audit.domain.dto.PrerequisiteAuditResult;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NonDesignPrerequisiteAudit implements PrerequisiteAuditable {

    private final Map<String, List<String>> prerequisiteCourseCodes; // Key : 후수과목 코드, Value : 선수과목 코드 리스트

    @Override
    public PrerequisiteAuditResult audit(List<AuditCompletedCourse> completedCourses) {
        PrerequisiteAuditResult prerequisiteAuditResult = new PrerequisiteAuditResult(new EnumMap<>(AbeekType.class), new ArrayList<>());
        List<NonPassResult> nonPassResults = prerequisiteAuditResult.nonPassResults();
        Map<String, AuditCompletedCourse> completedCourseTable = completedCourses.stream()
                                                                                 .collect(Collectors.toMap(
                                                                                     AuditCompletedCourse::code,
                                                                                     c -> c,
                                                                                     (c1, c2) -> c1.compareTo(c2) <= 0 ? c2 : c1));
        for (AuditCompletedCourse completedAfterCourse : completedCourses) {
            String afterCourseCode = completedAfterCourse.code();
            boolean isPassed = true;
            List<String> mustBeforeCourseCodes = prerequisiteCourseCodes.get(afterCourseCode);
            if (mustBeforeCourseCodes == null || mustBeforeCourseCodes.isEmpty()) {
                continue;
            }
            for (String mustBeforeCourseCode : mustBeforeCourseCodes) {
                if (isPrerequisiteFailed(mustBeforeCourseCode, completedAfterCourse, completedCourseTable)) {
                    nonPassResults.add(NonPassResult.of(completedAfterCourse, NonPassMessage.NOT_SATISFIED_PREREQUISITE));
                    isPassed = false;
                    break;
                }
            }
            prerequisiteAuditResult.passResults().put(completedAfterCourse.abeekType(), isPassed);
        }
        return prerequisiteAuditResult;
    }

    private boolean isPrerequisiteFailed(String mustBeforeCourseCode, AuditCompletedCourse afterCourse, Map<String, AuditCompletedCourse> completedCourseTable) {
        AuditCompletedCourse completedBeforeCourse = completedCourseTable.get(mustBeforeCourseCode);

        return completedBeforeCourse == null || !PrerequisiteChecker.isSatisfiedPrerequisite(completedBeforeCourse, afterCourse);
    }
}
