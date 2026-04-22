package com.gonghak98.v2.report.infrastructure.factory;

import com.gonghak98.v2.report.domain.abeek.major.GeneralMajor;
import com.gonghak98.v2.report.domain.abeek.major.LabMajor;
import com.gonghak98.v2.report.domain.abeek.major.Major;
import com.gonghak98.v2.report.infrastructure.factory.dto.ComponentName;
import com.gonghak98.v2.report.infrastructure.factory.dto.RequirementDetail.AreaRequirement;
import com.gonghak98.v2.report.infrastructure.factory.dto.RequirementDetail.ComponentRule;
import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MajorFactory {

    public Major create(AreaRequirement requirement) {
        LabMajor labMajor = null;
        GeneralMajor generalMajor = null;

        final List<ComponentRule> components = requirement.getComponents();
        for (ComponentRule rule : components) {
            if (rule.getName().equals(ComponentName.LAB_MAJOR.name())) {
                labMajor = new LabMajor(new HashSet<>(rule.getTargetCourses()),
                                        rule.getConditionValue());
            }
            if (rule.getName().equals(ComponentName.GENERAL_MAJOR.name())) {
                generalMajor = new GeneralMajor(new HashSet<>(rule.getTargetCourses()),
                                                rule.getConditionValue());
            }
        }
        double minCredit = requirement.getMinCredit();

        return new Major(labMajor, generalMajor, minCredit);
    }
}
