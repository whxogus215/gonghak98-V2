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

     private final Map<String, String> prerequisiteCourseCodes;

    public void check(List<CompletedCourse> completedCourses, RequirementResult requirementResult) {
        Map<String, NonPassMessage> nonPassResults = requirementResult.nonPassResults();
        Map<String, CompletedCourse> completedCourseTable = completedCourses.stream().collect(Collectors.toMap(CompletedCourse::getCode, c -> c));

        for (CompletedCourse completedCourse : completedCourses) {
            String afterCourseCode = completedCourse.getCode();
            String beforeCourseCode = prerequisiteCourseCodes.get(afterCourseCode);
            if (beforeCourseCode == null) {
                continue;
            }
            if (completedCourseTable.containsKey(beforeCourseCode)) {
                CompletedCourse beforeCourse = completedCourseTable.get(beforeCourseCode);
                CompletedCourse afterCourse = completedCourseTable.get(afterCourseCode);
                if (!PrerequisiteChecker.isSatisfiedPrerequisite(beforeCourse,afterCourse)) {
                    nonPassResults.put(afterCourseCode, NonPassMessage.NOT_SATISFIED_PREREQUISITE);
                }
            } else {
                nonPassResults.put(afterCourseCode, NonPassMessage.NOT_SATISFIED_PREREQUISITE);
            }
        }
    }
}
