package com.gonghak98.v2.report.infrastructure.collection;

import com.gonghak98.v2.report.domain.abeek.AreaType;
import com.gonghak98.v2.report.domain.abeek.NonPassMessage;
import com.gonghak98.v2.report.domain.abeek.dto.CheckResult;
import com.gonghak98.v2.report.domain.counting.AreaCreditSummary;
import java.util.Date;
import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

@NoArgsConstructor
@Getter
public class Report {

    @Id
    private String id;

    @CreatedDate
    private Date createTime;

    @LastModifiedDate
    private Date updateTime;

    private Map<AreaType, Boolean> passResults;
    private Map<Long, NonPassMessage> nonPassResults;
    private Map<AreaType, AreaCreditSummary> creditSummaries;

    public Report(final Map<AreaType, Boolean> passResults, final Map<Long, NonPassMessage> nonPassResults) {
        this.passResults = passResults;
        this.nonPassResults = nonPassResults;
    }

    public Report(Map<AreaType, Boolean> passResults,
                  Map<Long, NonPassMessage> nonPassResults,
                  Map<AreaType, AreaCreditSummary> creditSummaries) {
        this.passResults = passResults;
        this.nonPassResults = nonPassResults;
        this.creditSummaries = creditSummaries;
    }

    public static Report toReport(final CheckResult checkResult) {
        return new Report(checkResult.passResults(), checkResult.nonPassResults(), checkResult.creditSummaries());
    }
}
