package com.gonghak98.v2.certification.controller;

import java.util.List;

public record SubRuleResult(boolean isPassed,
                            String description,
                            List<String> courseNames) {

}
