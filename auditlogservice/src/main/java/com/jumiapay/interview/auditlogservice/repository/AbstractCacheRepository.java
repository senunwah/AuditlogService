package com.jumiapay.interview.auditlogservice.repository;

import com.jumiapay.interview.auditlogservice.models.CacheConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Repository
public abstract class AbstractCacheRepository implements ICacheRepository {

    protected RedisTemplate abstractRedisTemplate;

    private static Logger logger = LoggerFactory.getLogger(AbstractCacheRepository.class);

    @PostConstruct
    protected abstract void init();

    @Override
    public Object createCache(String appName, String cacheName, long ttl) {
        CacheConfig config = new CacheConfig();
        config.setCacheName(appName + ":" + cacheName + ":");
        if (ttl > 0L) {
            config.setTtl(ttl);
            config.setTtlEnabled(true);
        }

        return config;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void put(Object cache, String key, Object data) {
        try {
            CacheConfig config = (CacheConfig) cache;
            if (config != null) {
                String cacheKey = config.getCacheName() + key;
                abstractRedisTemplate.opsForValue().set(cacheKey,data);
                if (config.isTtlEnabled()) {
                    abstractRedisTemplate.expire(cacheKey,config.getTtl(), TimeUnit.SECONDS);
                }
            }
        } catch (Exception ex) {
            logger.info(ex.getMessage());
        }
    }

    @Override
    public Object get(Object cache, String key) {
        try {
            CacheConfig config = (CacheConfig) cache;
            if (config != null) {
                String cacheKey = config.getCacheName() + key;
                return this.abstractRedisTemplate.opsForValue().get(cacheKey);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean remove(Object cache, String key) {
        try {
            CacheConfig config = (CacheConfig) cache;
            if (config != null) {
                String cacheKey = config.getCacheName() + key;
                return abstractRedisTemplate.delete(cacheKey);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }

        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean clear(Object cache, String key) {
        try {
            CacheConfig config = (CacheConfig) cache;
            if (config != null) {
                String cacheKey = config.getCacheName() + key;
                Set<String> keys = this.abstractRedisTemplate.keys(cacheKey);
                for (String value : keys) {
                    this.abstractRedisTemplate.delete(value);
                }
                return true;
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }

        return false;
    }
}
