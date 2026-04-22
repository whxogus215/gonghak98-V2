package com.gonghak98.v2.report.infrastructure.factory;

import static org.assertj.core.api.Assertions.assertThatCode;

import com.gonghak98.v2.report.infrastructure.entity.DepartmentEntity;
import com.gonghak98.v2.report.infrastructure.entity.GonghakRequirementEntity;
import com.gonghak98.v2.report.infrastructure.jpa.JpaDepartmentRepository;
import com.gonghak98.v2.report.infrastructure.jpa.JpaGonghakRequirementRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class BasicFactoryTest {

    private final String testDepartmentName = "전자정보통신공학과";
    private final Short entranceYear = 2025;

    @Autowired
    private BasicFactory basicFactory;

    @Autowired
    private JpaDepartmentRepository departmentRepository;

    @Autowired
    private JpaGonghakRequirementRepository gonghakRequirementRepository;

    @Autowired
    private EntityManager entityManager;

    private DepartmentEntity department;

    @BeforeEach
    void setUp() {
        department = departmentRepository.save(new DepartmentEntity(testDepartmentName));

        entityManager.createNativeQuery(
                         "INSERT INTO gonghak_requirement (department_id, entrance_year, detail) VALUES (:deptId, :year, :json FORMAT JSON)")
                     .setParameter("deptId", department.getId())
                     .setParameter("year", entranceYear)
                     .setParameter("json", createDetailJson())
                     .executeUpdate();

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    @DisplayName("세부요건 JSON 데이터를 조회하여 MSC 영역 검사 객체를 생성할 수 있다.")
    void createTest() {
        //given
        final GonghakRequirementEntity findRequirement = gonghakRequirementRepository.findByDepartmentAndEntranceYear(department, entranceYear)
                                                                                     .orElseThrow();

        //when & then
        assertThatCode(() -> basicFactory.create(department, findRequirement.getDetail().getBasicRequirement())).doesNotThrowAnyException();
    }

    private String createDetailJson() {
        return """
            {
              "totalRequirement": {
                "minCredit": 86
              },
              "designRequirement": {
                "minCredit": 9
              },
              "basicRequirement": {
                "minCredit": 30,
                "components": [
                       {
                         "name": "msdBasic",
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
              },
              "majorRequirement": {
                "minCredit": 45,
                "components": [ ]
              },
              "prerequisiteRequirement": {
                "targetCourses": [ ]
              }
            }
            """;
    }
}
