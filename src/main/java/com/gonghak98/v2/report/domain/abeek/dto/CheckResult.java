package com.gonghak98.v2.report.domain.abeek.dto;

import com.gonghak98.v2.report.domain.abeek.AbeekType;
import com.gonghak98.v2.report.domain.abeek.NonPassMessage;
import com.gonghak98.v2.report.domain.counting.AreaCreditSummary;
import java.util.Map;

public record CheckResult(Map<AbeekType, Boolean> passResults,
                          Map<String, NonPassMessage> nonPassResults,
                          Map<AbeekType, AreaCreditSummary> creditSummaries) {

}
