package com.gonghak98.v2.report.domain.abeek.gyoyang;

import com.gonghak98.v2.report.domain.abeek.dto.AreaCheckResult;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.List;

public interface Gyoyang {

    void checkAllCourses(List<CompletedCourse> completedCourses, AreaCheckResult areaCheckResult);
    
    Double getRequiredCredits();
}
