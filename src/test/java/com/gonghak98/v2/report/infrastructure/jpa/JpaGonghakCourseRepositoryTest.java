package com.gonghak98.v2.report.infrastructure.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import com.gonghak98.v2.report.domain.abeek.AbeekType;
import com.gonghak98.v2.report.domain.abeek.CourseType;
import com.gonghak98.v2.report.infrastructure.entity.CourseEntity;
import com.gonghak98.v2.report.infrastructure.entity.DepartmentEntity;
import com.gonghak98.v2.report.infrastructure.entity.GonghakCourseEntity;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class JpaGonghakCourseRepositoryTest {

    private final String testDepartmentName = "전자정보통신공학과";

    @Autowired
    private JpaDepartmentRepository jpaDepartmentRepository;
    @Autowired
    private JpaCourseRepository jpaCourseRepository;
    @Autowired
    private JpaGonghakCourseRepository jpaGonghakCourseRepository;

    private DepartmentEntity departmentEntity;
    private CourseEntity courseEntity;
    private GonghakCourseEntity gonghakCourseEntity;

    @BeforeEach
    void setUp() {
        departmentEntity = new DepartmentEntity(testDepartmentName);
        jpaDepartmentRepository.save(departmentEntity);

        courseEntity = new CourseEntity("001234", "테스트", 3.0);
        jpaCourseRepository.save(courseEntity);

        gonghakCourseEntity = new GonghakCourseEntity(departmentEntity, AbeekType.GYOYANG, CourseType.ESSENTIAL, courseEntity);
        jpaGonghakCourseRepository.save(gonghakCourseEntity);
    }

    @AfterEach
    void tearDown() {
        jpaGonghakCourseRepository.delete(gonghakCourseEntity);
        jpaDepartmentRepository.delete(departmentEntity);
        jpaCourseRepository.delete(courseEntity);
    }

    @Test
    @DisplayName("학과이름과 영역 타입으로 원하는 공학인증 과목을 조회할 수 있다.")
    void 공학인증_과목_엔티티_조회() {
        //given
        final DepartmentEntity department = jpaDepartmentRepository.findByName(testDepartmentName)
                                                                   .orElseThrow();
        //when
        final List<GonghakCourseEntity> gonghakCourses = jpaGonghakCourseRepository.findByDepartmentAndCategory(department, AbeekType.GYOYANG);

        //then
        assertThat(gonghakCourses).hasSize(1);
    }
}
