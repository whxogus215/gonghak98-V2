package com.gonghak98.v2.report.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gonghak98.v2.report.domain.abeek.AbeekType;
import com.gonghak98.v2.report.domain.abeek.dto.NonPassResult;
import com.gonghak98.v2.report.domain.abeek.dto.NotCheckedResult;
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
    private List<NotCheckedResultDto> notCheckedResults;
    private List<CreditSummaryDto> creditSummaries;

    @Getter
    @Builder
    public static class PassResultDto {

        private ResponseAreaType areaType;

        @Getter(onMethod_ = {@JsonProperty("isPassed")})
        private boolean isPassed;

        public static PassResultDto from(AbeekType abeekType, boolean isPassed) {
            return PassResultDto.builder()
                                .areaType(ResponseAreaType.from(abeekType))
                                .isPassed(isPassed)
                                .build();
        }
    }

    @Getter
    @Builder
    public static class NonPassResultDto {

        private String courseCode;
        private String courseName;
        private int year;
        private int semester;
        private double credit;
        private String reason;

        public static NonPassResultDto from(NonPassResult nonPassResult) {
            return NonPassResultDto.builder()
                                   .courseCode(nonPassResult.courseCode())
                                   .courseName(nonPassResult.courseName())
                                   .year(nonPassResult.year())
                                   .semester(nonPassResult.semester())
                                   .credit(nonPassResult.credit())
                                   .reason(nonPassResult.nonPassMessage().name())
                                   .build();
        }
    }

    @Getter
    @Builder
    public static class NotCheckedResultDto {

        private String courseCode;
        private String courseName;
        private int year;
        private int semester;
        private double credit;

        public static NotCheckedResultDto from(NotCheckedResult notCheckedResult) {
            return NotCheckedResultDto.builder()
                                      .courseCode(notCheckedResult.courseCode())
                                      .courseName(notCheckedResult.courseName())
                                      .year(notCheckedResult.year())
                                      .semester(notCheckedResult.semester())
                                      .credit(notCheckedResult.credit())
                                      .build();
        }
    }

    @Getter
    @Builder
    public static class CreditSummaryDto {

        private String areaType;
        private double completedCredits;
        private double requiredCredits;
        private List<RelatedCourseDto> relatedCourses;

        public static CreditSummaryDto from(AbeekType abeekType, AreaCreditSummary summary) {
            return CreditSummaryDto.builder()
                                   .areaType(abeekType.name())
                                   .completedCredits(summary.getPointCountResult().completedPoints())
                                   .requiredCredits(summary.getPointCountResult().requiredPoints())
                                   .relatedCourses(summary.getRelatedCourses().stream().map(RelatedCourseDto::from).toList())
                                   .build();
        }
    }

    @Getter
    @Builder
    public static class RelatedCourseDto {

        private String courseCode;
        private String courseName;
        private int year;
        private int semester;
        private double credit;

        public static RelatedCourseDto from(CompletedCourse completedCourse) {
            return RelatedCourseDto.builder()
                                   .courseCode(completedCourse.getCode())
                                   .courseName(completedCourse.getName())
                                   .year(completedCourse.getYear())
                                   .semester(completedCourse.getSemester())
                                   .credit(completedCourse.getCredit())
                                   .build();
        }
    }
}
