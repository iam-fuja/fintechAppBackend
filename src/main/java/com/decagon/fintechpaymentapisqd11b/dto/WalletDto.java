package com.decagon.fintechpaymentapisqd11b.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class WalletDto {

    private String accountNumber;
    private BigInteger balance;
    private String bankName = "Wema Bank";
}
