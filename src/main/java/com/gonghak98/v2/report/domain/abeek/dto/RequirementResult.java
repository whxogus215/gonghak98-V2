package com.gonghak98.v2.report.domain.abeek.dto;

import com.gonghak98.v2.report.domain.abeek.AreaType;
import com.gonghak98.v2.report.domain.abeek.NonPassMessage;
import java.util.Map;

public record RequirementResult(Map<AreaType, Boolean> passResults,
                                Map<Long, NonPassMessage> nonPassResults) {

}
