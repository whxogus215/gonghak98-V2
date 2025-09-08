package com.gonghak98.v2.report.domain.course;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SemesterConst {

    ONE("1학기", 1),
    TWO("2학기", 2),
    SUMMER("여름학기", 3),
    WINTER("겨울학기", 4),
    EMPTY("올바르지 않은 값", 0);

    private final String content;
    private final int value;

    public static SemesterConst getSemester(String content) {
        return Arrays.stream(values()).filter(s -> s.content.equals(content))
                     .findAny().orElse(EMPTY);
    }
}
