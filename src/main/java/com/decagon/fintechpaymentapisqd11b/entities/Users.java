package com.decagon.fintechpaymentapisqd11b.entities;

import com.decagon.fintechpaymentapisqd11b.enums.UsersStatus;
import lombok.*;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

@Entity
public class Users extends BaseClass{

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @Column(unique = true, length = 11)
    @NotNull
    private String BVN;

    @Email
    @Column(unique = true)
    @NotNull
    private String email;

    @Column(length = 11)
    @NotNull
    private String phoneNumber;

    @Column(length = 64)
    @NotNull
    private String password;

    @Column(length = 4)
    @NotNull
    private String pin;

    @Enumerated(EnumType.STRING)
    @NotNull
    private UsersStatus usersStatus;

    @NotNull
    private String token;

    @OneToOne(mappedBy = "users")
    private Wallet wallet;

    private String role;

    private String username;

}

