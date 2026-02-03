package com.gonghak98.v2.report.infrastructure.mongo;

import com.gonghak98.v2.report.infrastructure.collection.Report;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoDBReportRepository extends MongoRepository<Report, String> {

}
