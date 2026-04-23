package com.gonghak98.v2.report.domain.abeek.major;

import com.gonghak98.v2.report.domain.abeek.AbeekType;
import com.gonghak98.v2.report.domain.abeek.dto.AreaCheckResult;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Major {

    private final LabMajor labMajor;
    private final GeneralMajor generalMajor;
    private final double minPoint;

    public void checkAllCourses(List<CompletedCourse> completedCourses, AreaCheckResult areaCheckResult) {
        boolean generalResult = generalMajor.check(completedCourses);
        boolean labResult = labMajor.check(completedCourses);

        areaCheckResult.passResults().put(AbeekType.MAJOR, generalResult && labResult);
    }
    
    public List<CompletedCourse> getRelatedCourses(List<CompletedCourse> completedCourses) {
        List<CompletedCourse> relatedCourses = new ArrayList<>();
        relatedCourses.addAll(generalMajor.getRelatedCourses(completedCourses));
        relatedCourses.addAll(labMajor.getRelatedCourses(completedCourses));
        return relatedCourses;
    }

    public Double getRequiredPoints() {
        return minPoint;
    }
}
