package com.decagon.fintechpaymentapisqd11b.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UsersDTO {
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
