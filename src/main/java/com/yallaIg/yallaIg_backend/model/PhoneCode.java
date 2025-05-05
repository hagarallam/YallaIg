package com.yallaIg.yallaIg_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "phone_codes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PhoneCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phone_code_id")
    private Integer phoneCodeId;

    @Column(name = "phone_code")
    private String phoneCode;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

}
