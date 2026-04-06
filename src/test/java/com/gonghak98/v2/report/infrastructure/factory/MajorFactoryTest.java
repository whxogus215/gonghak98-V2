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
            new LabMajor(Set.of("005611", "009658", "008076", "009666"), 1),
            new GeneralMajor(
                Set.of("004114", "005246", "007620", "004111", "007453", "004474", "009649", "007806",
                       "004699", "004600", "004829", "003284", "008086", "006294", "006132"),
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
