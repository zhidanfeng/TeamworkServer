package com.teamwork.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class ShiroCacheManager implements CacheManager {
    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return null;
    }
}
