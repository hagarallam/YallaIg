package com.yallaIg.yallaIg_backend.repository;

import com.yallaIg.yallaIg_backend.model.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletTransactionRepository extends JpaRepository<WalletTransaction,Integer> {
}
