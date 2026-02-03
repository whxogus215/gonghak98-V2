package com.gonghak98.v2.report.infrastructure.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gonghak98.v2.report.domain.abeek.prerequisite.DesignPrerequisite;
import com.gonghak98.v2.report.domain.abeek.prerequisite.NonDesignPrerequisite;
import com.gonghak98.v2.report.domain.abeek.prerequisite.Prerequisite;
import com.gonghak98.v2.report.infrastructure.entity.DepartmentEntity;
import com.gonghak98.v2.report.infrastructure.factory.dto.PrerequisiteConfig;
import com.gonghak98.v2.report.infrastructure.factory.dto.PrerequisiteConfig.DesignPrerequisiteConfig;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PrerequisiteFactory {

    private final ObjectMapper objectMapper;

    public Prerequisite create(final DepartmentEntity department) {
        PrerequisiteConfig prerequisiteConfig = loadConfig(department.getName());

        NonDesignPrerequisite nonDesignPrerequisite = new NonDesignPrerequisite(prerequisiteConfig.getNonDesignPrerequisiteCourseIds());
        DesignPrerequisiteConfig designPrerequisiteConfig = prerequisiteConfig.getDesignPrerequisiteCourseIds();
        DesignPrerequisite designPrerequisite = new DesignPrerequisite(designPrerequisiteConfig.getBasicCourseId(),
                                                                       designPrerequisiteConfig.getElementCourseIds(),
                                                                       designPrerequisiteConfig.getComprehensiveCourseIds());

        return new Prerequisite(nonDesignPrerequisite, designPrerequisite);
    }

    private PrerequisiteConfig loadConfig(final String name) {
        String path = "json/prerequisite-config/" + name + ".json";
        final ClassPathResource resource = new ClassPathResource(path);
        if (!resource.exists()) {
            throw new IllegalArgumentException(path + "을 찾을 수 없습니다.");
        }
        try {
            final PrerequisiteConfig prerequisiteConfig = objectMapper.readValue(resource.getInputStream(), PrerequisiteConfig.class);
            if (prerequisiteConfig.getNonDesignPrerequisiteCourseIds() == null) {
                throw new IllegalArgumentException("비설계 과목의 선후수 정보가 존재하지 않습니다.");
            }
            if (prerequisiteConfig.getDesignPrerequisiteCourseIds() == null) {
                throw new IllegalArgumentException("설계 과목의 선후수 정보가 존재하지 않습니다.");
            }
            return prerequisiteConfig;
        } catch (IOException e) {
            throw new IllegalArgumentException("파일을 읽어오는 중 에러가 발생했습니다.");
        }
    }
}
