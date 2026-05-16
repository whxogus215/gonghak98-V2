package com.gonghak98.v2.audit.fixture;

import com.gonghak98.v2.audit.domain.prerequisite.DesignPrerequisiteAudit;
import java.util.Set;

public class PrerequisiteFixture {

    public static DesignPrerequisiteAudit createDesignPrerequisite() {
        String basicCourseId = "007620";
        Set<String> elementCourseIds = Set.of("007721", "009650", "006935", "009662", "007585", "009663");
        Set<String> comprehensiveCourseIds = Set.of("009947", "009948");

        return new DesignPrerequisiteAudit(basicCourseId, elementCourseIds, comprehensiveCourseIds);
    }
}
