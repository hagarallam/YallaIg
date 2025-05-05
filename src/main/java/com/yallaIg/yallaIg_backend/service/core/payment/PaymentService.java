package com.yallaIg.yallaIg_backend.service.core.payment;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.yallaIg.yallaIg_backend.enums.PaymentMethodEnum;
import com.yallaIg.yallaIg_backend.service.core.payment.model.PaymentRequest;
import com.yallaIg.yallaIg_backend.service.core.payment.model.PaymentResponse;


public interface PaymentService {

    <T> PaymentResponse<T> initiatePayment(PaymentRequest paymentRequest);
    <T> PaymentResponse<T>  executePayment(String paymentId, String payerId);

    PaymentMethodEnum getPaymentMethod();
}
