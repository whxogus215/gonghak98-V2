package com.gonghak98.v2.report.controller.dto;

import com.gonghak98.v2.audit.domain.constant.NonPassMessage;

public enum FailReason {

    NOT_SATISFIED_PREREQUISITE,
    UNKNOWN;

    public static FailReason from(NonPassMessage message) {
        if (message == null) {
            return UNKNOWN;
        }
        try {
            return FailReason.valueOf(message.name());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}
