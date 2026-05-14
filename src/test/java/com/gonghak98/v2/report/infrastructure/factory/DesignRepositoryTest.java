package com.gonghak98.v2.report.infrastructure.factory;

import static org.assertj.core.api.Assertions.assertThatCode;

import com.gonghak98.v2.audit.domain.constant.AbeekType;
import com.gonghak98.v2.audit.domain.constant.CourseType;
import com.gonghak98.v2.audit.infrastructure.DesignRepository;
import com.gonghak98.v2.report.infrastructure.entity.CourseEntity;
import com.gonghak98.v2.report.infrastructure.entity.DepartmentEntity;
import com.gonghak98.v2.report.infrastructure.entity.GonghakCourseEntity;
import com.gonghak98.v2.report.infrastructure.jpa.JpaCourseRepository;
import com.gonghak98.v2.report.infrastructure.jpa.JpaDepartmentRepository;
import com.gonghak98.v2.report.infrastructure.jpa.JpaGonghakCourseRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class DesignRepositoryTest {

    private final double testMinDesignCredit = 9.0;
    private final String testDepartmentName = "전자정보통신공학과";

    @Autowired
    private JpaGonghakCourseRepository jpaGonghakCourseRepository;

    @Autowired
    private JpaDepartmentRepository jpaDepartmentRepository;

    @Autowired
    private JpaCourseRepository jpaCourseRepository;

    @Autowired
    private DesignRepository designRepository;

    @BeforeEach
    void setUp() {
        DepartmentEntity departmentEntity = new DepartmentEntity(testDepartmentName);
        jpaDepartmentRepository.save(departmentEntity);

        CourseEntity baseCourseEntity = new CourseEntity("000001", "기초설계", 3.0);
        jpaCourseRepository.save(baseCourseEntity);

        CourseEntity elementCourseEntity = new CourseEntity("000002", "요소설계", 3.0);
        jpaCourseRepository.save(elementCourseEntity);

        CourseEntity comprehensiveCourseEntity = new CourseEntity("000003", "종합설계", 3.0);
        jpaCourseRepository.save(comprehensiveCourseEntity);

        jpaGonghakCourseRepository.save(new GonghakCourseEntity(departmentEntity, AbeekType.DESIGN, CourseType.DESIGN_BASIC, baseCourseEntity));
        jpaGonghakCourseRepository.save(new GonghakCourseEntity(departmentEntity, AbeekType.DESIGN, CourseType.DESIGN_ELEMENT, elementCourseEntity));
        jpaGonghakCourseRepository.save(
            new GonghakCourseEntity(departmentEntity, AbeekType.DESIGN, CourseType.DESIGN_COMPREHENSIVE, comprehensiveCourseEntity));
    }

    @AfterEach
    void tearDown() {
        jpaGonghakCourseRepository.deleteAll();
        jpaDepartmentRepository.deleteAll();
    }

    @Test
    @DisplayName("학과별 설계과목의 설계학점이 담긴 JSON으로 역직렬화 할 수 있다.")
    void createTest() {
        //given
        final DepartmentEntity departmentEntity = jpaDepartmentRepository.findByName(testDepartmentName).orElseThrow();

        //when & then
        assertThatCode(() -> designRepository.create(departmentEntity, testMinDesignCredit)).doesNotThrowAnyException();
    }
}
