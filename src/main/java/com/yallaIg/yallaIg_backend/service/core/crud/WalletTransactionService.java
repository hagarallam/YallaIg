package com.yallaIg.yallaIg_backend.service.core.crud;

import com.yallaIg.yallaIg_backend.dto.request.ChargeWalletRequestDto;
import com.yallaIg.yallaIg_backend.enums.PaymentMethodEnum;
import com.yallaIg.yallaIg_backend.model.UserWallet;

public interface WalletTransactionService {
    void save(UserWallet userWallet, Double amount, PaymentMethodEnum paymentMethodEnum, String transactionId,Integer userId);
}
