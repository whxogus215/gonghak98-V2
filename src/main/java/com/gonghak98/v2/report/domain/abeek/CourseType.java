package com.gonghak98.v2.report.domain.abeek;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CourseType {

    ESSENTIAL("필수"),
    ELECTIVE("선택");

    private final String content;
}
