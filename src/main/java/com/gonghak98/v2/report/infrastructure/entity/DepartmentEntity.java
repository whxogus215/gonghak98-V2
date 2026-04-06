package com.gonghak98.v2.report.infrastructure.entity;

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
@Table(name = "department")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DepartmentEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "SMALLINT UNSIGNED")
    private Integer id;

    @Getter
    @Column(name = "name", length = 20, nullable = false, unique = true)
    private String name;

    public DepartmentEntity(String name) {
        this.name = name;
    }
}
