package com.gonghak98.v2.audit.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import com.gonghak98.v2.audit.domain.constant.AbeekType;
import com.gonghak98.v2.audit.infrastructure.entity.GonghakRequirementEntity;
import com.gonghak98.v2.audit.infrastructure.jpa.JpaGonghakRequirementRepository;
import com.gonghak98.v2.core.infrastructure.entity.DepartmentEntity;
import com.gonghak98.v2.core.infrastructure.jpa.JpaDepartmentRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class BasicRepositoryTest {

    private final Short entranceYear = 2025;

    @Autowired
    private BasicRepository basicRepository;

    @Autowired
    private JpaDepartmentRepository departmentRepository;

    @Autowired
    private JpaGonghakRequirementRepository gonghakRequirementRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        DepartmentEntity mscDepartment = new DepartmentEntity("전자정보통신공학과");
        DepartmentEntity bsmDepartment = new DepartmentEntity("소프트웨어학과");
        departmentRepository.save(mscDepartment);
        departmentRepository.save(bsmDepartment);

        entityManager.createNativeQuery(
                         "INSERT INTO gonghak_requirement (department_id, entrance_year, detail) VALUES (:deptId, :year, :json FORMAT JSON)")
                     .setParameter("deptId", mscDepartment.getId())
                     .setParameter("year", entranceYear)
                     .setParameter("json", createMscDetailJson())
                     .executeUpdate();

        entityManager.createNativeQuery(
                         "INSERT INTO gonghak_requirement (department_id, entrance_year, detail) VALUES (:deptId, :year, :json FORMAT JSON)")
                     .setParameter("deptId", bsmDepartment.getId())
                     .setParameter("year", entranceYear)
                     .setParameter("json", createBsmDetailJson())
                     .executeUpdate();

        entityManager.flush();
        entityManager.clear();
    }

    @ParameterizedTest
    @CsvSource({
        "전자정보통신공학과, MSC",
        "소프트웨어학과, BSM"
    })
    @DisplayName("세부요건 JSON 데이터를 조회하여 BASIC(MSC/BSM) 영역 검사 객체를 생성할 수 있다.")
    void createBasicTest(String departmentName, AbeekType expected) {
        //given
        final DepartmentEntity findDepartment = departmentRepository.findByName(departmentName).orElseThrow();
        final GonghakRequirementEntity findRequirement = gonghakRequirementRepository.findByDepartmentAndEntranceYear(findDepartment, entranceYear)
                                                                                     .orElseThrow();

        //when & then
        assertThat(basicRepository.create(findDepartment, findRequirement.getDetail().getBasicRequirement()).getAbeekType()).isEqualTo(expected);
    }

    private String createMscDetailJson() {
        return """
            {
              "basicRequirement": {
                "minCredit": 30,
                "components": [
                       {
                         "name": "mscBasic",
                         "description": "주어진 MSC 과목 모두 이수",
                         "ruleType": "MUST_TAKE_ALL",
                         "conditionValue": 9,
                         "targetCourses": [
                           "011300",
                           "007330",
                           "009912",
                           "001357",
                           "000304",
                           "009913",
                           "001725",
                           "011320",
                           "011678"
                         ]
                       }
                     ]
              }
            }
            """;
    }

    private String createBsmDetailJson() {
        return """
            {
              "basicRequirement": {
                "minCredit": 30,
                "components": [
                       {
                         "name": "bsmBasic",
                         "description": "주어진 BSM 과목 모두 이수",
                         "ruleType": "MUST_TAKE_ALL",
                         "conditionValue": 9,
                         "targetCourses": [
                           "011300",
                           "007330",
                           "009912",
                           "001357",
                           "000304",
                           "009913",
                           "001725",
                           "011320",
                           "011678"
                         ]
                       }
                     ]
              }
            }
            """;
    }
}
