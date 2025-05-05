package com.yallaIg.yallaIg_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users_contact_info")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserContactInfo {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_info_id")
    private Integer contactInfoId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "phone")
    private String phone;

    @ManyToOne
    @JoinColumn(name = "phone_code_id")
    private PhoneCode phoneCode;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

}
