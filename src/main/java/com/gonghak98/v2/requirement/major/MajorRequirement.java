package com.gonghak98.v2.requirement.major;

import com.gonghak98.v2.completedcourse.CompletedCourse;
import com.gonghak98.v2.requirement.constant.RequirementType;
import com.gonghak98.v2.requirement.vo.CheckResult;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class MajorRequirement {

    private final LabMajor labMajor;

    private final GeneralMajor generalMajor;

    public MajorRequirement(LabMajor labMajor, GeneralMajor generalMajor1) {
        this.labMajor = labMajor;
        this.generalMajor = generalMajor1;
    }

    public CheckResult check(List<CompletedCourse> completedCourse) {
        Map<RequirementType, Boolean> passResult = new EnumMap<>(RequirementType.class);
        passResult.put(RequirementType.LAB, labMajor.check(completedCourse));
        passResult.put(RequirementType.GENERAL, generalMajor.check(completedCourse));
        return new CheckResult(passResult);
    }
}
