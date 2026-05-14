package com.gonghak98.v2.audit.domain.dto;

import com.gonghak98.v2.audit.domain.constant.AbeekType;
import java.util.List;
import java.util.Map;

public record PrerequisiteAuditResult(Map<AbeekType, Boolean> passResults,
                                      List<NonPassResult> nonPassResults) {

    public static PrerequisiteAuditResult merge(PrerequisiteAuditResult nonDesignResult, PrerequisiteAuditResult designResult) {
        nonDesignResult.passResults().putAll(designResult.passResults());
        nonDesignResult.nonPassResults().addAll(designResult.nonPassResults());
        return nonDesignResult;
    }
}
