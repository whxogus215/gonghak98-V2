package com.gonghak98.v2.report.infrastructure.factory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gonghak98.v2.report.domain.abeek.major.GeneralMajor;
import com.gonghak98.v2.report.domain.abeek.major.LabMajor;
import com.gonghak98.v2.report.domain.abeek.major.Major;
import com.gonghak98.v2.report.infrastructure.entity.DepartmentEntity;
import java.util.List;
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
            new LabMajor(List.of(5611, 9658, 8076, 9666), 1),
            new GeneralMajor(
                List.of(4114, 5246, 7620, 4111, 7453, 4474, 9649, 7806,
                        4699, 4600, 4829, 3284, 8086, 6294, 6132),
                24
            )
        );

        //when
        final Major major = majorFactory.create(departmentEntity);

        //then
        assertThatCode(() -> majorFactory.create(departmentEntity)).doesNotThrowAnyException();
        assertThat(major).usingRecursiveComparison().isEqualTo(expected);
    }
}
