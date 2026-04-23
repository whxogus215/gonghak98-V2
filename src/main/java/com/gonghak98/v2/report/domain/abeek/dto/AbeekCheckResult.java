package com.gonghak98.v2.report.domain.abeek.dto;

import com.gonghak98.v2.report.domain.abeek.AbeekType;
import com.gonghak98.v2.report.domain.counting.AreaCreditSummary;
import java.util.List;
import java.util.Map;

public record AbeekCheckResult(Map<AbeekType, Boolean> passResults,
                               List<NonPassResult> nonPassResults,
                               Map<AbeekType, AreaCreditSummary> creditSummaries) {

}
