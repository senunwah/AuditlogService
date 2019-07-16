package com.jumiapay.interview.auditlogservice.repository;

import com.jumiapay.interview.auditlogservice.models.Result;

import java.util.Date;

public interface IAuditLogRepository<T> {

    public void save(T object);

    public Result<T> retrieveAllByUser(String userId, int pageNumber, int pageSize);

    public Result<T> retrieveAllByUser(String userId, int pageNumber, int pageSize, Date from, Date to);

    public Result<T> retrieveAll(int pageNumber, int pageSize);

    public Result<T> retrieveAll(int pageNumber, int pageSize, Date from, Date to);

}
