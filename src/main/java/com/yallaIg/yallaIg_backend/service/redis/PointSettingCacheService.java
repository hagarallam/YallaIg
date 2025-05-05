package com.yallaIg.yallaIg_backend.service.redis;

import com.yallaIg.yallaIg_backend.model.PointSetting;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class PointSettingCacheService extends RedisService<PointSetting> {

    public static final String KEY_NAME = "POINTS_SETTINGS";


    public PointSettingCacheService(RedisTemplate<String, PointSetting> redisTemplate) {
        super(redisTemplate);
    }
}
