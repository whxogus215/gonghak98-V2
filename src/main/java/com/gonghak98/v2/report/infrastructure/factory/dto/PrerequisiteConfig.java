package com.gonghak98.v2.report.infrastructure.factory.dto;

import java.util.Map;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PrerequisiteConfig {

    private String departmentName;
    private Map<Integer, Integer> nonDesignPrerequisiteCourseIds;
    private DesignPrerequisiteConfig designPrerequisiteCourseIds;

    @Getter
    @NoArgsConstructor
    public static class DesignPrerequisiteConfig {
        private Integer basicCourseId;
        private Set<Integer> elementCourseIds;
        private Set<Integer> comprehensiveCourseIds;
    }
}
