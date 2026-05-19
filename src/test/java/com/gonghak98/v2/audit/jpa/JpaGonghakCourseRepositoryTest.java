package com.gonghak98.v2.audit.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import com.gonghak98.v2.audit.domain.constant.AbeekType;
import com.gonghak98.v2.audit.domain.constant.CourseType;
import com.gonghak98.v2.audit.infrastructure.entity.GonghakCourseEntity;
import com.gonghak98.v2.audit.infrastructure.jpa.JpaGonghakCourseRepository;
import com.gonghak98.v2.core.infrastructure.entity.CourseEntity;
import com.gonghak98.v2.core.infrastructure.entity.DepartmentEntity;
import com.gonghak98.v2.core.infrastructure.jpa.JpaCourseRepository;
import com.gonghak98.v2.core.infrastructure.jpa.JpaDepartmentRepository;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JpaGonghakCourseRepositoryTest {

    private final String testDepartmentName = "전자정보통신공학과";

    @Autowired
    private JpaDepartmentRepository jpaDepartmentRepository;

    @Autowired
    private JpaCourseRepository jpaCourseRepository;

    @Autowired
    private JpaGonghakCourseRepository jpaGonghakCourseRepository;

    private DepartmentEntity departmentEntity;

    @BeforeAll
    void setUp() {
        departmentEntity = new DepartmentEntity(testDepartmentName);
        jpaDepartmentRepository.save(departmentEntity);

        CourseEntity courseEntity = new CourseEntity("001234", "전문교양 과목", 3.0);
        CourseEntity courseEntity2 = new CourseEntity("005678", "MSC 과목", 3.0);

        jpaCourseRepository.save(courseEntity);
        jpaCourseRepository.save(courseEntity2);

        GonghakCourseEntity gonghakCourseEntity = new GonghakCourseEntity(departmentEntity, AbeekType.GYOYANG, CourseType.ESSENTIAL, courseEntity);
        GonghakCourseEntity gonghakCourseEntity2 = new GonghakCourseEntity(departmentEntity, AbeekType.MSC, CourseType.ESSENTIAL, courseEntity2);
        jpaGonghakCourseRepository.save(gonghakCourseEntity);
        jpaGonghakCourseRepository.save(gonghakCourseEntity2);
    }

    @AfterAll
    void tearDown() {
        jpaGonghakCourseRepository.deleteAll();
        jpaDepartmentRepository.deleteAll();
        jpaCourseRepository.deleteAll();
    }

    @Test
    @DisplayName("학과이름과 영역 타입으로 원하는 공학인증 과목을 조회할 수 있다.")
    void 공학인증_과목_엔티티_조회() {
        //given
        final DepartmentEntity department = jpaDepartmentRepository.findByName(testDepartmentName)
                                                                   .orElseThrow();
        //when
        final List<GonghakCourseEntity> gonghakCourses = jpaGonghakCourseRepository.findByDepartmentAndAbeekType(department, AbeekType.GYOYANG);

        //then
        assertThat(gonghakCourses).hasSize(1);
    }

    @Test
    @DisplayName("학과이름과 과목 코드 리스트로 일치하는 공학인증 과목 리스트를 조회할 수 있다.")
    void 기이수_과목_코드로_공학인증_과목_엔티티_조회() {
        //given
        List<String> courseCodes = List.of("001234", "005678");

        //when
        final List<GonghakCourseEntity> gonghakCourses = jpaGonghakCourseRepository.findAllByDepartmentNameAndCourseCodeIn(testDepartmentName, courseCodes);

        //then
        assertThat(gonghakCourses).hasSize(2);
    }
}
