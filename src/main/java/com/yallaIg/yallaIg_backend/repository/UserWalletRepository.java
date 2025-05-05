package com.yallaIg.yallaIg_backend.repository;

import com.yallaIg.yallaIg_backend.model.UserWallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserWalletRepository extends JpaRepository<UserWallet,Integer> {

    Optional<UserWallet> findByCreatedBy(Integer userId);
}
