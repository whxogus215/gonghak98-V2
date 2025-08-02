package com.gonghak98.v2.certification.controller;

public record MajorAreaDetails(SubRuleResult design,
                               SubRuleResult lab,
                               SubRuleResult general,
                               SubRuleResult prerequisite) implements AreaDetails {

}
