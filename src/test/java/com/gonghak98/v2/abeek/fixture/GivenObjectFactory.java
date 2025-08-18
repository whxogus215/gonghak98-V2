package com.gonghak98.v2.abeek.fixture;

import com.gonghak98.v2.abeek.AreaType;
import com.gonghak98.v2.abeek.dto.CheckResult;
import java.util.EnumMap;
import java.util.HashMap;

public class GivenObjectFactory {

    public static CheckResult createCheckResult() {
        return new CheckResult(new EnumMap<>(AreaType.class), new HashMap<>());
    }

}
