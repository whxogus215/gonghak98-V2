package com.gonghak98.v2.abeek.major;

import com.gonghak98.v2.abeek.AreaType;
import com.gonghak98.v2.abeek.dto.CheckResult;
import com.gonghak98.v2.student.CompletedCourse;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Major {

    private final DesignMajor designMajor;
    private final LabMajor labMajor;
    private final GeneralMajor generalMajor;

    public void checkAllCourses(List<CompletedCourse> completedCourses, CheckResult checkResult) {
        boolean generalResult = generalMajor.check(completedCourses);
        boolean labResult = labMajor.check(completedCourses);
        boolean designResult = designMajor.check(completedCourses);

        checkResult.passResults().put(AreaType.MAJOR, generalResult && labResult && designResult);
    }
}
