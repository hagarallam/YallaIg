package com.yallaIg.yallaIg_backend.service.core.payment;

import com.yallaIg.yallaIg_backend.enums.PaymentMethodEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceFactory {

    private final Map<PaymentMethodEnum, PaymentService> paymentServices;

    @Autowired
    public PaymentServiceFactory(List<PaymentService> paymentServiceList) {
        paymentServices = paymentServiceList.stream()
                .collect(Collectors.toMap(PaymentService::getPaymentMethod, Function.identity()));
    }


    public PaymentService getPaymentInstance(PaymentMethodEnum paymentMethodEnum){
        PaymentService paymentService = paymentServices.get(paymentMethodEnum);
        if(Objects.isNull(paymentService))
            throw new IllegalArgumentException("Unknown payment method: " + paymentMethodEnum);

        return paymentService;
    }
}
