package com.yallaIg.yallaIg_backend.service.redis;


import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public abstract class RedisService<T> {

    private final RedisTemplate<String, T> redisTemplate;

    public void put(String key, String hashKey, T object, int timeout) {
        redisTemplate.opsForHash().put(key, hashKey, object);
        setTimeOut(key,timeout);
    }


    public T get(String key, String hashKey) {
        return (T) this.redisTemplate.opsForHash().get(key, hashKey);
    }

    public void delete(String key, String hashKey) {
        this.redisTemplate.opsForHash().delete(key, hashKey);
    }

    public void putAll(String key, Map<String, T> objects,int timeout) {
        this.redisTemplate.opsForHash().putAll(key, objects);
        setTimeOut(key,timeout);
    }

    public List<T> getAll(String key, List<String> hashKeys) {
        return (List<T>) this.redisTemplate.opsForHash().multiGet(key, Collections.singleton(hashKeys));
    }

    private void setTimeOut(String key,int timeout) {
        redisTemplate.expire(key, timeout, TimeUnit.HOURS);
    }


}
