package com.gonghak98.v2.report.infrastructure.jpa;

import com.gonghak98.v2.report.infrastructure.entity.DepartmentEntity;
import com.gonghak98.v2.report.infrastructure.entity.GonghakRequirementEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaGonghakRequirementRepository extends JpaRepository<GonghakRequirementEntity, Integer> {

    Optional<GonghakRequirementEntity> findByDepartmentAndEntranceYear(DepartmentEntity department, Short entranceYear);
}
