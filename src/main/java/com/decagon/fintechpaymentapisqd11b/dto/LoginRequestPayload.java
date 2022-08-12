package com.decagon.fintechpaymentapisqd11b.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter @AllArgsConstructor @NoArgsConstructor
public class LoginRequestPayload {
    private String email;
    private String password;
}
