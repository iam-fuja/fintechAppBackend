package com.decagon.fintechpaymentapisqd11b.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UsersRegistrationDto {
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
