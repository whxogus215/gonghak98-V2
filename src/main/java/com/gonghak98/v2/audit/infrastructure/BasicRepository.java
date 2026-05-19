package com.gonghak98.v2.audit.infrastructure;

import com.gonghak98.v2.audit.domain.constant.AbeekType;
import com.gonghak98.v2.audit.domain.abeek.Basic;
import com.gonghak98.v2.audit.domain.rule.RequirementRule;
import com.gonghak98.v2.core.infrastructure.entity.DepartmentEntity;
import com.gonghak98.v2.audit.infrastructure.dto.RequirementDetail.AreaRequirement;
import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BasicRepository {

    public Basic create(DepartmentEntity department, AreaRequirement requirement) {
        final AbeekType basicType = AbeekType.getBasicType(department.getName());
        final List<RequirementRule> rules = requirement.getComponents().stream()
                                                       .map(component -> new RequirementRule(
                                                           component.getName(),
                                                           new HashSet<>(component.getTargetCourses()),
                                                           component.getConditionValue(),
                                                           component.getRuleType()
                                                       ))
                                                       .toList();
        double minCredit = requirement.getMinCredit();

        return new Basic(basicType, rules, minCredit);
    }
}
