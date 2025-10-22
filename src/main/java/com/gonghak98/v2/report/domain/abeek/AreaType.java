package com.gonghak98.v2.report.domain.abeek;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AreaType {

    MSC(Set.of("전자정보통신공학과")),
    BSM(Collections.emptySet()),
    MAJOR(Collections.emptySet()),
    GYOYANG(Collections.emptySet()),
    DESIGN(Collections.emptySet());

    private final Set<String> departmentNames;

    public static AreaType getBasicType(String departmentName) {
        return Arrays.stream(values())
                     .filter(areaType -> (areaType == MSC || areaType == BSM))
                     .filter(areaType -> areaType.departmentNames.contains(departmentName))
                     .findFirst()
                     .orElse(null);
    }
}
