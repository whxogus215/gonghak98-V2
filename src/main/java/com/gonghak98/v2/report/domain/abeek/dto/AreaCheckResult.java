package com.gonghak98.v2.report.domain.abeek.dto;

import com.gonghak98.v2.report.domain.abeek.AbeekType;
import java.util.List;
import java.util.Map;

public record AreaCheckResult(Map<AbeekType, Boolean> passResults,
                              List<NonPassResult> nonPassResults,
                              List<NonPassResult> notCheckedResults) {

}
