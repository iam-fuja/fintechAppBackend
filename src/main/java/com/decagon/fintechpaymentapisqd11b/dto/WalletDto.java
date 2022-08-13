package com.decagon.fintechpaymentapisqd11b.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class WalletDto {

    private String accountNumber;
    private BigDecimal balance;
    private String bankName;
}
