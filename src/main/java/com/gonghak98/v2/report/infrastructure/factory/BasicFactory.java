package com.gonghak98.v2.report.infrastructure.factory;

import com.gonghak98.v2.report.domain.abeek.AbeekType;
import com.gonghak98.v2.report.domain.abeek.basic.Basic;
import com.gonghak98.v2.report.domain.abeek.exception.AbeekException;
import com.gonghak98.v2.report.domain.abeek.exception.ExceptionMessage;
import com.gonghak98.v2.report.domain.abeek.rule.RequirementRule;
import com.gonghak98.v2.report.infrastructure.entity.DepartmentEntity;
import com.gonghak98.v2.report.infrastructure.entity.GonghakCourseEntity;
import com.gonghak98.v2.report.infrastructure.factory.dto.RequirementDetail.AreaRequirement;
import com.gonghak98.v2.report.infrastructure.jpa.JpaGonghakCourseRepository;
import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BasicFactory {

    private final JpaGonghakCourseRepository gonghakCourseRepository;

    public Basic create(DepartmentEntity department, AreaRequirement requirement) {
        final AbeekType basicType = AbeekType.getBasicType(department.getName());
        final List<GonghakCourseEntity> gonghakCourses = gonghakCourseRepository.findByDepartmentAndAbeekType(department, basicType);

        if (gonghakCourses.isEmpty()) {
            throw new AbeekException(ExceptionMessage.EMPTY_GONGHAK_COURSE.getMessage());
        }

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
