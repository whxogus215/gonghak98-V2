package com.gonghak98.v2.report.infrastructure.entity;

import com.gonghak98.v2.report.domain.course.Course;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class CourseEntity {

    @Id @Getter
    private Integer id;

    @Getter
    private String name;

    private double point;

    public Course toDomain() {
        return Course.builder()
                     .id(id)
                     .name(name)
                     .point(point)
                     .build();
    }

    public CourseEntity(Integer id, String name, double point) {
        this.id = id;
        this.name = name;
        this.point = point;
    }
}
