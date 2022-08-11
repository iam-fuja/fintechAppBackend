package com.decagon.fintechpaymentapisqd11b.service.serviceImpl;

import com.decagon.fintechpaymentapisqd11b.customExceptions.EmailTakenException;
import com.decagon.fintechpaymentapisqd11b.customExceptions.PasswordNotMatchingException;
import com.decagon.fintechpaymentapisqd11b.customExceptions.UserNotFoundException;
import com.decagon.fintechpaymentapisqd11b.dto.UsersRegistrationDto;
import com.decagon.fintechpaymentapisqd11b.entities.Users;
import com.decagon.fintechpaymentapisqd11b.entities.Wallet;
import com.decagon.fintechpaymentapisqd11b.enums.UsersStatus;
import com.decagon.fintechpaymentapisqd11b.repository.UsersRepository;
import com.decagon.fintechpaymentapisqd11b.repository.WalletRepository;
import com.decagon.fintechpaymentapisqd11b.service.UsersService;
import com.decagon.fintechpaymentapisqd11b.service.WalletService;
import com.decagon.fintechpaymentapisqd11b.validations.token.ConfirmationToken;
import lombok.AllArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@AllArgsConstructor
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UsersServiceImpl implements UsersService, UserDetailsService {

    private final static String USER_EMAIL_ALREADY_EXISTS_MSG = "Users with email %s already exists!";
    private final ConfirmationTokenServiceImpl confirmTokenService;
    private final WalletService walletService;
    private final WalletRepository walletRepository;
    private final UsersRepository usersRepository;

    @Value("${hash}")
    private String HASH;

    @Override
    public String registerUser(UsersRegistrationDto userRegistrationDto) throws JSONException {

        if (!userRegistrationDto.getPassword().equals(userRegistrationDto.getConfirmPassword())) {
            throw new PasswordNotMatchingException("Passwords do not match!");
        }

        boolean userExists = userRepository.findByEmail(userRegistrationDto.getEmail()).isPresent();

        if (userExists) {
            throw new EmailTakenException(
                    String.format(USER_EMAIL_ALREADY_EXISTS_MSG, userRegistrationDto.getEmail()));
        }

        Users user = new Users();
        user.setFirstName(userRegistrationDto.getFirstName());
        user.setLastName(userRegistrationDto.getLastName());
        user.setEmail(userRegistrationDto.getEmail());
        user.setPhoneNumber(userRegistrationDto.getPhoneNumber());
        user.setBVN(userRegistrationDto.getBVN());
        user.setPassword(userRegistrationDto.getPassword());
        user.setPin(userRegistrationDto.getPin());
        user.setCreatedAt(LocalDateTime.now());
        user.setUserStatus(UsersStatus.INACTIVE);
        user.setRole("USER");
        user.setUsername(userRegistrationDto.getUsername());
        Users user1 = userRepository.save(user);

        Wallet wallet = generateWallet(user1);
        wallet.setUsers(user1);
        walletRepository.save(wallet);

        String token = UUID.randomUUID().toString();
        saveToken(token, user);

        return token;
    }

    @Override
    public void saveToken(String token, Users user) {
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(24),
                user
        );
        confirmTokenService.saveConfirmationToken(confirmationToken);
    }

    @Override
    public Wallet generateWallet(Users user) throws JSONException {
        return walletService.createWallet(user);
    }

    @Override
    public void enableUser(String email) {
        Users user = userRepository.findByEmail(email).orElseThrow(() ->  new UserNotFoundException("Users not found."));
        user.setUserStatus(UsersStatus.ACTIVE);
        userRepository.save(user);
    }

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
