package com.yallaIg.yallaIg_backend.service.core.crud;

import com.yallaIg.yallaIg_backend.dto.request.ChargeWalletRequestDto;
import com.yallaIg.yallaIg_backend.model.User;
import com.yallaIg.yallaIg_backend.model.UserWallet;

import java.util.Optional;

public interface UserWalletService {
    void createUserWallet(User user);
    UserWallet updateUserWallet(double amount,Integer currentUserId);
    UserWallet updateUserWallet(double amount);

    boolean chargeWallet(String paymentId);

    String processPayment(ChargeWalletRequestDto chargeWalletRequestDto);

    boolean executePayment(String paymentId, String payerId);

    UserWallet getUserWallet(Integer currentUserId);

    }
