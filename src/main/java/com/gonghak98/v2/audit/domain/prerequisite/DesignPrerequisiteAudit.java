package com.gonghak98.v2.audit.domain.prerequisite;

import com.gonghak98.v2.audit.domain.constant.AbeekType;
import com.gonghak98.v2.audit.domain.constant.NonPassMessage;
import com.gonghak98.v2.audit.domain.dto.NonPassResult;
import com.gonghak98.v2.audit.domain.dto.PrerequisiteAuditResult;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DesignPrerequisiteAudit implements PrerequisiteAuditable {

    private final String basicCourseCode;
    private final Set<String> elementCourseCodes;
    private final Set<String> comprehensiveCourseCodes;

    @Override
    public PrerequisiteAuditResult audit(List<CompletedCourse> courses) {
        PrerequisiteAuditResult prerequisiteAuditResult = new PrerequisiteAuditResult(new EnumMap<>(AbeekType.class),
                                                                                      new ArrayList<>());

        Map<AbeekType, Boolean> passResults = prerequisiteAuditResult.passResults();
        List<NonPassResult> nonPassResults = prerequisiteAuditResult.nonPassResults();

        Optional<CompletedCourse> completedBasicCourse = courses.stream()
                                                                .filter(completedCourse -> basicCourseCode.equals(completedCourse.getCode()))
                                                                .findFirst();

        List<CompletedCourse> completedElementCourses = courses.stream()
                                                               .filter(completedCourse -> elementCourseCodes.contains(completedCourse.getCode()))
                                                               .toList();

        List<CompletedCourse> completedComprehensiveCourses = courses.stream()
                                                                     .filter(
                                                                         completedCourse -> comprehensiveCourseCodes.contains(completedCourse.getCode()))
                                                                     .collect(Collectors.toList());

        boolean isElementPassed = checkElementPrerequisite(completedBasicCourse, completedElementCourses, nonPassResults);
        boolean isComprehensivePassed = checkComprehensivePrerequisite(completedBasicCourse, completedElementCourses, completedComprehensiveCourses,
                                                                       nonPassResults);
        boolean isAllSatisfied = isComprehensivePassed && isElementPassed;

        passResults.put(AbeekType.DESIGN, isAllSatisfied);
        return prerequisiteAuditResult;
    }

    private boolean checkElementPrerequisite(Optional<CompletedCourse> completedBasicCourse,
                                             List<CompletedCourse> completedElementCourses,
                                             List<NonPassResult> nonPassResults) {
        // 기초설계를 먼저 들었는지 확인
        if (completedBasicCourse.isEmpty()) {
            for (CompletedCourse completedElementCourse : completedElementCourses) {
                nonPassResults.add(new NonPassResult(completedElementCourse.getCode(),
                                                     completedElementCourse.getName(),
                                                     completedElementCourse.getYear(),
                                                     completedElementCourse.getSemester(),
                                                     completedElementCourse.getCredit(),
                                                     NonPassMessage.NOT_SATISFIED_PREREQUISITE
                                   )
                );
            }
            return false;
        }
        CompletedCourse realCompletedBasicCourse = completedBasicCourse.get();
        for (CompletedCourse completedElementCourse : completedElementCourses) {
            if (!PrerequisiteChecker.isSatisfiedPrerequisite(realCompletedBasicCourse, completedElementCourse)) {
                nonPassResults.add(new NonPassResult(completedElementCourse.getCode(),
                                                     completedElementCourse.getName(),
                                                     completedElementCourse.getYear(),
                                                     completedElementCourse.getSemester(),
                                                     completedElementCourse.getCredit(),
                                                     NonPassMessage.NOT_SATISFIED_PREREQUISITE
                                   )
                );
                return false;
            }
        }
        return true;
    }

    private boolean checkComprehensivePrerequisite(Optional<CompletedCourse> completedBasicCourse,
                                                   List<CompletedCourse> completedElementCourses,
                                                   List<CompletedCourse> completedComprehensiveCourses,
                                                   List<NonPassResult> nonPassResults) {
        // 기초설계를 먼저 들었는지 확인
        if (completedBasicCourse.isEmpty()) {
            for (CompletedCourse completedComprehensiveCourse : completedComprehensiveCourses) {
                nonPassResults.add(new NonPassResult(completedComprehensiveCourse.getCode(),
                                                     completedComprehensiveCourse.getName(),
                                                     completedComprehensiveCourse.getYear(),
                                                     completedComprehensiveCourse.getSemester(),
                                                     completedComprehensiveCourse.getCredit(),
                                                     NonPassMessage.NOT_SATISFIED_PREREQUISITE
                                   )
                );
            }
            return false;
        }
        CompletedCourse realCompletedBasicCourse = completedBasicCourse.get();

        for (CompletedCourse completedComprehensiveCourse : completedComprehensiveCourses) {
            if (!PrerequisiteChecker.isSatisfiedPrerequisite(realCompletedBasicCourse, completedComprehensiveCourse)) {
                nonPassResults.add(new NonPassResult(completedComprehensiveCourse.getCode(),
                                                     completedComprehensiveCourse.getName(),
                                                     completedComprehensiveCourse.getYear(),
                                                     completedComprehensiveCourse.getSemester(),
                                                     completedComprehensiveCourse.getCredit(),
                                                     NonPassMessage.NOT_SATISFIED_PREREQUISITE
                                   )
                );
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
                nonPassResults.add(new NonPassResult(completedComprehensiveCourse.getCode(),
                                                     completedComprehensiveCourse.getName(),
                                                     completedComprehensiveCourse.getYear(),
                                                     completedComprehensiveCourse.getSemester(),
                                                     completedComprehensiveCourse.getCredit(),
                                                     NonPassMessage.NOT_SATISFIED_PREREQUISITE
                                   )
                );
                return false;
            }
        }
        return true;
    }
}
