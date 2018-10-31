package org.starichkov.java.spring.cache2k;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

/**
 * @author Vadim Starichkov
 * @since 31.10.2018 18:30
 */
@Component
public class CacheRefreshEndpoint implements Endpoint<String> {
    private final CacheManager cacheManager;

    @Value("${cache.refresh.name:default}")
    private String cacheRefreshName;

    @Value("${cache.example.keys}")
    private String[] keys;

    @Autowired
    public CacheRefreshEndpoint(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public String getId() {
        return cacheRefreshName;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isSensitive() {
        return false;
    }

    @Override
    public String invoke() {
        StringBuilder stringBuilder = new StringBuilder();

        Cache cache = cacheManager.getCache(cacheRefreshName);
        for (String key : keys) {
            String value = cache.get(key, String.class);
            stringBuilder
                    .append(key)
                    .append(":")
                    .append(value)
                    .append(System.lineSeparator());
        }

        return stringBuilder.toString();
    }
}
