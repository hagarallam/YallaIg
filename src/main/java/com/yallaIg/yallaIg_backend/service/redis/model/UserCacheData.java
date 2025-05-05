package com.yallaIg.yallaIg_backend.service.redis.model;

import com.yallaIg.yallaIg_backend.enums.PaymentMethodEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCacheData implements Serializable {

    private Integer userId;
    private Double amount ;
    private PaymentMethodEnum paymentMethodEnum;

}
