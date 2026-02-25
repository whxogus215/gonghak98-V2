package com.gonghak98.v2.report.domain.abeek.basic;

import com.gonghak98.v2.report.domain.abeek.AreaType;
import com.gonghak98.v2.report.domain.abeek.dto.RequirementResult;
import com.gonghak98.v2.report.domain.student.CompletedCourse;

import java.util.List;

public interface Basic {

    void checkAllCourses(List<CompletedCourse> completedCourses, RequirementResult requirementResult);

    List<CompletedCourse> getRelatedCourses(List<CompletedCourse> completedCourses);

    AreaType getBasicAreaType();

    Double getRequiredPoints();
}
