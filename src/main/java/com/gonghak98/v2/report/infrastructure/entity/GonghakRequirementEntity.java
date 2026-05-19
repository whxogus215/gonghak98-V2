package com.gonghak98.v2.report.infrastructure.entity;

import com.gonghak98.v2.audit.infrastructure.dto.RequirementDetail;
import com.gonghak98.v2.core.infrastructure.entity.DepartmentEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "gonghak_requirement")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GonghakRequirementEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JdbcTypeCode(SqlTypes.SMALLINT)
    @Column(name = "id", columnDefinition = "SMALLINT UNSIGNED")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "department_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private DepartmentEntity department;

    @Column(name = "entrance_year", nullable = false, columnDefinition = "YEAR")
    private Short entranceYear;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "detail", columnDefinition = "JSON", nullable = false)
    private RequirementDetail detail;

    public GonghakRequirementEntity(DepartmentEntity department, Short entranceYear, RequirementDetail detail) {
        this.department = department;
        this.entranceYear = entranceYear;
        this.detail = detail;
    }
}
