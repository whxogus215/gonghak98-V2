package com.gonghak98.v2.abeek.basic.msc;

import com.gonghak98.v2.abeek.AreaType;
import com.gonghak98.v2.abeek.basic.Basic;
import com.gonghak98.v2.abeek.dto.CheckResult;
import com.gonghak98.v2.course.Course;
import com.gonghak98.v2.student.CompletedCourse;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MscBasic implements Basic {

    // TODO List가 아니라 Set을 사용했을 때, 더 이점이 있지 않은지 고민해보기 (동일성과 동등성 정의 필요! - 학수번호 기준)
    private final List<Course> essentialCourses;

    @Override
    public void checkAllCourses(List<CompletedCourse> completedCourses, CheckResult checkResult) {
        // MSC(Math, Science, Computing) 검사 로직 구현
        Set<Integer> completedCourseIds = completedCourses.stream()
                                                          .map(CompletedCourse::getId)
                                                          .collect(Collectors.toSet());
        List<Integer> essentialCourseIds = essentialCourses.stream()
                                                           .map(Course::getId)
                                                           .toList();

        boolean isSatisfied = completedCourseIds.containsAll(essentialCourseIds);

        checkResult.passResults().put(AreaType.MSC, isSatisfied);
    }
}
