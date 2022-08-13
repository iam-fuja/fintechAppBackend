package com.decagon.fintechpaymentapisqd11b.controller;

import com.decagon.fintechpaymentapisqd11b.dto.LoginRequestPayload;
import com.decagon.fintechpaymentapisqd11b.dto.LoginResponseDto;
import com.decagon.fintechpaymentapisqd11b.dto.UsersResponse;
import com.decagon.fintechpaymentapisqd11b.service.serviceImpl.LoginServiceImpl;
import com.decagon.fintechpaymentapisqd11b.service.serviceImpl.UsersServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class UsersController {
    private final UsersServiceImpl usersService;

    private final LoginServiceImpl loginService;


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestPayload loginRequestPayload)
    {
            log.info("successful");
            String token = loginService.login(loginRequestPayload);
            return new ResponseEntity<>(new LoginResponseDto(token),HttpStatus.OK);

    }


    @GetMapping("/viewUser")
    public ResponseEntity<UsersResponse> getUser(){
        UsersResponse usersResponse = usersService.getUser();
        return new ResponseEntity<>(usersResponse, HttpStatus.OK);
    }

}
