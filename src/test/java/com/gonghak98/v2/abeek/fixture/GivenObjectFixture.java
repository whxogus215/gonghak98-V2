package com.gonghak98.v2.abeek.fixture;

import com.gonghak98.v2.report.domain.abeek.AreaType;
import com.gonghak98.v2.report.domain.abeek.dto.CheckResult;
import java.util.EnumMap;
import java.util.HashMap;

public class GivenObjectFixture {

    public static CheckResult createCheckResult() {
        return new CheckResult(new EnumMap<>(AreaType.class), new HashMap<>());
    }

}
