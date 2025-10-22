package com.gonghak98.v2.report.domain.abeek;

import com.gonghak98.v2.report.domain.abeek.basic.Basic;
import com.gonghak98.v2.report.domain.abeek.design.Design;
import com.gonghak98.v2.report.domain.abeek.dto.CheckResult;
import com.gonghak98.v2.report.domain.abeek.gyoyang.Gyoyang;
import com.gonghak98.v2.report.domain.abeek.major.Major;
import com.gonghak98.v2.report.domain.abeek.prerequisite.Prerequisite;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Abeek {

    private final Gyoyang gyoyang;
    private final Basic basic;
    private final Major major;
    private final Design design;
    private final Prerequisite prerequisite;

    public CheckResult checkAllCourses(List<CompletedCourse> completedCourses) {
        CheckResult checkResult = new CheckResult(null, null);

        gyoyang.checkAllCourses(completedCourses, checkResult);
        basic.checkAllCourses(completedCourses, checkResult);
        major.checkAllCourses(completedCourses, checkResult);
        design.checkAllCourses(completedCourses, checkResult);
        prerequisite.checkAllCourses(completedCourses, checkResult);

        return checkResult;
    }
}
