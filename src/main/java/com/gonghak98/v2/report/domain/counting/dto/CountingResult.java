package com.gonghak98.v2.report.domain.counting.dto;

import com.gonghak98.v2.report.domain.abeek.AbeekType;
import com.gonghak98.v2.report.domain.counting.AreaCreditSummary;
import java.util.Map;

public record CountingResult(
    Map<AbeekType, AreaCreditSummary> creditSummaries
) {
}
