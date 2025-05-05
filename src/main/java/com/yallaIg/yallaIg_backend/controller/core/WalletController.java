package com.yallaIg.yallaIg_backend.controller.core;

import com.paypal.api.payments.Payment;
import com.yallaIg.yallaIg_backend.dto.request.ChargeWalletRequestDto;
import com.yallaIg.yallaIg_backend.dto.response.GenericApiResponse;
import com.yallaIg.yallaIg_backend.enums.PaymentMethodEnum;
import com.yallaIg.yallaIg_backend.service.core.crud.UserWalletService;
import com.yallaIg.yallaIg_backend.util.ApiRequestUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.yallaIg.yallaIg_backend.constants.ErrorConstants.ERROR_CHARGE_WALLET;
import static com.yallaIg.yallaIg_backend.constants.SuccessConstants.SUCCESS_CHARGE_WALLET;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@RequestMapping("/api/user-wallet")
@RestController
@RequiredArgsConstructor
public class WalletController {

    @Value("${front-port}")
    private String frontPort;

    private final UserWalletService userWalletService;

    @PostMapping("/charge")
    public void chargeUserWallet(@RequestBody @Valid ChargeWalletRequestDto chargeWalletRequestDto , HttpServletResponse response) {
        String redirectUrl = userWalletService.processPayment(chargeWalletRequestDto);
        try {
            response.sendRedirect(redirectUrl);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/success")
    public ResponseEntity<GenericApiResponse> paymentSuccess(@RequestParam String paymentId,@RequestParam("PayerID") String payerId){
        boolean isSuccessPayment = userWalletService.executePayment(paymentId,payerId);
        GenericApiResponse genericApiResponse = new GenericApiResponse();
        if(isSuccessPayment){
            boolean isSuccessfulCharge = userWalletService.chargeWallet(paymentId);
            if(isSuccessfulCharge){
                genericApiResponse.setStatusCode(OK.value());
                genericApiResponse.setMessage(SUCCESS_CHARGE_WALLET);
                return new ResponseEntity<>(genericApiResponse,OK);
            }
            else{
                genericApiResponse.setStatusCode(BAD_REQUEST.value());
                genericApiResponse.setMessage(ERROR_CHARGE_WALLET);
                return new ResponseEntity<>(genericApiResponse,BAD_REQUEST);
            }
        }
        genericApiResponse.setStatusCode(BAD_REQUEST.value());
        genericApiResponse.setMessage(ERROR_CHARGE_WALLET);
        return new ResponseEntity<>(genericApiResponse,BAD_REQUEST);

    }
    @GetMapping("/cancel")
    public void paymentCancel(HttpServletRequest request, HttpServletResponse response){
        try {
            String url = ApiRequestUtils.getAppUrl(request) + frontPort  ;
            response.sendRedirect(url + "/dashboard/my-transactions");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
