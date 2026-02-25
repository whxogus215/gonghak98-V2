package com.gonghak98.v2.report.infrastructure.collection;

import com.gonghak98.v2.report.domain.abeek.AreaType;
import com.gonghak98.v2.report.domain.abeek.NonPassMessage;
import com.gonghak98.v2.report.domain.abeek.dto.CheckResult;
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

    private Map<Integer, NonPassMessage> nonPassResults;

    public Report(final Map<AreaType, Boolean> passResults, final Map<Integer, NonPassMessage> nonPassResults) {
        this.passResults = passResults;
        this.nonPassResults = nonPassResults;
    }

    public static Report toReport(final CheckResult checkResult) {
        return new Report(checkResult.passResults(), checkResult.nonPassResults());
    }
}
