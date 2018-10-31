package org.starichkov.java.spring.cache2k;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author Vadim Starichkov
 * @since 31.10.2018 18:04
 */
@Component
public class CacheEndpoint implements Endpoint<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheEndpoint.class);

    private final CacheManager cacheManager;

    @Value("${cache.name:default}")
    private String cacheName;

    @Value("${cache.example.keys}")
    private String[] keys;

    @Value("${cache.example.values}")
    private String[] values;

    @Autowired
    public CacheEndpoint(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @PostConstruct
    public void init() {
        LOGGER.info("Populating cache with values");

        Cache cache = cacheManager.getCache(cacheName);
        for (int i = 0; i < keys.length; i++) {
            cache.putIfAbsent(keys[i], values[i]);
        }
    }

    @Override
    public String getId() {
        return cacheName;
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

        Cache cache = cacheManager.getCache(cacheName);
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
