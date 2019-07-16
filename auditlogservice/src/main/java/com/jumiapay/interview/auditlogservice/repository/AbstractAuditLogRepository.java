package com.jumiapay.interview.auditlogservice.repository;

import com.jumiapay.interview.auditlogservice.models.Result;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

@Repository
public abstract class AbstractAuditLogRepository<T extends Object> implements IAuditLogRepository<T> {

    protected MongoTemplate abstractMongoTemplate;
    protected Class<T> type;

    @PostConstruct
    protected abstract void init();

    @Override
    public void save(T object) {
        this.abstractMongoTemplate.save(object);
    }

    @Override
    public Result<T> retrieveAllByUser(String userId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Query query = new Query().with(pageable);
        query.addCriteria(Criteria.where("userId").is(userId));

        List<T> list = abstractMongoTemplate.find(query,type);
        return new Result<>(list,list.size(),pageNumber,pageSize);
    }

    @Override
    public Result<T> retrieveAllByUser(String userId, int pageNumber, int pageSize, Date from, Date to) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Query query = new Query().with(pageable);
        query.addCriteria(Criteria.where("userId").is(userId).and("dateCreated").gte(from).lte(to));

        List<T> list = abstractMongoTemplate.find(query,type);
        return new Result<>(list,list.size(),pageNumber,pageSize);
    }

    @Override
    public Result<T> retrieveAll(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Query query = new Query().with(pageable);

        List<T> list = abstractMongoTemplate.find(query,type);
        return new Result<>(list,list.size(),pageNumber,pageSize);
    }

    @Override
    public Result<T> retrieveAll(int pageNumber, int pageSize, Date from, Date to) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Query query = new Query().with(pageable);
        query.addCriteria(Criteria.where("dateCreated").gte(from).lte(to));

        List<T> list = abstractMongoTemplate.find(query,type);
        return new Result<>(list,list.size(),pageNumber,pageSize);
    }
}
