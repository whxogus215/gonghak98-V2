package com.gonghak98.v2.audit.infrastructure.jpa;

import com.gonghak98.v2.audit.infrastructure.entity.GonghakRequirementEntity;
import com.gonghak98.v2.core.infrastructure.entity.DepartmentEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaGonghakRequirementRepository extends JpaRepository<GonghakRequirementEntity, Integer> {

    Optional<GonghakRequirementEntity> findByDepartmentAndEntranceYear(DepartmentEntity department, Short entranceYear);
}
