package com.yallaIg.yallaIg_backend.service.core.crud;

import com.yallaIg.yallaIg_backend.enums.RoleEnum;
import com.yallaIg.yallaIg_backend.model.Role;
import com.yallaIg.yallaIg_backend.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService{

    private final RoleRepository repository;


    @Override
    public Optional<Role> getRoleByRoleName(RoleEnum roleEnum) {
        return repository.findByRoleEnum(roleEnum);
    }
}
