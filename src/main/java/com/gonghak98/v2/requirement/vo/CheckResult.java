package com.gonghak98.v2.requirement.vo;

import com.gonghak98.v2.requirement.constant.RequirementType;
import java.util.Map;

public record CheckResult(Map<RequirementType, Boolean> passResults) {

}
