package com.gonghak98.v2.report.infrastructure.factory.dto;

import java.util.Map;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PrerequisiteConfig {

    private String departmentName;
    private Map<Long, Long> nonDesignPrerequisiteCourseIds;
    private DesignPrerequisiteConfig designPrerequisiteCourseIds;

    @Getter
    @NoArgsConstructor
    public static class DesignPrerequisiteConfig {
        private Long basicCourseId;
        private Set<Long> elementCourseIds;
        private Set<Long> comprehensiveCourseIds;
    }
}
