package com.gonghak98.v2.report.infrastructure.jpa;

import com.gonghak98.v2.report.infrastructure.entity.DepartmentEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaDepartmentRepository extends JpaRepository<DepartmentEntity, Integer> {

    Optional<DepartmentEntity> findByName(String departmentName);
}
