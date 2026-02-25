package com.gonghak98.v2.report.domain.abeek.dto;

import com.gonghak98.v2.report.domain.abeek.AreaType;
import com.gonghak98.v2.report.domain.abeek.NonPassMessage;
import com.gonghak98.v2.report.domain.counting.AreaCreditSummary;
import java.util.Map;

public record CheckResult(Map<AreaType, Boolean> passResults,
                          Map<Integer, NonPassMessage> nonPassResults,
                          Map<AreaType, AreaCreditSummary> creditSummaries) {

}
