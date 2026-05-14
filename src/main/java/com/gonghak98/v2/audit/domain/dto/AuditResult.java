package com.gonghak98.v2.audit.domain.dto;

import com.gonghak98.v2.audit.domain.constant.AbeekType;
import java.util.List;
import java.util.Map;

public record AuditResult(Map<AbeekType, Boolean> passResults,
                          List<NonPassResult> nonPassResults) {

    public static AuditResult merge(AuditResult first, AuditResult second) {
        first.passResults().putAll(second.passResults());
        first.nonPassResults().addAll(second.nonPassResults());
        return first;
    }
}
