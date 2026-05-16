package com.gonghak98.v2.audit.domain.dto;

import com.gonghak98.v2.audit.domain.constant.AbeekType;
import java.util.List;
import java.util.Map;

public record PrerequisiteAuditResult(Map<AbeekType, Boolean> passResults,
                                      List<NonPassResult> nonPassResults) {

    public static PrerequisiteAuditResult merge(PrerequisiteAuditResult first, PrerequisiteAuditResult second) {
        Map<AbeekType, Boolean> mergedPassResults = new java.util.EnumMap<>(AbeekType.class);
        mergedPassResults.putAll(first.passResults());
        mergedPassResults.putAll(second.passResults());

        List<NonPassResult> mergedNonPassResults = new java.util.ArrayList<>(first.nonPassResults());
        mergedNonPassResults.addAll(second.nonPassResults());

        return new PrerequisiteAuditResult(mergedPassResults, mergedNonPassResults);
    }
}
