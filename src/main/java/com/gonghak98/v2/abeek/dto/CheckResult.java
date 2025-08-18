package com.gonghak98.v2.abeek.dto;

import com.gonghak98.v2.abeek.AreaType;
import com.gonghak98.v2.abeek.NonPassMessage;
import java.util.Map;

public record CheckResult(Map<AreaType, Boolean> passResults,
                          Map<Integer, NonPassMessage> nonPassResults) {
}
