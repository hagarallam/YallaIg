package com.yallaIg.yallaIg_backend.service.core.crud;

import com.yallaIg.yallaIg_backend.enums.RoleEnum;
import com.yallaIg.yallaIg_backend.model.Role;
import java.util.Optional;

public interface RoleService {

    Optional<Role> getRoleByRoleName(RoleEnum roleEnum);
}
