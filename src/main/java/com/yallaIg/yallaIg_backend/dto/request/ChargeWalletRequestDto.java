package com.yallaIg.yallaIg_backend.dto.request;

import com.yallaIg.yallaIg_backend.enums.PaymentMethodEnum;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.yallaIg.yallaIg_backend.constants.ErrorConstants.ERROR_CHARGE_AMOUNT;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChargeWalletRequestDto {

    @Min(value = 1,message = ERROR_CHARGE_AMOUNT)
    private Double amount ;
    private PaymentMethodEnum paymentMethodEnum;
}
