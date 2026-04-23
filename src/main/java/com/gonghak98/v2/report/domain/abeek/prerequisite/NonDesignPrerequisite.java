package com.gonghak98.v2.report.domain.abeek.prerequisite;

import com.gonghak98.v2.report.domain.abeek.NonPassMessage;
import com.gonghak98.v2.report.domain.abeek.dto.AreaCheckResult;
import com.gonghak98.v2.report.domain.abeek.dto.NonPassResult;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NonDesignPrerequisite {

    private final Map<String, String> prerequisiteCourseCodes; // Key : 후수과목 코드, Value : 선수과목 코드

    public void check(List<CompletedCourse> completedCourses, AreaCheckResult areaCheckResult) {
        List<NonPassResult> nonPassResults = areaCheckResult.nonPassResults();
        Map<String, CompletedCourse> completedCourseTable = completedCourses.stream()
                                                                            .collect(Collectors.toMap(
                                                                                CompletedCourse::getCode, c -> c));

        for (CompletedCourse completedCourse : completedCourses) {
            String afterCourseCode = completedCourse.getCode();
            String beforeCourseCode = prerequisiteCourseCodes.get(afterCourseCode);
            if (beforeCourseCode == null) {
                continue;
            }
            if (completedCourseTable.containsKey(beforeCourseCode)) {
                CompletedCourse beforeCourse = completedCourseTable.get(beforeCourseCode);
                CompletedCourse afterCourse = completedCourseTable.get(afterCourseCode);
                if (!PrerequisiteChecker.isSatisfiedPrerequisite(beforeCourse, afterCourse)) {
                    nonPassResults.add(new NonPassResult(afterCourseCode,
                                                         afterCourse.getName(),
                                                         afterCourse.getYear(),
                                                         afterCourse.getSemester(),
                                                         afterCourse.getCredit(),
                                                         NonPassMessage.NOT_SATISFIED_PREREQUISITE));
                }
            } else {
                nonPassResults.add(new NonPassResult(completedCourse.getCode(),
                                                     completedCourse.getName(),
                                                     completedCourse.getYear(),
                                                     completedCourse.getSemester(),
                                                     completedCourse.getCredit(),
                                                     NonPassMessage.NOT_SATISFIED_PREREQUISITE));
            }
        }
    }
}
