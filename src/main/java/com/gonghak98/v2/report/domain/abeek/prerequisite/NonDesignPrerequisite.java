package com.gonghak98.v2.report.domain.abeek.prerequisite;

import com.gonghak98.v2.report.domain.abeek.NonPassMessage;
import com.gonghak98.v2.report.domain.abeek.dto.CheckResult;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NonDesignPrerequisite {

    private final Map<Integer, Integer> prerequisiteCourseIds;

    public void check(List<CompletedCourse> completedCourses, CheckResult checkResult) {
        Map<Integer, NonPassMessage> nonPassResults = checkResult.nonPassResults();
        Map<Integer, CompletedCourse> completedCourseTable = completedCourses.stream().collect(Collectors.toMap(CompletedCourse::getId, c -> c));

        for (CompletedCourse completedCourse : completedCourses) {
            Integer afterCourseId = completedCourse.getId();
            Integer beforeCourseId = prerequisiteCourseIds.get(afterCourseId);
            if (beforeCourseId == null) {
                continue;
            }
            if (completedCourseTable.containsKey(beforeCourseId)) {
                CompletedCourse beforeCourse = completedCourseTable.get(beforeCourseId);
                CompletedCourse afterCourse = completedCourseTable.get(afterCourseId);
                if (PrerequisiteChecker.isSatisfiedPrerequisite(beforeCourse,afterCourse)) {
                    nonPassResults.put(afterCourseId, NonPassMessage.NOT_SATISFIED_PREREQUISITE);
                }
            } else {
                nonPassResults.put(afterCourseId, NonPassMessage.NOT_SATISFIED_PREREQUISITE);
            }
        }
    }
}
