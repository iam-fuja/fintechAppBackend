package com.decagon.fintechpaymentapisqd11b.controller;

import com.decagon.fintechpaymentapisqd11b.customExceptions.WalletNotFoundException;
import com.decagon.fintechpaymentapisqd11b.dto.LoginRequestPayload;
import com.decagon.fintechpaymentapisqd11b.dto.LoginResponseDto;
import com.decagon.fintechpaymentapisqd11b.dto.WalletDto;
import com.decagon.fintechpaymentapisqd11b.service.serviceImpl.LoginServiceImpl;
import com.decagon.fintechpaymentapisqd11b.service.serviceImpl.UsersServiceImpl;
import com.decagon.fintechpaymentapisqd11b.service.serviceImpl.WalletServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
//@RequestMapping(path = "/api/")
@Slf4j
public class UsersController {

    private final LoginServiceImpl loginService;

    private final WalletServiceImpl walletService;


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestPayload loginRequestPayload)
    {
            log.info("successful");
            String token = loginService.login(loginRequestPayload);
                walletService.getToken(token);
            return new ResponseEntity<>(new LoginResponseDto(token),HttpStatus.OK);
    }

    @GetMapping("/viewWalletDetails")
    public ResponseEntity<WalletDto> viewWalletDetails()  {
        return new ResponseEntity<>(walletService.viewWalletDetails(),HttpStatus.OK);
    }

}
