package com.yallaIg.yallaIg_backend.service.redis;

import com.yallaIg.yallaIg_backend.enums.PaymentMethodEnum;
import com.yallaIg.yallaIg_backend.service.redis.model.UserCacheData;
import com.yallaIg.yallaIg_backend.util.CommonUtil;
import com.yallaIg.yallaIg_backend.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserPaymentCacheService extends RedisService<UserCacheData>{

    public static final String KEY_NAME = "USERS_DATA";


    public UserPaymentCacheService(RedisTemplate<String, UserCacheData> redisTemplate) {
        super(redisTemplate);
    }

    public UserCacheData createUserCacheData(Double amount, PaymentMethodEnum paymentMethodEnum) {
        UserCacheData userCacheData = new UserCacheData();
        userCacheData.setUserId(SecurityUtil.getCurrentUserId());
        userCacheData.setAmount(amount);
        userCacheData.setPaymentMethodEnum(paymentMethodEnum);
        return userCacheData;
    }
}
