package com.gonghak98.v2.abeek.basic;

import com.gonghak98.v2.abeek.dto.CheckResult;
import com.gonghak98.v2.student.CompletedCourse;

import java.util.List;

public interface Basic {

    void checkAllCourses(List<CompletedCourse> completedCourses, CheckResult checkResult);
}
