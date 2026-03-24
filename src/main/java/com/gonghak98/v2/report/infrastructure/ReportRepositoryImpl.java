package com.gonghak98.v2.report.infrastructure;

import com.gonghak98.v2.report.domain.abeek.dto.CheckResult;
import com.gonghak98.v2.report.infrastructure.collection.Report;
import com.gonghak98.v2.report.infrastructure.mongo.MongoDBReportRepository;
import com.gonghak98.v2.report.service.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReportRepositoryImpl implements ReportRepository {

    private final MongoDBReportRepository mongoDBReportRepository;

    @Override
    public Report save(final CheckResult checkResult) {
        return mongoDBReportRepository.save(Report.toReport(checkResult));
    }
}
