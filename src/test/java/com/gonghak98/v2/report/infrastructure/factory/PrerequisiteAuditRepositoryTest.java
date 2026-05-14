package com.gonghak98.v2.report.infrastructure.factory;

import static org.assertj.core.api.Assertions.assertThatCode;

import com.gonghak98.v2.audit.infrastructure.PrerequisiteRepository;
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
class PrerequisiteAuditRepositoryTest {

    private final Short entranceYear = 2025;

    @Autowired
    private PrerequisiteRepository prerequisiteRepository;

    @Autowired
    private JpaDepartmentRepository departmentRepository;

    @Autowired
    private JpaGonghakRequirementRepository gonghakRequirementRepository;

    @Autowired
    private EntityManager entityManager;

    private DepartmentEntity department;

    @BeforeEach
    void setUp() {
        String testDepartmentName = "전자정보통신공학과";
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
    @DisplayName("세부요건 JSON 데이터를 조회하여 선후수 검사 객체를 생성할 수 있다.")
    void createTest() {
        //given
        final GonghakRequirementEntity findRequirement = gonghakRequirementRepository.findByDepartmentAndEntranceYear(department, entranceYear)
                                                                                     .orElseThrow();

        //when & then
        assertThatCode(() -> prerequisiteRepository.create(department,
                                                           findRequirement.getDetail().getPrerequisiteRequirement()))
            .doesNotThrowAnyException();
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
                "components": [ ]
              },
              "majorRequirement": {
                "minCredit": 45,
                "components": [ ]
              },
              "prerequisiteRequirement": {
                "targetCourses": [
                  { "afterCode": "004111", "beforeCode": "001357" },
                  { "afterCode": "007722", "beforeCode": "007453" },
                  { "afterCode": "009659", "beforeCode": "009649" },
                  { "afterCode": "004600", "beforeCode": "005246" },
                  { "afterCode": "004474", "beforeCode": "005246" }
                ]
              }
            }
            """;
    }
}
