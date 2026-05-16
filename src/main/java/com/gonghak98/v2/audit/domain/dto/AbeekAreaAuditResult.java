package com.gonghak98.v2.audit.domain.dto;

import com.gonghak98.v2.audit.domain.constant.AbeekType;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public record AbeekAreaAuditResult(Map<AbeekType, Boolean> passResults,
                                   List<NonPassResult> nonPassResults) {

    public static AbeekAreaAuditResult merge(AbeekAreaAuditResult first, AbeekAreaAuditResult second) {
        Map<AbeekType, Boolean> mergedPassResults = new EnumMap<>(AbeekType.class);
        mergedPassResults.putAll(first.passResults());
        mergedPassResults.putAll(second.passResults());

        List<NonPassResult> mergedNonPassResults = new ArrayList<>(first.nonPassResults());
        mergedNonPassResults.addAll(second.nonPassResults());

        return new AbeekAreaAuditResult(mergedPassResults, mergedNonPassResults);
    }
}
