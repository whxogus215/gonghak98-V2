package com.gonghak98.v2.report.infrastructure.factory;

import com.gonghak98.v2.report.domain.abeek.AbeekType;
import com.gonghak98.v2.report.domain.abeek.prerequisite.DesignPrerequisite;
import com.gonghak98.v2.report.domain.abeek.prerequisite.NonDesignPrerequisite;
import com.gonghak98.v2.report.domain.abeek.prerequisite.Prerequisite;
import com.gonghak98.v2.report.infrastructure.entity.DepartmentEntity;
import com.gonghak98.v2.report.infrastructure.entity.GonghakCourseEntity;
import com.gonghak98.v2.report.infrastructure.factory.dto.RequirementDetail.PrerequisiteComponent;
import com.gonghak98.v2.report.infrastructure.factory.dto.RequirementDetail.PrerequisiteRequirement;
import com.gonghak98.v2.report.infrastructure.jpa.JpaGonghakCourseRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PrerequisiteFactory {

    private final JpaGonghakCourseRepository gonghakCourseRepository;

    public Prerequisite create(final DepartmentEntity department, PrerequisiteRequirement requirement) {
        NonDesignPrerequisite nonDesignPrerequisite = new NonDesignPrerequisite(requirement.getTargetCourses()
                                                                                           .stream()
                                                                                           .collect(Collectors.toMap(
                                                                                               PrerequisiteComponent::getAfterCode,
                                                                                               PrerequisiteComponent::getBeforeCode
                                                                                           ))
        );

        final List<GonghakCourseEntity> findDesignCourses = gonghakCourseRepository.findByDepartmentAndAbeekType(department, AbeekType.DESIGN);
        final DesignPrerequisite designPrerequisite = getDesignPrerequisite(findDesignCourses);

        return new Prerequisite(nonDesignPrerequisite, designPrerequisite);
    }

    private static DesignPrerequisite getDesignPrerequisite(List<GonghakCourseEntity> findDesignCourses) {
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
        return new DesignPrerequisite(basicCourseCode,
                                      elementCourseCodes,
                                      comprehensiveCourseCodes);
    }
}
