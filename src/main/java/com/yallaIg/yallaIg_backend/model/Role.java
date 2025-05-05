package com.yallaIg.yallaIg_backend.model;

import com.yallaIg.yallaIg_backend.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer roleId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role_name")
    private RoleEnum roleEnum;


    @Override
    public String getAuthority() {
        return roleEnum.toString();
    }
}
