package com.jumiapay.interview.auditlogservice.models;

public class CacheConfig {

    private String cacheName;
    private boolean ttlEnabled;
    private long ttl;

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public boolean isTtlEnabled() {
        return ttlEnabled;
    }

    public void setTtlEnabled(boolean ttlEnabled) {
        this.ttlEnabled = ttlEnabled;
    }

    public long getTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }
}