package com.gonghak98.v2.report.domain.abeek.gyoyang;

import com.gonghak98.v2.report.domain.abeek.AreaType;
import com.gonghak98.v2.report.domain.abeek.dto.RequirementResult;
import com.gonghak98.v2.report.domain.course.Course;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ProGyoyang implements Gyoyang {

    private final List<Course> essentialCourses;
    private final List<Course> electiveCourses;

    private final Set<Integer> courseIds;

    private final double minPoint;

    public ProGyoyang(List<Course> essentialCourses,
                      List<Course> electiveCourses,
                      double minPoint) {
        this.essentialCourses = essentialCourses;
        this.electiveCourses = electiveCourses;
        this.minPoint = minPoint;
        this.courseIds = new HashSet<>();
        essentialCourses.forEach(course -> this.courseIds.add(course.getId()));
        electiveCourses.forEach(course -> this.courseIds.add(course.getId()));
    }

    @Override
    public void checkAllCourses(List<CompletedCourse> completedCourses, RequirementResult requirementResult) {
        Set<Integer> completedCourseIds = completedCourses.stream()
                                                          .map(CompletedCourse::getId)
                                                          .collect(Collectors.toSet());
        int completedEssentialCount = 0;
        int completedElectiveCount = 0;
        double totalPoint = 0.0;

        for (Course course : essentialCourses) {
            if (completedCourseIds.contains(course.getId())) {
                completedEssentialCount++;
                totalPoint += course.getPoint();
            }
        }
        for (Course course : electiveCourses) {
            if (completedCourseIds.contains(course.getId())) {
                completedElectiveCount++;
                totalPoint += course.getPoint();
            }
        }

        boolean isEssentialSatisfied = (completedEssentialCount == essentialCourses.size());
        boolean isElectiveSatisfied = completedElectiveCount >= 2;
        boolean isMinPointSatisfied = totalPoint >= minPoint;
        boolean isAllSatisfied = isMinPointSatisfied && isEssentialSatisfied && isElectiveSatisfied;

        requirementResult.passResults().put(AreaType.GYOYANG, isAllSatisfied);
    }

    @Override
    public List<CompletedCourse> getRelatedCourses(List<CompletedCourse> completedCourses) {
        return completedCourses.stream()
                               .filter(course -> courseIds.contains(course.getId()))
                               .toList();
    }

    @Override
    public Double getRequiredPoints() {
        return minPoint;
    }
}
