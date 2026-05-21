package com.gonghak98.v2.core.infrastructure.jpa;

import com.gonghak98.v2.core.infrastructure.entity.CourseEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCourseRepository extends JpaRepository<CourseEntity, Integer> {

    Optional<CourseEntity> findByName(String name);

    boolean existsByCode(String code);
}
