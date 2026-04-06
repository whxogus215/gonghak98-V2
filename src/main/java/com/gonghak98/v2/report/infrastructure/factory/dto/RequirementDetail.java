package com.gonghak98.v2.report.infrastructure.factory.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequirementDetail {

    private BasicRequirement totalRequirement;
    private BasicRequirement designRequirement;

    private AreaRequirement basicRequirement;
    private AreaRequirement majorRequirement;
    private PrerequisiteRequirement prerequisiteRequirement;

    @Getter
    @NoArgsConstructor
    public static class BasicRequirement {

        private double minCredit;
    }

    @Getter
    @NoArgsConstructor
    public static class AreaRequirement {

        private double minCredit;
        private List<ComponentRule> components;
    }

    @Getter
    @NoArgsConstructor
    public static class ComponentRule {

        private String name;
        private String description;
        private ConditionType conditionType;
        private Integer conditionValue;
        private List<String> targetCourses;
    }

    @Getter
    @NoArgsConstructor
    public static class PrerequisiteRequirement {

        private List<PrerequisiteComponent> targetCourses;
    }

    @Getter
    @NoArgsConstructor
    public static class PrerequisiteComponent {

        private String afterCode;
        private String beforeCode;
    }
}
