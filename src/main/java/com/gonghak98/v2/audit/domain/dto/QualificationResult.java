package com.gonghak98.v2.audit.domain.dto;

import com.gonghak98.v2.audit.domain.constant.AbeekType;
import com.gonghak98.v2.report.domain.counting.AreaCreditSummary;
import java.util.List;
import java.util.Map;

public record QualificationResult(Map<AbeekType, Boolean> passResults,
                                  List<NonPassResult> nonPassResults,
                                  Map<AbeekType, AreaCreditSummary> creditSummaries) {

}
