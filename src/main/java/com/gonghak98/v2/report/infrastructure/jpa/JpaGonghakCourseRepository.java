package com.gonghak98.v2.report.infrastructure.jpa;

import com.gonghak98.v2.report.domain.abeek.AbeekType;
import com.gonghak98.v2.report.infrastructure.entity.DepartmentEntity;
import com.gonghak98.v2.report.infrastructure.entity.GonghakCourseEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaGonghakCourseRepository extends JpaRepository<GonghakCourseEntity, Integer> {

    List<GonghakCourseEntity> findByDepartmentAndCategory(DepartmentEntity department, AbeekType gyoyang);
}
