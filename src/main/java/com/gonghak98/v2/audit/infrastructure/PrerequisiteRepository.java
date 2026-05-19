package com.gonghak98.v2.audit.infrastructure;

import com.gonghak98.v2.audit.domain.constant.AbeekType;
import com.gonghak98.v2.audit.domain.prerequisite.DesignPrerequisiteAudit;
import com.gonghak98.v2.audit.domain.prerequisite.NonDesignPrerequisiteAudit;
import com.gonghak98.v2.audit.domain.prerequisite.PrerequisiteAudit;
import com.gonghak98.v2.core.infrastructure.entity.DepartmentEntity;
import com.gonghak98.v2.report.infrastructure.entity.GonghakCourseEntity;
import com.gonghak98.v2.audit.infrastructure.dto.RequirementDetail.PrerequisiteComponent;
import com.gonghak98.v2.audit.infrastructure.dto.RequirementDetail.PrerequisiteRequirement;
import com.gonghak98.v2.report.infrastructure.jpa.JpaGonghakCourseRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PrerequisiteRepository {

    private final JpaGonghakCourseRepository gonghakCourseRepository;

    public PrerequisiteAudit create(final DepartmentEntity department, PrerequisiteRequirement requirement) {
        NonDesignPrerequisiteAudit nonDesignPrerequisiteAudit = new NonDesignPrerequisiteAudit(requirement.getTargetCourses()
                                                                                                          .stream()
                                                                                                          .collect(Collectors.toMap(
                                                                                               PrerequisiteComponent::getAfterCode,
                                                                                               PrerequisiteComponent::getBeforeCode
                                                                                           ))
        );

        final List<GonghakCourseEntity> findDesignCourses = gonghakCourseRepository.findByDepartmentAndAbeekType(department, AbeekType.DESIGN);
        final DesignPrerequisiteAudit designPrerequisiteAudit = getDesignPrerequisite(findDesignCourses);

        return new PrerequisiteAudit(List.of(nonDesignPrerequisiteAudit, designPrerequisiteAudit));
    }

    private static DesignPrerequisiteAudit getDesignPrerequisite(List<GonghakCourseEntity> findDesignCourses) {
        String basicCourseCode = "";
        Set<String> elementCourseCodes = new HashSet<>();
        Set<String> comprehensiveCourseCodes = new HashSet<>();

        for (GonghakCourseEntity gonghakCourse : findDesignCourses) {
            switch (gonghakCourse.getCourseType()) {
                case DESIGN_BASIC -> basicCourseCode = gonghakCourse.getCourse().getCode();
                case DESIGN_ELEMENT -> elementCourseCodes.add(gonghakCourse.getCourse().getCode());
                case DESIGN_COMPREHENSIVE -> comprehensiveCourseCodes.add(gonghakCourse.getCourse().getCode());
            }
        }
        return new DesignPrerequisiteAudit(basicCourseCode,
                                           elementCourseCodes,
                                           comprehensiveCourseCodes);
    }
}
