package com.gonghak98.v2.report.infrastructure.entity;

import com.gonghak98.v2.report.domain.abeek.AreaType;
import com.gonghak98.v2.report.domain.abeek.CourseType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class GonghakCourseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private DepartmentEntity department;

    private AreaType category;

    @Getter
    private CourseType subCategory;

    @Getter
    @OneToOne
    private CourseEntity course;

    public GonghakCourseEntity(DepartmentEntity department, AreaType category, CourseType subCategory, CourseEntity course) {
        this.department = department;
        this.category = category;
        this.subCategory = subCategory;
        this.course = course;
    }
}
