package com.gonghak98.v2.audit.domain.constant;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AbeekTypeTest {

    @ParameterizedTest
    @CsvSource({
        "전자정보통신공학과, MSC",
        "항공우주공학과, MSC",
        "소프트웨어학과, BSM"
    })
    void 학과별_기초영역_타입을_반환한다(String departmentName, AbeekType expected) {
        assertThat(AbeekType.getBasicType(departmentName)).isEqualTo(expected);
    }
}
