package com.gonghak98.v2.report.domain.abeek;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CourseType {

    ESSENTIAL("필수"),
    ELECTIVE("선택"),

    DESIGN_BASIC("기초설계"),
    DESIGN_ELEMENT("요소설계"),
    DESIGN_COMPREHENSIVE("종합설계");

    private final String content;
}
