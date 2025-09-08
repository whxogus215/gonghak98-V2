package com.gonghak98.v2.report.domain.abeek.gyoyang;

import com.gonghak98.v2.report.domain.abeek.AreaType;
import com.gonghak98.v2.report.domain.abeek.dto.CheckResult;
import com.gonghak98.v2.report.domain.course.Course;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
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
    public void checkAllCourses(List<CompletedCourse> completedCourses, CheckResult checkResult) {
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

        checkResult.passResults().put(AreaType.GYOYANG, isAllSatisfied);
    }
}
