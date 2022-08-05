package com.decagon.fintechpaymentapisqd11b.entities;

import com.decagon.fintechpaymentapisqd11b.enums.TransactionType;
import com.decagon.fintechpaymentapisqd11b.enums.UsersStatus;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.*;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

@Entity
public class Transaction extends BaseClass{

    @NotNull
    private BigInteger amount;

    @NotNull
    @Column(length = 11)
    private String sourceAccount;

    @NotNull
    private String narration;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UsersStatus usersStatus;

    @NotNull
    private String clientRef;

    @NotNull
    private String flwRef;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @NotNull
    private Date transactionDate;

    @ManyToOne
    @JoinColumn(name = "wallet_id", referencedColumnName = "id")
    private Wallet wallet;
}

