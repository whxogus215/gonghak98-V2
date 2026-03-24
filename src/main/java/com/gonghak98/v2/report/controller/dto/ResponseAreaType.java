package com.gonghak98.v2.report.controller.dto;

import com.gonghak98.v2.report.domain.abeek.AreaType;

public enum ResponseAreaType {

    MSC, BSM, MAJOR, GYOYANG, DESIGN,
    UNKNOWN;

    public static ResponseAreaType from(AreaType areaType) {
        if (areaType == null) {
            return UNKNOWN;
        }
        try {
            return ResponseAreaType.valueOf(areaType.name());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}
