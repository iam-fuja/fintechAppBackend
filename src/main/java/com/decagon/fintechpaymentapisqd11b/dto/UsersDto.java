package com.decagon.fintechpaymentapisqd11b.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersDto {
    private String firstName;
    private String lastName;
    private String username;
    private String BVN;
    private String email;
    private String phoneNumber;
    private String password;
    private String confirmPassword;
    private String pin;
}
