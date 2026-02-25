package com.gonghak98.v2.report.domain.abeek.gyoyang;

import com.gonghak98.v2.report.domain.abeek.AreaType;
import com.gonghak98.v2.report.domain.abeek.dto.RequirementResult;
import com.gonghak98.v2.report.domain.course.Course;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProGyoyang implements Gyoyang {

    private final List<Course> essentialCourses;
    private final List<Course> electiveCourses;

    private final double minPoint;

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
        Set<Integer> allCourseIds = new HashSet<>();
        essentialCourses.forEach(course -> allCourseIds.add(course.getId()));
        electiveCourses.forEach(course -> allCourseIds.add(course.getId()));
        
        return completedCourses.stream()
                .filter(course -> allCourseIds.contains(course.getId()))
                .toList();
    }

    @Override
    public Double getRequiredPoints() {
        return minPoint;
    }
}
