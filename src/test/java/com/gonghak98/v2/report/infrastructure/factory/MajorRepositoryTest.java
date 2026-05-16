package com.gonghak98.v2.report.infrastructure.factory;

import static org.assertj.core.api.Assertions.assertThatCode;

import com.gonghak98.v2.audit.infrastructure.MajorRepository;
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
class MajorRepositoryTest {

    private final String testDepartmentName = "전자정보통신공학과";
    private final Short entranceYear = 2025;

    @Autowired
    private MajorRepository majorRepository;

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
    @DisplayName("세부요건 JSON 데이터를 조회하여 전공영역 검사 객체를 생성할 수 있다.")
    void createTest() {
        //given
        final GonghakRequirementEntity findRequirement = gonghakRequirementRepository.findByDepartmentAndEntranceYear(department, entranceYear)
                                                                                     .orElseThrow();

        //when & then
        assertThatCode(() -> majorRepository.create(findRequirement.getDetail().getMajorRequirement())).doesNotThrowAnyException();
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
                "targetCourses": [ ]
              }
            }
            """;
    }
}
