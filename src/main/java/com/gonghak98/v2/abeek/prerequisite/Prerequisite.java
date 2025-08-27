package com.gonghak98.v2.abeek.prerequisite;

import com.gonghak98.v2.abeek.dto.CheckResult;
import com.gonghak98.v2.student.CompletedCourse;

import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Prerequisite {

    private final NonDesignPrerequisite nonDesignPrerequisite;
    private final DesignPrerequisite designPrerequisite;

    public void checkAllCourses(List<CompletedCourse> completedCourses, CheckResult checkResult) {
        nonDesignPrerequisite.check(completedCourses, checkResult);
        designPrerequisite.check(completedCourses, checkResult);
    }
}
