package com.gonghak98.v2.report.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gonghak98.v2.report.domain.abeek.AreaType;
import com.gonghak98.v2.report.domain.abeek.NonPassMessage;
import com.gonghak98.v2.report.domain.counting.AreaCreditSummary;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReportResponse {

    private String id;
    private List<PassResultDto> passResults;
    private List<NonPassResultDto> nonPassResults;
    private List<CreditSummaryDto> creditSummaries;

    @Getter
    @Builder
    public static class PassResultDto {

        private ResponseAreaType areaType;

        private boolean isPassed;

        public static PassResultDto from(AreaType areaType, boolean isPassed) {
            return PassResultDto.builder()
                                .areaType(ResponseAreaType.from(areaType))
                                .isPassed(isPassed)
                                .build();
        }
    }

    @Getter
    @Builder
    public static class NonPassResultDto {

        private Long courseId;
        private String reason;

        public static NonPassResultDto from(Long courseId, NonPassMessage reason) {
            return NonPassResultDto.builder()
                                   .courseId(courseId)
                                   .reason(reason.name())
                                   .build();
        }
    }

    @Getter
    @Builder
    public static class CreditSummaryDto {

        private String areaType;
        private double completedPoints;
        private double requiredPoints;
        private List<RelatedCourseDto> relatedCourses;

        public static CreditSummaryDto from(AreaType areaType, AreaCreditSummary summary) {
            return CreditSummaryDto.builder()
                                   .areaType(areaType.name())
                                   .completedPoints(summary.getPointCountResult().completedPoints())
                                   .requiredPoints(summary.getPointCountResult().requiredPoints())
                                   .relatedCourses(summary.getRelatedCourses().stream().map(RelatedCourseDto::from).toList())
                                   .build();
        }
    }

    @Getter
    @Builder
    public static class RelatedCourseDto {

        private Long courseId;
        private String name;
        private int year;
        private int semester;
        private double point;

        public static RelatedCourseDto from(CompletedCourse completedCourse) {
            return RelatedCourseDto.builder()
                                   .courseId(completedCourse.getId())
                                   .name(completedCourse.getName())
                                   .year(completedCourse.getYear())
                                   .semester(completedCourse.getSemester())
                                   .point(completedCourse.getPoint())
                                   .build();
        }
    }
}
