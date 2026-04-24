package com.gonghak98.v2.report.domain.abeek.gyoyang;

import com.gonghak98.v2.report.domain.abeek.AbeekType;
import com.gonghak98.v2.report.domain.abeek.dto.AreaCheckResult;
import com.gonghak98.v2.report.domain.course.Course;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ProGyoyang implements Gyoyang {

    private final List<Course> essentialCourses;
    private final List<Course> electiveCourses;

    private final Set<String> courseCodes;

    private final double minCredit;

    public ProGyoyang(List<Course> essentialCourses,
                      List<Course> electiveCourses,
                      double minCredit) {
        this.essentialCourses = essentialCourses;
        this.electiveCourses = electiveCourses;
        this.minCredit = minCredit;
        this.courseCodes = new HashSet<>();
        essentialCourses.forEach(course -> this.courseCodes.add(course.getCode()));
        electiveCourses.forEach(course -> this.courseCodes.add(course.getCode()));
    }

    @Override
    public void checkAllCourses(List<CompletedCourse> completedCourses, AreaCheckResult areaCheckResult) {
        Set<String> completedCourseIds = completedCourses.stream()
                                                          .map(CompletedCourse::getCode)
                                                          .collect(Collectors.toSet());
        int completedEssentialCount = 0;
        int completedElectiveCount = 0;
        double totalCredit = 0.0;

        for (Course course : essentialCourses) {
            if (completedCourseIds.contains(course.getCode())) {
                completedEssentialCount++;
                totalCredit += course.getCredit();
            }
        }
        for (Course course : electiveCourses) {
            if (completedCourseIds.contains(course.getCode())) {
                completedElectiveCount++;
                totalCredit += course.getCredit();
            }
        }

        boolean isEssentialSatisfied = (completedEssentialCount == essentialCourses.size());
        boolean isElectiveSatisfied = completedElectiveCount >= 2;
        boolean isMinCreditSatisfied = totalCredit >= minCredit;
        boolean isAllSatisfied = isMinCreditSatisfied && isEssentialSatisfied && isElectiveSatisfied;

        areaCheckResult.passResults().put(AbeekType.GYOYANG, isAllSatisfied);
    }

    @Override
    public List<CompletedCourse> getRelatedCourses(List<CompletedCourse> completedCourses) {
        return completedCourses.stream()
                               .filter(course -> courseCodes.contains(course.getCode()))
                               .toList();
    }

    @Override
    public Double getRequiredCredits() {
        return minCredit;
    }
}
