package com.gonghak98.v2.report.domain.abeek.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ExceptionMessage {

    EMPTY_GONGHAK_COURSE("공학인증 과목이 존재하지 않습니다.");

    @Getter
    private final String message;
}
