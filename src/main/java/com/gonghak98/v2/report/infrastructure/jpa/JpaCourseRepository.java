package com.gonghak98.v2.report.infrastructure.jpa;

import com.gonghak98.v2.report.infrastructure.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCourseRepository extends JpaRepository<CourseEntity, Integer> {

}
