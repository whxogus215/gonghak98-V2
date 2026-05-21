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

    private final String mscDepartmentName = "전자정보통신공학과";
    private final String bsmDepartmentName = "소프트웨어학과";

    @Autowired
    private JpaDepartmentRepository jpaDepartmentRepository;

    @Autowired
    private JpaCourseRepository jpaCourseRepository;

    @Autowired
    private JpaGonghakCourseRepository jpaGonghakCourseRepository;

    @BeforeAll
    void setUp() {
        DepartmentEntity mscDepartment = new DepartmentEntity(mscDepartmentName);
        DepartmentEntity bsmDepartment = new DepartmentEntity(bsmDepartmentName);
        jpaDepartmentRepository.save(mscDepartment);
        jpaDepartmentRepository.save(bsmDepartment);

        CourseEntity gyoyangCourse = new CourseEntity("001234", "전문교양 과목", 3.0);
        CourseEntity basicCourse = new CourseEntity("005678", "MSC/BSM 과목>", 3.0);

        jpaCourseRepository.save(gyoyangCourse);
        jpaCourseRepository.save(basicCourse);

        GonghakCourseEntity gyoyangGonghakCourse = new GonghakCourseEntity(mscDepartment, AbeekType.GYOYANG, CourseType.ESSENTIAL, gyoyangCourse);
        GonghakCourseEntity mscGonghakCourse = new GonghakCourseEntity(mscDepartment, AbeekType.MSC, CourseType.ESSENTIAL, basicCourse);
        GonghakCourseEntity bsmGonghakCourse = new GonghakCourseEntity(bsmDepartment, AbeekType.BSM, CourseType.ESSENTIAL, basicCourse);

        jpaGonghakCourseRepository.save(gyoyangGonghakCourse);
        jpaGonghakCourseRepository.save(mscGonghakCourse);
        jpaGonghakCourseRepository.save(bsmGonghakCourse);
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
        final DepartmentEntity department = jpaDepartmentRepository.findByName(mscDepartmentName)
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
        final List<GonghakCourseEntity> gonghakCourses = jpaGonghakCourseRepository.findAllByDepartmentNameAndCourseCodeIn(mscDepartmentName, courseCodes);

        //then
        assertThat(gonghakCourses).hasSize(2);
    }

    @Test
    @DisplayName("같은 과목이라도 학과에 맞는 공학인증 과목을 조회한다.")
    void 기이수_과목_코드로_공학인증_과목_엔티티_조회2() {
        //given
        List<String> courseCodes = List.of("005678");

        //when
        final List<GonghakCourseEntity> mscGonghakCourses = jpaGonghakCourseRepository.findAllByDepartmentNameAndCourseCodeIn(mscDepartmentName, courseCodes);
        final List<GonghakCourseEntity> bsmGonghakCourses = jpaGonghakCourseRepository.findAllByDepartmentNameAndCourseCodeIn(bsmDepartmentName, courseCodes);

        //then
        assertThat(mscGonghakCourses).hasSize(1);
        assertThat(mscGonghakCourses.get(0).getAbeekType()).isEqualTo(AbeekType.MSC);

        assertThat(bsmGonghakCourses).hasSize(1);
        assertThat(bsmGonghakCourses.get(0).getAbeekType()).isEqualTo(AbeekType.BSM);
    }
}
