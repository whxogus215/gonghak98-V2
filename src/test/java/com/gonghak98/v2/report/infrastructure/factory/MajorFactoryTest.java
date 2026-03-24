package com.gonghak98.v2.report.infrastructure.factory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gonghak98.v2.report.domain.abeek.major.GeneralMajor;
import com.gonghak98.v2.report.domain.abeek.major.LabMajor;
import com.gonghak98.v2.report.domain.abeek.major.Major;
import com.gonghak98.v2.report.infrastructure.entity.DepartmentEntity;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MajorFactoryTest {

    @Test
    @DisplayName("학과별 전공 세부정보가 담긴 JSON으로 역직렬화 할 수 있다.")
    void createTest() {
        //given
        MajorFactory majorFactory = new MajorFactory(new ObjectMapper());
        final DepartmentEntity departmentEntity = new DepartmentEntity("전자정보통신공학과");

        Major expected = new Major(
            new LabMajor(Set.of(5611L, 9658L, 8076L, 9666L), 1),
            new GeneralMajor(
                Set.of(4114L, 5246L, 7620L, 4111L, 7453L, 4474L, 9649L, 7806L,
                       4699L, 4600L, 4829L, 3284L, 8086L, 6294L, 6132L),
                24
            ),
            45
        );

        //when
        final Major major = majorFactory.create(departmentEntity);

        //then
        assertThatCode(() -> majorFactory.create(departmentEntity)).doesNotThrowAnyException();
        assertThat(major).usingRecursiveComparison().isEqualTo(expected);
    }
}
