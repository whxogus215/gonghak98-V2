package com.gonghak98.v2.abeek.fixture;

import com.gonghak98.v2.report.domain.abeek.prerequisite.DesignPrerequisite;
import java.util.Set;

public class PrerequisiteFixture {

    public static DesignPrerequisite createDesignPrerequisite() {
        Long basicCourseId = 7620L;
        Set<Long> elementCourseIds = Set.of(7721L, 9650L, 6935L, 9662L, 7585L, 9663L);
        Set<Long> comprehensiveCourseIds = Set.of(9947L, 9948L);

        return new DesignPrerequisite(basicCourseId, elementCourseIds, comprehensiveCourseIds);
    }
}
