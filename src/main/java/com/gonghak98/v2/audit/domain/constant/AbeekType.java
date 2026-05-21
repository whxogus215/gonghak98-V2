package com.gonghak98.v2.audit.domain.constant;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AbeekType {
    
    NONE(Collections.emptySet()), // 공학인증에 포함되지 않는 과목에 대한 ABEEK TYPE
    
    MSC(Set.of("전자정보통신공학과", "항공우주공학과")),
    BSM(Set.of("소프트웨어학과")),
    MAJOR(Collections.emptySet()),
    GYOYANG(Collections.emptySet()),
    DESIGN(Collections.emptySet());

    private final Set<String> departmentNames;

    public static AbeekType getBasicType(String departmentName) {
        return Arrays.stream(values())
                     .filter(areaType -> (areaType == MSC || areaType == BSM))
                     .filter(areaType -> areaType.departmentNames.contains(departmentName))
                     .findFirst()
                     .orElse(null);
    }
}
