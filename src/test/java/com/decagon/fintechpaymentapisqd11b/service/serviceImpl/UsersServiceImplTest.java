package com.decagon.fintechpaymentapisqd11b.service.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.decagon.fintechpaymentapisqd11b.customExceptions.EmailTakenException;
import com.decagon.fintechpaymentapisqd11b.customExceptions.PasswordNotMatchingException;
import com.decagon.fintechpaymentapisqd11b.dto.UsersDTO;
import com.decagon.fintechpaymentapisqd11b.dto.UsersResponse;
import com.decagon.fintechpaymentapisqd11b.entities.Users;
import com.decagon.fintechpaymentapisqd11b.entities.Wallet;
import com.decagon.fintechpaymentapisqd11b.enums.UsersStatus;
import com.decagon.fintechpaymentapisqd11b.repository.UsersRepository;
import com.decagon.fintechpaymentapisqd11b.repository.WalletRepository;
import com.decagon.fintechpaymentapisqd11b.security.filter.JwtUtils;
import com.decagon.fintechpaymentapisqd11b.service.WalletService;
import com.decagon.fintechpaymentapisqd11b.validations.token.ConfirmationToken;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UsersServiceImpl.class, BCryptPasswordEncoder.class,
        WalletService.class})
@ExtendWith(SpringExtension.class)
class UsersServiceImplTest {
    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private WalletServiceImpl walletServiceImpl;

    @MockBean
    private ConfirmationTokenServiceImpl confirmationTokenServiceImpl;

    @MockBean
    private UsersRepository usersRepository;

    @Autowired
    private UsersServiceImpl usersServiceImpl;

    @MockBean
    private WalletRepository walletRepository;

    @MockBean
    private WalletService walletService;


    @Test
    void testRegisterUser() throws JSONException {
        doNothing().when(confirmationTokenServiceImpl).saveConfirmationToken((ConfirmationToken) any());

        Users users = new Users();
        users.setBVN("BVN");
        users.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users.setEmail("jane.doe@example.org");
        users.setFirstName("Jane");
        users.setId(123L);
        users.setLastName("Doe");
        users.setPassword("iloveyou");
        users.setPhoneNumber("4105551212");
        users.setPin("Pin");
        users.setRole("Role");
        users.setToken("ABC123");
        users.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users.setUsername("janedoe");
        users.setUsersStatus(UsersStatus.ACTIVE);

        Wallet wallet = new Wallet();
        wallet.setAccountNumber("42");
        wallet.setBalance(BigDecimal.valueOf(42L));
        wallet.setBankName("Bank Name");
        wallet.setCreateAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet.setId(123L);
        wallet.setModifyAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet.setTransactions(new ArrayList<>());
        wallet.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet.setUsers(users);

        users.setWallet(wallet);

        Optional<Users> ofResult = Optional.of(users);
        when(usersRepository.save((Users) any())).thenReturn(users);
        when(usersRepository.findByEmail((String) any())).thenReturn(ofResult);
        assertThrows(EmailTakenException.class, () -> usersServiceImpl.registerUser(new UsersDTO("Jane", "Doe", "janedoe",
                "BVN", "jane.doe@example.org", "4105551212", "iloveyou", "iloveyou", "Pin")));
        verify(usersRepository).findByEmail((String) any());
    }


    @Test
    void testSaveToken() {
        doNothing().when(confirmationTokenServiceImpl).saveConfirmationToken((ConfirmationToken) any());

        Wallet wallet = new Wallet();
        wallet.setAccountNumber("42");
        wallet.setBalance(BigDecimal.valueOf(42L));
        wallet.setBankName("Bank Name");
        wallet.setCreateAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet.setId(123L);
        wallet.setModifyAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet.setTransactions(new ArrayList<>());
        wallet.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));

        Users users = new Users();
        users.setBVN("BVN");
        users.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users.setEmail("jane.doe@example.org");
        users.setFirstName("Jane");
        users.setId(123L);
        users.setLastName("Doe");
        users.setPassword("iloveyou");
        users.setPhoneNumber("4105551212");
        users.setPin("Pin");
        users.setRole("Role");
        users.setToken("ABC123");
        users.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users.setUsername("janedoe");
        users.setUsersStatus(UsersStatus.ACTIVE);
        users.setWallet(wallet);

        wallet.setUsers(users);

        usersServiceImpl.saveToken("ABC123", users);
        verify(confirmationTokenServiceImpl).saveConfirmationToken((ConfirmationToken) any());
    }


    @Test
    void testGenerateWallet() throws JSONException {
        Wallet wallet = new Wallet();
        wallet.setAccountNumber("42");
        wallet.setBalance(null);
        wallet.setBankName("Bank Name");
        wallet.setCreateAt(null);
        wallet.setCreatedAt(null);
        wallet.setId(123L);
        wallet.setModifyAt(null);
        wallet.setTransactions(new ArrayList<>());
        wallet.setUpdatedAt(null);
        wallet.setUsers(new Users());

        Users users = new Users();
        users.setBVN("BVN");
        users.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users.setEmail("jane.doe@example.org");
        users.setFirstName("Jane");
        users.setId(123L);
        users.setLastName("Doe");
        users.setPassword("iloveyou");
        users.setPhoneNumber("4105551212");
        users.setPin("Pin");
        users.setRole("Role");
        users.setToken("ABC123");
        users.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users.setUsername("janedoe");
        users.setUsersStatus(UsersStatus.ACTIVE);
        users.setWallet(wallet);

        Wallet wallet1 = new Wallet();
        wallet1.setAccountNumber("42");
        wallet1.setBalance(BigDecimal.valueOf(42L));
        wallet1.setBankName("Bank Name");
        wallet1.setCreateAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet1.setId(123L);
        wallet1.setModifyAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet1.setTransactions(new ArrayList<>());
        wallet1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet1.setUsers(users);

        Users users1 = new Users();
        users1.setBVN("BVN");
        users1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users1.setEmail("jane.doe@example.org");
        users1.setFirstName("Jane");
        users1.setId(123L);
        users1.setLastName("Doe");
        users1.setPassword("iloveyou");
        users1.setPhoneNumber("4105551212");
        users1.setPin("Pin");
        users1.setRole("Role");
        users1.setToken("ABC123");
        users1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users1.setUsername("janedoe");
        users1.setUsersStatus(UsersStatus.ACTIVE);
        users1.setWallet(wallet1);

        Wallet wallet2 = new Wallet();
        wallet2.setAccountNumber("42");
        wallet2.setBalance(BigDecimal.valueOf(42L));
        wallet2.setBankName("Bank Name");
        wallet2.setCreateAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet2.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet2.setId(123L);
        wallet2.setModifyAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet2.setTransactions(new ArrayList<>());
        wallet2.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet2.setUsers(users1);
        when(walletService.createWallet((Users) any())).thenReturn(wallet2);

        Wallet wallet3 = new Wallet();
        wallet3.setAccountNumber("42");
        wallet3.setBalance(null);
        wallet3.setBankName("Bank Name");
        wallet3.setCreateAt(null);
        wallet3.setCreatedAt(null);
        wallet3.setId(123L);
        wallet3.setModifyAt(null);
        wallet3.setTransactions(new ArrayList<>());
        wallet3.setUpdatedAt(null);
        wallet3.setUsers(new Users());

        Users users2 = new Users();
        users2.setBVN("BVN");
        users2.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users2.setEmail("jane.doe@example.org");
        users2.setFirstName("Jane");
        users2.setId(123L);
        users2.setLastName("Doe");
        users2.setPassword("iloveyou");
        users2.setPhoneNumber("4105551212");
        users2.setPin("Pin");
        users2.setRole("Role");
        users2.setToken("ABC123");
        users2.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users2.setUsername("janedoe");
        users2.setUsersStatus(UsersStatus.ACTIVE);
        users2.setWallet(wallet3);

        Wallet wallet4 = new Wallet();
        wallet4.setAccountNumber("42");
        wallet4.setBalance(BigDecimal.valueOf(42L));
        wallet4.setBankName("Bank Name");
        wallet4.setCreateAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet4.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet4.setId(123L);
        wallet4.setModifyAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet4.setTransactions(new ArrayList<>());
        wallet4.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet4.setUsers(users2);

        Users users3 = new Users();
        users3.setBVN("BVN");
        users3.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users3.setEmail("jane.doe@example.org");
        users3.setFirstName("Jane");
        users3.setId(123L);
        users3.setLastName("Doe");
        users3.setPassword("iloveyou");
        users3.setPhoneNumber("4105551212");
        users3.setPin("Pin");
        users3.setRole("Role");
        users3.setToken("ABC123");
        users3.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users3.setUsername("janedoe");
        users3.setUsersStatus(UsersStatus.ACTIVE);
        users3.setWallet(wallet4);
        Wallet actualGenerateWalletResult = usersServiceImpl.generateWallet(users3);
        assertSame(wallet2, actualGenerateWalletResult);
        assertEquals("42", actualGenerateWalletResult.getBalance().toString());
        verify(walletService).createWallet((Users) any());
    }


    @Test
    void testGetUser() {
        when(jwtUtils.extractUsername((String) any())).thenReturn("janedoe");

        Users users = new Users();
        users.setBVN("BVN");
        users.setCreatedAt(null);
        users.setEmail("jane.doe@example.org");
        users.setFirstName("Jane");
        users.setId(123L);
        users.setLastName("Doe");
        users.setPassword("iloveyou");
        users.setPhoneNumber("4105551212");
        users.setPin("Pin");
        users.setRole("Role");
        users.setToken("ABC123");
        users.setUpdatedAt(null);
        users.setUsername("janedoe");
        users.setUsersStatus(UsersStatus.ACTIVE);
        users.setWallet(new Wallet());

        Wallet wallet = new Wallet();
        wallet.setAccountNumber("42");
        wallet.setBalance(BigDecimal.valueOf(42L));
        wallet.setBankName("Bank Name");
        wallet.setCreateAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet.setId(123L);
        wallet.setModifyAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet.setTransactions(new ArrayList<>());
        wallet.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet.setUsers(users);

        Users users1 = new Users();
        users1.setBVN("BVN");
        users1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users1.setEmail("jane.doe@example.org");
        users1.setFirstName("Jane");
        users1.setId(123L);
        users1.setLastName("Doe");
        users1.setPassword("iloveyou");
        users1.setPhoneNumber("4105551212");
        users1.setPin("Pin");
        users1.setRole("Role");
        users1.setToken("ABC123");
        users1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users1.setUsername("janedoe");
        users1.setUsersStatus(UsersStatus.ACTIVE);
        users1.setWallet(wallet);

        Wallet wallet1 = new Wallet();
        wallet1.setAccountNumber("42");
        wallet1.setBalance(BigDecimal.valueOf(42L));
        wallet1.setBankName("Bank Name");
        wallet1.setCreateAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet1.setId(123L);
        wallet1.setModifyAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet1.setTransactions(new ArrayList<>());
        wallet1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet1.setUsers(users1);

        Users users2 = new Users();
        users2.setBVN("BVN");
        users2.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users2.setEmail("jane.doe@example.org");
        users2.setFirstName("Jane");
        users2.setId(123L);
        users2.setLastName("Doe");
        users2.setPassword("iloveyou");
        users2.setPhoneNumber("4105551212");
        users2.setPin("Pin");
        users2.setRole("Role");
        users2.setToken("ABC123");
        users2.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users2.setUsername("janedoe");
        users2.setUsersStatus(UsersStatus.ACTIVE);
        users2.setWallet(wallet1);
        when(usersRepository.findUsersByEmail((String) any())).thenReturn(users2);
        UsersResponse actualUser = usersServiceImpl.getUser();
        assertEquals("BVN", actualUser.getBVN());
        assertEquals("4105551212", actualUser.getPhoneNumber());
        assertEquals("Doe", actualUser.getLastName());
        assertEquals("Jane", actualUser.getFirstName());
        assertEquals("jane.doe@example.org", actualUser.getEmail());
        verify(jwtUtils).extractUsername((String) any());
        verify(usersRepository).findUsersByEmail((String) any());
    }


    @Test
    void testGetUser2() {
        when(jwtUtils.extractUsername((String) any())).thenReturn("janedoe");
        when(usersRepository.findUsersByEmail((String) any()))
                .thenThrow(new PasswordNotMatchingException("An error occurred"));
        assertThrows(PasswordNotMatchingException.class, () -> usersServiceImpl.getUser());
        verify(jwtUtils).extractUsername((String) any());
        verify(usersRepository).findUsersByEmail((String) any());
    }


    @Test
    void testEnableUser() {
        Wallet wallet = new Wallet();
        wallet.setAccountNumber("42");
        wallet.setBalance(null);
        wallet.setBankName("Bank Name");
        wallet.setCreateAt(null);
        wallet.setCreatedAt(null);
        wallet.setId(123L);
        wallet.setModifyAt(null);
        wallet.setTransactions(new ArrayList<>());
        wallet.setUpdatedAt(null);
        wallet.setUsers(new Users());

        Users users = new Users();
        users.setBVN("BVN");
        users.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users.setEmail("jane.doe@example.org");
        users.setFirstName("Jane");
        users.setId(123L);
        users.setLastName("Doe");
        users.setPassword("iloveyou");
        users.setPhoneNumber("4105551212");
        users.setPin("Pin");
        users.setRole("Role");
        users.setToken("ABC123");
        users.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users.setUsername("janedoe");
        users.setUsersStatus(UsersStatus.ACTIVE);
        users.setWallet(wallet);

        Optional<Users> ofResult = Optional.of(users);

        when(usersRepository.save((Users) any())).thenReturn(users);
        when(usersRepository.findByEmail((String) any())).thenReturn(ofResult);
        usersServiceImpl.enableUser("jane.doe@example.org");
        verify(usersRepository).save((Users) any());
        verify(usersRepository).findByEmail((String) any());
    }


}

