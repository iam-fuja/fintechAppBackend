package com.decagon.fintechpaymentapisqd11b.controller;

import com.decagon.fintechpaymentapisqd11b.dto.UsersRegistrationDto;
import com.decagon.fintechpaymentapisqd11b.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/vi/registration")
public class UsersRegistrationController {
        public final RegistrationService registrationService;

        @PostMapping("/register")
        public String register(@RequestBody UsersRegistrationDto userRegistrationDto) throws JSONException {
            return registrationService.register(userRegistrationDto);
        }

        @GetMapping("/confirmToken")
        public String confirmToken(@RequestParam("token") String token) throws JSONException {
            return registrationService.confirmToken(token);
        }
}
