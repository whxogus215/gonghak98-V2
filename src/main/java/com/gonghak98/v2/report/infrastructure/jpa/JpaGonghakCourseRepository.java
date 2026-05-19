package com.gonghak98.v2.report.infrastructure.jpa;

import com.gonghak98.v2.audit.domain.constant.AbeekType;
import com.gonghak98.v2.core.infrastructure.entity.DepartmentEntity;
import com.gonghak98.v2.report.infrastructure.entity.GonghakCourseEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaGonghakCourseRepository extends JpaRepository<GonghakCourseEntity, Integer> {

    List<GonghakCourseEntity> findByDepartmentAndAbeekType(DepartmentEntity department, AbeekType abeekType);

    @Query(
        "SELECT gc FROM GonghakCourseEntity gc "
            + "JOIN FETCH gc.course c "
            + "JOIN FETCH gc.department d "
            + "WHERE d.name = :departmentName "
            + "AND c.code IN :courseCodes"
    )
    List<GonghakCourseEntity> findAllByDepartmentNameAndCourseCodeIn(@Param("departmentName") String departmentName,
                                                                     @Param("courseCodes") List<String> courseCodes);
}
