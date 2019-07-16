package com.jumiapay.interview.auditlogservice.repository.impl;

import com.jumiapay.interview.auditlogservice.models.Auditlog;
import com.jumiapay.interview.auditlogservice.repository.AbstractAuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AuditlogRepository extends AbstractAuditLogRepository<Auditlog> {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    protected void init() {
        abstractMongoTemplate = mongoTemplate;
        type = Auditlog.class;
    }
}
