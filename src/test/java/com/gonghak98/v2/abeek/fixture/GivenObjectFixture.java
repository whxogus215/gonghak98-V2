package com.gonghak98.v2.abeek.fixture;

import com.gonghak98.v2.report.domain.abeek.AbeekType;
import com.gonghak98.v2.report.domain.abeek.dto.RequirementResult;
import java.util.EnumMap;
import java.util.HashMap;

public class GivenObjectFixture {

    public static RequirementResult createCheckResult() {
        return new RequirementResult(new EnumMap<>(AbeekType.class), new HashMap<>());
    }

}
