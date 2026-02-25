package com.gonghak98.v2.report.domain.abeek.basic.msc;

import com.gonghak98.v2.report.domain.abeek.AreaType;
import com.gonghak98.v2.report.domain.abeek.basic.Basic;
import com.gonghak98.v2.report.domain.abeek.dto.RequirementResult;
import com.gonghak98.v2.report.domain.course.Course;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MscBasic implements Basic {

    private final List<Course> essentialCourses;

    @Override
    public void checkAllCourses(List<CompletedCourse> completedCourses, RequirementResult requirementResult) {
        // MSC(Math, Science, Computing) 검사 로직 구현
        Set<Integer> completedCourseIds = completedCourses.stream()
                                                          .map(CompletedCourse::getId)
                                                          .collect(Collectors.toSet());
        List<Integer> essentialCourseIds = essentialCourses.stream()
                                                           .map(Course::getId)
                                                           .toList();

        boolean isSatisfied = completedCourseIds.containsAll(essentialCourseIds);

        requirementResult.passResults().put(AreaType.MSC, isSatisfied);
    }

    @Override
    public List<CompletedCourse> getRelatedCourses(List<CompletedCourse> completedCourses) {
        Set<Integer> essentialCourseIds = essentialCourses.stream()
                                                          .map(Course::getId)
                                                          .collect(Collectors.toSet());

        return completedCourses.stream()
                               .filter(course -> essentialCourseIds.contains(course.getId()))
                               .toList();
    }

    @Override
    public AreaType getBasicAreaType() {
        return AreaType.MSC;
    }

    @Override
    public Double getRequiredPoints() {
        return essentialCourses.stream()
                               .mapToDouble(Course::getPoint)
                               .sum();
    }
}
