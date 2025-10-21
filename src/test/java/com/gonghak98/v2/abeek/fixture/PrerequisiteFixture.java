package com.gonghak98.v2.abeek.fixture;

import com.gonghak98.v2.report.domain.abeek.prerequisite.DesignPrerequisite;
import java.util.Set;

public class PrerequisiteFixture {

    public static DesignPrerequisite createDesignPrerequisite() {
        Integer basicCourseId = 7620;
        Set<Integer> elementCourseIds = Set.of(7721, 9650, 6935, 9662, 7585, 9663);
        Set<Integer> comprehensiveCourseIds = Set.of(9947, 9948);

        return new DesignPrerequisite(basicCourseId, elementCourseIds, comprehensiveCourseIds);
    }
}
