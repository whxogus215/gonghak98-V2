package com.gonghak98.v2.audit.domain.prerequisite;

import com.gonghak98.v2.audit.domain.constant.AbeekType;
import com.gonghak98.v2.audit.domain.constant.NonPassMessage;
import com.gonghak98.v2.audit.domain.dto.NonPassResult;
import com.gonghak98.v2.audit.domain.dto.PrerequisiteAuditResult;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NonDesignPrerequisiteAudit implements PrerequisiteAuditable {

    private final Map<String, String> prerequisiteCourseCodes; // Key : 후수과목 코드, Value : 선수과목 코드

    @Override
    public PrerequisiteAuditResult audit(List<CompletedCourse> courses) {
        PrerequisiteAuditResult prerequisiteAuditResult = new PrerequisiteAuditResult(new EnumMap<>(AbeekType.class), new ArrayList<>());
        List<NonPassResult> nonPassResults = prerequisiteAuditResult.nonPassResults();
        Map<String, CompletedCourse> completedCourseTable = courses.stream()
                                                                   .collect(Collectors.toMap(
                                                                       CompletedCourse::getCode,
                                                                       c -> c,
                                                                       (c1, c2) -> c1.compareTo(c2) <= 0 ? c2 : c1));

        for (CompletedCourse afterCourse : courses) {
            String afterCourseCode = afterCourse.getCode();
            String beforeCourseCode = prerequisiteCourseCodes.get(afterCourseCode);
            if (beforeCourseCode == null) {
                continue;
            }
            if (completedCourseTable.containsKey(beforeCourseCode)) {
                CompletedCourse beforeCourse = completedCourseTable.get(beforeCourseCode);
                if (!PrerequisiteChecker.isSatisfiedPrerequisite(beforeCourse, afterCourse)) {
                    nonPassResults.add(NonPassResult.of(afterCourse, NonPassMessage.NOT_SATISFIED_PREREQUISITE));
                    prerequisiteAuditResult.passResults().put(afterCourse.getAbeekType(), Boolean.FALSE);
                }
            } else {
                nonPassResults.add(NonPassResult.of(afterCourse, NonPassMessage.NOT_SATISFIED_PREREQUISITE));
                prerequisiteAuditResult.passResults().put(afterCourse.getAbeekType(), Boolean.FALSE);
            }
        }
        return prerequisiteAuditResult;
    }
}
