package com.gonghak98.v2.report.infrastructure.entity;

import com.gonghak98.v2.report.domain.course.Course;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class CourseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer courseId;

    private String name;

    private double point;

    public Course toDomain() {
        return Course.builder()
                     .id(courseId)
                     .name(name)
                     .point(point)
                     .build();
    }

    public CourseEntity(Integer courseId, String name, double point) {
        this.courseId = courseId;
        this.name = name;
        this.point = point;
    }
}
