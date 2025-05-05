package com.yallaIg.yallaIg_backend.model;

import com.yallaIg.yallaIg_backend.enums.PaymentMethodEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "wallet_transactions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WalletTransaction extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "transaction_id")
    private String transactionId;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private UserWallet userWallet;

    @Column(name = "amount")
    private Double amount = 0.0;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethodEnum paymentMethodEnum;

}
