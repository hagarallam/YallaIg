package com.yallaIg.yallaIg_backend.repository;

import com.yallaIg.yallaIg_backend.dto.response.FileResponseDto;
import com.yallaIg.yallaIg_backend.model.User;
import com.yallaIg.yallaIg_backend.repository.dto.UserDataDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    @Query("SELECT new com.yallaIg.yallaIg_backend.repository.dto.UserDataDto( " +
            "u.fullName, u.email, ci.phone, ci.country.countryId, ci.phoneCode.phoneCodeId, " +
            "COALESCE(uw.balance, 0.0), u.userImage) " +
            "FROM User u " +
            "LEFT JOIN u.contactInfo ci " +
            "LEFT JOIN ci.country c " +
            "LEFT JOIN ci.phoneCode pc " +
            "LEFT JOIN UserWallet uw ON uw.createdBy = u.userId")
    Page<UserDataDto> findAllWithWallet(Pageable pageable);

    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    Optional<User> findByUserId(Integer userId);
}
