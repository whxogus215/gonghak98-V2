package com.gonghak98.v2.report.domain.abeek.basic.msc;

import com.gonghak98.v2.report.domain.abeek.AbeekType;
import com.gonghak98.v2.report.domain.abeek.basic.Basic;
import com.gonghak98.v2.report.domain.abeek.dto.RequirementResult;
import com.gonghak98.v2.report.domain.course.Course;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MscBasic implements Basic {

    private final Set<Course> essentialCourses;
    private final Set<String> essentialCourseIds;

    public MscBasic(Set<Course> essentialCourses) {
        this.essentialCourses = essentialCourses;
        this.essentialCourseIds = essentialCourses.stream()
                                                  .map(Course::getCode)
                                                  .collect(Collectors.toSet());
    }

    @Override
    public void checkAllCourses(List<CompletedCourse> completedCourses, RequirementResult requirementResult) {
        // MSC(Math, Science, Computing) 검사 로직 구현
        Set<String> completedCourseIds = completedCourses.stream()
                                                          .map(CompletedCourse::getCode)
                                                          .collect(Collectors.toSet());

        boolean isSatisfied = completedCourseIds.containsAll(essentialCourseIds);

        requirementResult.passResults().put(AbeekType.MSC, isSatisfied);
    }

    @Override
    public List<CompletedCourse> getRelatedCourses(List<CompletedCourse> completedCourses) {
        return completedCourses.stream()
                               .filter(course -> essentialCourseIds.contains(course.getCode()))
                               .toList();
    }

    @Override
    public AbeekType getBasicAreaType() {
        return AbeekType.MSC;
    }

    @Override
    public Double getRequiredPoints() {
        return essentialCourses.stream()
                               .mapToDouble(Course::getPoint)
                               .sum();
    }
}
