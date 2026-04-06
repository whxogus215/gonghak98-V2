package com.gonghak98.v2.report.controller.dto;

import com.gonghak98.v2.report.domain.abeek.AbeekType;

public enum ResponseAreaType {

    MSC, BSM, MAJOR, GYOYANG, DESIGN,
    UNKNOWN;

    public static ResponseAreaType from(AbeekType abeekType) {
        if (abeekType == null) {
            return UNKNOWN;
        }
        try {
            return ResponseAreaType.valueOf(abeekType.name());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}
