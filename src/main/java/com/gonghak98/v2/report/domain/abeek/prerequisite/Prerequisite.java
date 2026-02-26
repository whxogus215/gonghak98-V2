package com.gonghak98.v2.report.domain.abeek.prerequisite;

import com.gonghak98.v2.report.domain.abeek.dto.RequirementResult;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Prerequisite {

    private final NonDesignPrerequisite nonDesignPrerequisite;
    private final DesignPrerequisite designPrerequisite;

    public void checkAllCourses(List<CompletedCourse> completedCourses, RequirementResult requirementResult) {
        nonDesignPrerequisite.check(completedCourses, requirementResult);
        designPrerequisite.check(completedCourses, requirementResult);
    }
}
