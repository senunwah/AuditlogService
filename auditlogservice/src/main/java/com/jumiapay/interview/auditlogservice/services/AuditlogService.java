package com.jumiapay.interview.auditlogservice.services;

import com.jumiapay.interview.auditlogservice.models.Auditlog;
import com.jumiapay.interview.auditlogservice.models.Result;
import com.jumiapay.interview.auditlogservice.repository.impl.AuditlogRepository;
import com.jumiapay.interview.auditlogservice.repository.impl.CacheRepository;
import com.jumiapay.interview.auditlogservice.utils.AppUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;

@Service
public class AuditlogService {

    @Autowired
    private AuditlogRepository repository;
    @Autowired
    private CacheRepository cacheRepository;
    @Value("${app.redisTtl}")
    private long redisTtl;

    private Object auditlogCache;
    private static Logger logger = LoggerFactory.getLogger(AuditlogService.class);

    @PostConstruct
    public void init() {
        auditlogCache = cacheRepository.createCache(AppUtils.APPNAME,"auditlog",redisTtl);
        clearFromRedisCache();
    }

    public void save(Auditlog auditlog) {
        try {
            repository.save(auditlog);
            clearFromRedisCache();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public Result<Auditlog> retrieveByUser(String userId, int pageNumber, int pageSize) {
        try {
            String key = "retrieveByUser_userId_"+userId+"_pageNumber_"+pageNumber+"_pageSize_"+pageSize+"";
            Result<Auditlog> data = (Result<Auditlog>) cacheRepository.get(auditlogCache,key);
            if (data == null) {
                data = repository.retrieveAllByUser(userId,pageNumber,pageSize);
                if (data != null) {
                    cacheRepository.put(auditlogCache,key,data);
                }
            }
            return data;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public Result<Auditlog> retrieveRangeByUser(String userId, int pageNumber, int pageSize, Date from, Date to) {
        try {
            String key = "retrieveByUser_userId_"+userId+"_pageNumber_"+pageNumber+"_pageSize_"+pageSize+"_from_"+from+"_to_"+to+"";
            Result<Auditlog> data = (Result<Auditlog>) cacheRepository.get(auditlogCache,key);
            if (data == null) {
                data = repository.retrieveAllByUser(userId,pageNumber,pageSize,from,to);
                if (data != null) {
                    cacheRepository.put(auditlogCache,key,data);
                }
            }
            return data;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public Result<Auditlog> retrieveAll(int pageNumber, int pageSize) {
        try {
            String key = "retrieveAll_pageNumber_"+pageNumber+"_pageSize_"+pageSize+"";
            Result<Auditlog> data = (Result<Auditlog>) cacheRepository.get(auditlogCache,key);
            if (data == null) {
                data = repository.retrieveAll(pageNumber,pageSize);
                if (data != null) {
                    cacheRepository.put(auditlogCache,key,data);
                }
            }
            return data;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public Result<Auditlog> retrieveAllByRange(int pageNumber, int pageSize, Date from, Date to) {
        try {
            String key = "retrieveAllByRange_pageNumber_"+pageNumber+"_pageSize_"+pageSize+"_from_"+from+"_to_"+to+"";
            Result<Auditlog> data = (Result<Auditlog>) cacheRepository.get(auditlogCache,key);
            if (data == null) {
                data = repository.retrieveAll(pageNumber,pageSize,from,to);
                if (data != null) {
                    cacheRepository.put(auditlogCache,key,data);
                }
            }
            return data;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return null;
    }

    private void clearFromRedisCache() {
        String key = "auditlog_*";
        cacheRepository.clear(auditlogCache,key);
    }

}
