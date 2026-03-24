package com.gonghak98.v2.report.service;

import com.gonghak98.v2.report.domain.abeek.dto.CheckResult;
import com.gonghak98.v2.report.infrastructure.collection.Report;

public interface ReportRepository {

    Report save(CheckResult checkResult);
}
