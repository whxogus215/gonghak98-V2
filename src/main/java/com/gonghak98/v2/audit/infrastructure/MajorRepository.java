package com.gonghak98.v2.audit.infrastructure;

import com.gonghak98.v2.audit.domain.Major;
import com.gonghak98.v2.audit.domain.rule.RequirementRule;
import com.gonghak98.v2.audit.domain.rule.Rule;
import com.gonghak98.v2.audit.infrastructure.dto.RequirementDetail.AreaRequirement;
import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MajorRepository {

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
