package com.gonghak98.v2.report.domain.abeek.prerequisite;

import com.gonghak98.v2.report.domain.abeek.AbeekType;
import com.gonghak98.v2.report.domain.abeek.NonPassMessage;
import com.gonghak98.v2.report.domain.abeek.dto.RequirementResult;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DesignPrerequisite {

    private final String basicCourseCode;
    private final Set<String> elementCourseCodes;
    private final Set<String> comprehensiveCourseCodes;

    public void check(List<CompletedCourse> completedCourses, RequirementResult requirementResult) {
        Map<AbeekType, Boolean> passResults = requirementResult.passResults();
        Map<String, NonPassMessage> nonPassResults = requirementResult.nonPassResults();

        Optional<CompletedCourse> completedBasicCourse = completedCourses.stream()
                                                                         .filter(completedCourse -> basicCourseCode.equals(completedCourse.getCode()))
                                                                         .findFirst();

        List<CompletedCourse> completedElementCourses = completedCourses.stream()
                                                                        .filter(completedCourse -> elementCourseCodes.contains(completedCourse.getCode()))
                                                                        .toList();

        List<CompletedCourse> completedComprehensiveCourses = completedCourses.stream()
                                                                              .filter(
                                                                                  completedCourse -> comprehensiveCourseCodes.contains(completedCourse.getCode()))
                                                                              .collect(Collectors.toList());

        boolean isElementPassed = checkElementPrerequisite(completedBasicCourse, completedElementCourses, nonPassResults);
        boolean isComprehensivePassed = checkComprehensivePrerequisite(completedBasicCourse, completedElementCourses, completedComprehensiveCourses,
                                                                       nonPassResults);

        boolean isAllSatisfied = isComprehensivePassed && isElementPassed;
        passResults.put(AbeekType.DESIGN, passResults.getOrDefault(AbeekType.DESIGN, false) && isAllSatisfied);
    }

    private boolean checkElementPrerequisite(Optional<CompletedCourse> completedBasicCourse, List<CompletedCourse> completedElementCourses,
                                             Map<String, NonPassMessage> nonPassResults) {
        // 기초설계를 먼저 들었는지 확인
        if (completedBasicCourse.isEmpty()) {
            for (CompletedCourse completedElementCourse : completedElementCourses) {
                nonPassResults.put(completedElementCourse.getCode(), NonPassMessage.NOT_SATISFIED_PREREQUISITE);
            }
            return false;
        }
        CompletedCourse realCompletedBasicCourse = completedBasicCourse.get();
        for (CompletedCourse completedElementCourse : completedElementCourses) {
            if (!PrerequisiteChecker.isSatisfiedPrerequisite(realCompletedBasicCourse, completedElementCourse)) {
                nonPassResults.put(completedElementCourse.getCode(), NonPassMessage.NOT_SATISFIED_PREREQUISITE);
                return false;
            }
        }
        return true;
    }

    private boolean checkComprehensivePrerequisite(Optional<CompletedCourse> completedBasicCourse, List<CompletedCourse> completedElementCourses,
                                                   List<CompletedCourse> completedComprehensiveCourses, Map<String, NonPassMessage> nonPassResults) {
        // 기초설계를 먼저 들었는지 확인
        if (completedBasicCourse.isEmpty()) {
            for (CompletedCourse completedComprehensiveCourse : completedComprehensiveCourses) {
                nonPassResults.put(completedComprehensiveCourse.getCode(), NonPassMessage.NOT_SATISFIED_PREREQUISITE);
            }
            return false;
        }
        CompletedCourse realCompletedBasicCourse = completedBasicCourse.get();

        for (CompletedCourse completedComprehensiveCourse : completedComprehensiveCourses) {
            if (!PrerequisiteChecker.isSatisfiedPrerequisite(realCompletedBasicCourse, completedComprehensiveCourse)) {
                nonPassResults.put(completedComprehensiveCourse.getCode(), NonPassMessage.NOT_SATISFIED_PREREQUISITE);
                return false;
            }
        }
        // 요소 2개를 먼저 듣고 종합 설계를 들었는지 확인
        if (completedElementCourses.size() < 2) {
            return false;
        }
        // 요소 설계 중 종합 설계 보다 나중에 들은 과목이 없는지 확인
        Collections.sort(completedComprehensiveCourses);
        CompletedCourse completedComprehensiveCourse = completedComprehensiveCourses.get(0);
        for (CompletedCourse completedElementCourse : completedElementCourses) {
            if (!PrerequisiteChecker.isSatisfiedDesignPrerequisite(completedElementCourse, completedComprehensiveCourse)) {
                nonPassResults.put(completedComprehensiveCourse.getCode(), NonPassMessage.NOT_SATISFIED_PREREQUISITE);
                return false;
            }
        }
        return true;
    }
}
