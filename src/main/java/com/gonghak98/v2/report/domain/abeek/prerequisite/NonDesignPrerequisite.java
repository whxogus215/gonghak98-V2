package com.gonghak98.v2.report.domain.abeek.prerequisite;

import com.gonghak98.v2.report.domain.abeek.NonPassMessage;
import com.gonghak98.v2.report.domain.abeek.dto.RequirementResult;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NonDesignPrerequisite {

    private final Map<Long, Long> prerequisiteCourseIds;

    public void check(List<CompletedCourse> completedCourses, RequirementResult requirementResult) {
        Map<Long, NonPassMessage> nonPassResults = requirementResult.nonPassResults();
        Map<Long, CompletedCourse> completedCourseTable = completedCourses.stream().collect(Collectors.toMap(CompletedCourse::getId, c -> c));

        for (CompletedCourse completedCourse : completedCourses) {
            Long afterCourseId = completedCourse.getId();
            Long beforeCourseId = prerequisiteCourseIds.get(afterCourseId);
            if (beforeCourseId == null) {
                continue;
            }
            if (completedCourseTable.containsKey(beforeCourseId)) {
                CompletedCourse beforeCourse = completedCourseTable.get(beforeCourseId);
                CompletedCourse afterCourse = completedCourseTable.get(afterCourseId);
                if (!PrerequisiteChecker.isSatisfiedPrerequisite(beforeCourse,afterCourse)) {
                    nonPassResults.put(afterCourseId, NonPassMessage.NOT_SATISFIED_PREREQUISITE);
                }
            } else {
                nonPassResults.put(afterCourseId, NonPassMessage.NOT_SATISFIED_PREREQUISITE);
            }
        }
    }
}
