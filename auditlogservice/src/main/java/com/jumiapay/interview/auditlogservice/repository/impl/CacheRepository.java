package com.jumiapay.interview.auditlogservice.repository.impl;

import com.jumiapay.interview.auditlogservice.repository.AbstractCacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CacheRepository extends AbstractCacheRepository {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    protected void init() {
        abstractRedisTemplate = redisTemplate;
    }

}
