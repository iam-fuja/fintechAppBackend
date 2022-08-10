package com.decagon.fintechpaymentapisqd11b.service.serviceImpl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.decagon.fintechpaymentapisqd11b.entities.Users;
import com.decagon.fintechpaymentapisqd11b.repository.UsersRepository;

import com.decagon.fintechpaymentapisqd11b.service.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service @Transactional
@RequiredArgsConstructor @Slf4j
public class UsersServiceImpl implements UsersService, UserDetailsService {

    private final UsersRepository usersRepository;

    @Value("${hash}")
    private String HASH;

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try{
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256(HASH.getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                Users user = usersRepository.findUsersByUsername(username);
                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis()+ 20 * 60 * 1000) )
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRole())
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token",access_token);
                tokens.put("refresh_token",refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),tokens);
            } catch (Exception ex){

                response.setHeader("error_",ex.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> errors = new HashMap<>();
                errors.put("error_message",ex.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),errors);
            }


        }else{
            throw new RuntimeException("Refresh Token Missing");
        }

    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersRepository.findUsersByUsername(username);
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("USER");
        if(user == null){
            log.error("User not found");
            throw new UsernameNotFoundException("User not found");
        }else {
            log.info("User Found");

            return new User(user.getUsername(), user.getPassword(), Collections.singleton(authority));
        }
    }
}
