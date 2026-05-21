package com.gonghak98.v2.audit.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import com.gonghak98.v2.audit.infrastructure.jpa.JpaGonghakRequirementRepository;
import com.gonghak98.v2.core.infrastructure.entity.DepartmentEntity;
import com.gonghak98.v2.core.infrastructure.jpa.JpaDepartmentRepository;
import com.gonghak98.v2.audit.infrastructure.entity.GonghakRequirementEntity;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
class JpaGonghakRequirementRepositoryTest {

    private final String testDepartmentName = "전자정보통신공학과";
    private final Short entranceYear = 2025;

    @Autowired
    private JpaDepartmentRepository departmentRepository;

    @Autowired
    private JpaGonghakRequirementRepository gonghakRequirementRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        final DepartmentEntity department = departmentRepository.save(new DepartmentEntity(testDepartmentName));

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
    @DisplayName("JSON 컬럼 데이터를 DTO 객체로 매핑할 수 있다.")
    void findByDepartmentAndEntranceYear() {
        //given
        final DepartmentEntity findDepartment = departmentRepository.findByName(testDepartmentName).orElseThrow();

        //when
        final GonghakRequirementEntity findRequirement = gonghakRequirementRepository.findByDepartmentAndEntranceYear(findDepartment, entranceYear)
                                                                                     .orElseThrow();
        //then
        assertThat(findRequirement.getDepartment()).isEqualTo(findDepartment);
        assertThat(findRequirement.getEntranceYear()).isEqualTo(entranceYear);
        assertThat(findRequirement.getDetail().getBasicRequirement().getMinCredit()).isEqualTo(30);
        assertThat(findRequirement.getDetail().getMajorRequirement().getComponents()).hasSize(3);


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
                    "name": "math",
                    "description": "수학 영역 최소 9학점 이수",
                    "ruleType": "MIN_CREDIT",
                    "conditionValue": 9,
                    "targetCourses": ["002001", "002002", "002003"]
                  }
                ]
              },
              "majorRequirement": {
                "minCredit": 45,
                "components": [
                  {
                    "name": "labMajor",
                    "description": "전공실험 최소 1과목 이수",
                    "ruleType": "MIN_COUNT",
                    "conditionValue": 1,
                    "targetCourses": ["005611", "009658", "008076", "009666"]
                  },
                  {
                    "name": "generalMajor",
                    "description": "일반전공 최소 24학점 이수",
                    "ruleType": "MIN_CREDIT",
                    "conditionValue": 24,
                    "targetCourses": ["004114", "005246", "007620", "004111", "007453", "004474", "009649"]
                  },
                  {
                    "name": "mandatoryMajor",
                    "description": "필수 전공 모두 이수 (요구 과목 개수와 동일하게 조건 설정)",
                    "ruleType": "MUST_TAKE_ALL",
                    "conditionValue": 3,
                    "targetCourses": ["001001", "001002", "001003"]
                  }
                ]
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
