package com.gonghak98.v2.report.domain.abeek.dto;

import com.gonghak98.v2.report.domain.abeek.AbeekType;
import com.gonghak98.v2.report.domain.abeek.NonPassMessage;
import java.util.Map;

public record RequirementResult(Map<AbeekType, Boolean> passResults,
                                Map<String, NonPassMessage> nonPassResults) {

}
