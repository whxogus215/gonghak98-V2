package com.gonghak98.v2.report.domain.abeek;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AreaType {

    MSC(Set.of("전자정보통신공학과"), "MSC"),
    BSM(Collections.emptySet(), "BSM"),
    MAJOR(Collections.emptySet(), "전공"),
    GYOYANG(Collections.emptySet(), "전문교양"),
    DESIGN(Collections.emptySet(), "설계");

    private final Set<String> departmentNames;
    private final String detail;

    public static AreaType getBasicType(String departmentName) {
        return Arrays.stream(values())
                     .filter(areaType -> (areaType == MSC || areaType == BSM))
                     .filter(areaType -> areaType.departmentNames.contains(departmentName))
                     .findFirst()
                     .orElse(null);
    }

    public static AreaType getByName(String name) {
        return Arrays.stream(values())
                     .filter(areaType -> areaType.detail.equals(name))
                     .findFirst()
                     .orElse(null);
    }
}
