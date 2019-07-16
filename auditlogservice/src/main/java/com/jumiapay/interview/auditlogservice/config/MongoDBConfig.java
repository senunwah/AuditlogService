package com.jumiapay.interview.auditlogservice.config;

import com.jumiapay.interview.auditlogservice.repository.impl.AuditlogRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackageClasses = AuditlogRepository.class)
@Configuration
public class MongoDBConfig {

}
