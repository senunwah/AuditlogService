package com.jumiapay.interview.auditlogservice.repository;

public interface ICacheRepository {

    public Object createCache(String appName, String cacheName, long ttl);

    public void put(Object cache, String key, Object data);

    public Object get(Object cache, String key);

    public boolean remove(Object cache, String key);

    public boolean clear(Object cache, String key);
}
