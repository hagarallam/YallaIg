package com.yallaIg.yallaIg_backend.service.core.crud;

import com.yallaIg.yallaIg_backend.dto.request.ChargeWalletRequestDto;
import com.yallaIg.yallaIg_backend.enums.PaymentMethodEnum;
import com.yallaIg.yallaIg_backend.model.UserWallet;
import com.yallaIg.yallaIg_backend.model.WalletTransaction;
import com.yallaIg.yallaIg_backend.repository.WalletTransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional
public class WalletTransactionServiceImpl implements WalletTransactionService{

    private final WalletTransactionRepository walletTransactionRepository;


    @Override
    public void save(UserWallet userWallet, Double amount, PaymentMethodEnum paymentMethodEnum, String transactionId,Integer userId) {
        WalletTransaction walletTransaction = new WalletTransaction();
        walletTransaction.setTransactionId(transactionId);
        walletTransaction.setUserWallet(userWallet);
        walletTransaction.setAmount(amount);
        walletTransaction.setPaymentMethodEnum(paymentMethodEnum);
        walletTransaction.setCreatedBy(userId);
        walletTransaction.setLastModifiedBy(userId);
        walletTransaction.setCreationDate(new Date());
        walletTransaction.setLastModificationDate(new Date());
        walletTransactionRepository.save(walletTransaction);
    }
}
