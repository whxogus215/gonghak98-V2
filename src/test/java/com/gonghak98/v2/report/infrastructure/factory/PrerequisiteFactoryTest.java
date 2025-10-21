package com.gonghak98.v2.report.infrastructure.factory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gonghak98.v2.report.domain.abeek.prerequisite.DesignPrerequisite;
import com.gonghak98.v2.report.domain.abeek.prerequisite.NonDesignPrerequisite;
import com.gonghak98.v2.report.domain.abeek.prerequisite.Prerequisite;
import com.gonghak98.v2.report.infrastructure.entity.DepartmentEntity;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PrerequisiteFactoryTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("학과별 선후수 세부정보가 담긴 JSON으로 역직렬화 할 수 있다.")
    void createTest() {
        //given
        PrerequisiteFactory prerequisiteFactory = new PrerequisiteFactory(objectMapper);
        final DepartmentEntity departmentEntity = new DepartmentEntity("전자정보통신공학과");

        Prerequisite expected = new Prerequisite(
            new NonDesignPrerequisite(Map.of(4111, 1357,
                                             7722, 7453,
                                             9659, 9649,
                                             4600, 5246,
                                             4474, 5246)),
            new DesignPrerequisite(
                7620,
                Set.of(7721, 9650, 6935, 9662, 7585, 9663),
                Set.of(9947, 9948)
            ));

        //when
        final Prerequisite prerequisite = prerequisiteFactory.create(departmentEntity);

        //then
        assertThatCode(() -> prerequisiteFactory.create(departmentEntity)).doesNotThrowAnyException();
        assertThat(prerequisite).usingRecursiveComparison().isEqualTo(expected);
    }
}
