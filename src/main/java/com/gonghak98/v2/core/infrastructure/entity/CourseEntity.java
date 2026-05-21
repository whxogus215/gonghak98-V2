package com.gonghak98.v2.core.infrastructure.entity;

import com.gonghak98.v2.core.domain.course.Course;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "course")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "MEDIUMINT UNSIGNED")
    private Integer id;

    @Column(name = "code", length = 20, nullable = false, unique = true)
    private String code;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "credit", nullable = false)
    private double credit;

    public CourseEntity(String code, String name, double credit) {
        this.code = code;
        this.name = name;
        this.credit = credit;
    }

    public Course toDomain() {
        return Course.builder()
                     .code(code)
                     .name(name)
                     .credit(credit)
                     .build();
    }
}
