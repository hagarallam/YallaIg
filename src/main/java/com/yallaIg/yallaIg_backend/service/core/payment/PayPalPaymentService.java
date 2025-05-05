package com.yallaIg.yallaIg_backend.service.core.payment;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.yallaIg.yallaIg_backend.enums.PaymentMethodEnum;
import com.yallaIg.yallaIg_backend.service.core.payment.model.PaymentRequest;
import com.yallaIg.yallaIg_backend.service.core.payment.model.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class PayPalPaymentService implements PaymentService{

    private final APIContext apiContext;

    private static final String CURRENCY = "USD";
    private static final String METHOD = "paypal";
    @Value("${domain-url}")
    private String domainUrl ;
    private final String successUrl = domainUrl + "/api/user-wallet/success";
    private final String cancelUrl = domainUrl + "/api/user-wallet/cancel";
    private static final String INTENT = "sale";
    private static final String DESCRIPTION = "Payment for the IGCSE course";

    @Override
    public PaymentResponse<Payment> initiatePayment(PaymentRequest paymentRequest) {
        Amount amount = new Amount();
        amount.setCurrency(CURRENCY);
        amount.setTotal(String.format(Locale.forLanguageTag(CURRENCY), "%.2f", paymentRequest.getTotal()));

        Transaction transaction = new Transaction();
        transaction.setDescription(DESCRIPTION);
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(METHOD);

        Payment payment = new Payment();
        payment.setIntent(INTENT);
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setReturnUrl(successUrl);
        redirectUrls.setCancelUrl(cancelUrl);

        payment.setRedirectUrls(redirectUrls);
        try {
            return new PaymentResponse<Payment>(payment.create(apiContext));
        } catch (PayPalRESTException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PaymentResponse<Payment> executePayment(String paymentId,String payerId) {
        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);
        try {
            return new PaymentResponse<>(payment.execute(apiContext, paymentExecution));
        } catch (PayPalRESTException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public PaymentMethodEnum getPaymentMethod() {
        return PaymentMethodEnum.PAYPAL;
    }
}
