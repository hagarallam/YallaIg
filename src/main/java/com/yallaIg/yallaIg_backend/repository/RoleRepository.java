package com.yallaIg.yallaIg_backend.repository;

import com.yallaIg.yallaIg_backend.enums.RoleEnum;
import com.yallaIg.yallaIg_backend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer>  {

    Optional<Role> findByRoleEnum(RoleEnum roleEnum);
}
