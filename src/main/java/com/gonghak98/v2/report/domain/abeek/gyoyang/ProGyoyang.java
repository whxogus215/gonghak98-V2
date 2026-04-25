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

    private static final int THREE_ESSENTIAL_TOTAL_CREDIT = 8; // 문제해결을위한글쓰기와발표(3) + 서양철학:쟁점과토론(3) + 대학영어(2)
    private static final int ELECTIVE_MIN_CREDIT = 6; // 3학점인 인증선택 과목을 최소 2개 수강

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
        double completedEssentialCredit = 0;
        double completedElectiveCredit = 0;

        for (Course course : essentialCourses) {
            if (completedCourseIds.contains(course.getCode())) {
                completedEssentialCredit += course.getCredit();
            }
        }
        for (Course course : electiveCourses) {
            if (completedCourseIds.contains(course.getCode())) {
                completedElectiveCredit += course.getCredit();
            }
        }

        boolean isSatisfiedOnlyEssential = checkOnlyEssential(completedEssentialCredit);
        boolean isSatisfiedEssentialAndElective = checkEssentialAndElective(completedEssentialCredit, completedElectiveCredit);

        areaCheckResult.passResults().put(AbeekType.GYOYANG, (isSatisfiedOnlyEssential || isSatisfiedEssentialAndElective));
    }

    // 2021년 이전의 전문교양 영역 조건 검사 : 6개의 인증필수 과목을 이수했는지 검사
    private boolean checkOnlyEssential(double completedEssentialCredit) {
        // 2021년 이전의 경우, 전문교양 영역을 수강한다면 인증선택 과목은 존재하지 않아야 한다.
        return (completedEssentialCredit >= minCredit);
    }

    // 2022년 이후의 전문교양 영역 조건 검사 : 3개의 인증필수 과목을 이수했는지 검사 + 인증선택 2과목 이상 이수했는지 검사
    private boolean checkEssentialAndElective(double completedEssentialCredit, double completedElectiveCredit) {
        return (completedEssentialCredit >= THREE_ESSENTIAL_TOTAL_CREDIT) && (completedElectiveCredit >= ELECTIVE_MIN_CREDIT);
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
