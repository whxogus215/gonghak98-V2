package com.gonghak98.v2.report.domain.abeek.basic;

import com.gonghak98.v2.report.domain.abeek.dto.CheckResult;
import com.gonghak98.v2.report.domain.student.CompletedCourse;

import java.util.List;

public interface Basic {

    void checkAllCourses(List<CompletedCourse> completedCourses, CheckResult checkResult);
}
