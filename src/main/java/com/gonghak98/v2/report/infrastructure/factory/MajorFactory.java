package com.gonghak98.v2.report.infrastructure.factory;

import com.gonghak98.v2.report.domain.abeek.major.Major;
import com.gonghak98.v2.report.domain.abeek.rule.RequirementRule;
import com.gonghak98.v2.report.domain.abeek.rule.Rule;
import com.gonghak98.v2.report.infrastructure.factory.dto.RequirementDetail.AreaRequirement;
import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MajorFactory {

    public Major create(AreaRequirement requirement) {
        final List<Rule> rules = requirement.getComponents().stream()
                                            .map(component -> (Rule) new RequirementRule(
                                                component.getName(),
                                                new HashSet<>(component.getTargetCourses()),
                                                component.getConditionValue(),
                                                component.getRuleType()
                                            ))
                                            .toList();
        double minCredit = requirement.getMinCredit();

        return new Major(rules, minCredit);
    }
}
