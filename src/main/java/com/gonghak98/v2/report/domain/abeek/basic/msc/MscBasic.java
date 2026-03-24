package com.gonghak98.v2.report.domain.abeek.basic.msc;

import com.gonghak98.v2.report.domain.abeek.AreaType;
import com.gonghak98.v2.report.domain.abeek.basic.Basic;
import com.gonghak98.v2.report.domain.abeek.dto.RequirementResult;
import com.gonghak98.v2.report.domain.course.Course;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MscBasic implements Basic {

    private final Set<Course> essentialCourses;
    private final Set<Long> essentialCourseIds;

    public MscBasic(Set<Course> essentialCourses) {
        this.essentialCourses = essentialCourses;
        this.essentialCourseIds = essentialCourses.stream()
                                                  .map(Course::getId)
                                                  .collect(Collectors.toSet());
    }

    @Override
    public void checkAllCourses(List<CompletedCourse> completedCourses, RequirementResult requirementResult) {
        // MSC(Math, Science, Computing) 검사 로직 구현
        Set<Long> completedCourseIds = completedCourses.stream()
                                                          .map(CompletedCourse::getId)
                                                          .collect(Collectors.toSet());

        boolean isSatisfied = completedCourseIds.containsAll(essentialCourseIds);

        requirementResult.passResults().put(AreaType.MSC, isSatisfied);
    }

    @Override
    public List<CompletedCourse> getRelatedCourses(List<CompletedCourse> completedCourses) {
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
