package com.decagon.fintechpaymentapisqd11b.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Wallet extends BaseClass{

    @NotNull
    private BigInteger balance;

    @Column(length = 10, unique = true)
    @NotNull
    private String accountNumber;

    @NotNull
    private String bankName;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users users;

    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions;
}
