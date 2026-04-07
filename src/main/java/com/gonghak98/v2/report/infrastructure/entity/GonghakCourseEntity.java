package com.gonghak98.v2.report.infrastructure.entity;

import com.gonghak98.v2.report.domain.abeek.AbeekType;
import com.gonghak98.v2.report.domain.abeek.CourseType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(
    name = "gonghak_course",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uq_dept_course",
            columnNames = {"department_id", "course_id"}
        )
    }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GonghakCourseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "MEDIUMINT UNSIGNED")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "department_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private DepartmentEntity department;

    @ManyToOne
    @JoinColumn(name = "course_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CourseEntity course;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "abeek_type", length = 30, nullable = false)
    private AbeekType abeekType;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "course_type", length = 30, nullable = false)
    private CourseType courseType;

    @Column(name = "design_credit", nullable = false)
    private double designCredit;

    public GonghakCourseEntity(DepartmentEntity department, AbeekType abeekType, CourseType courseType, CourseEntity course) {
        this.department = department;
        this.abeekType = abeekType;
        this.courseType = courseType;
        this.course = course;
    }
}
