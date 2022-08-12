package com.decagon.fintechpaymentapisqd11b.service.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.decagon.fintechpaymentapisqd11b.customExceptions.EmailTakenException;
import com.decagon.fintechpaymentapisqd11b.customExceptions.UserNotFoundException;
import com.decagon.fintechpaymentapisqd11b.dto.UsersDTO;
import com.decagon.fintechpaymentapisqd11b.entities.Users;
import com.decagon.fintechpaymentapisqd11b.entities.Wallet;
import com.decagon.fintechpaymentapisqd11b.enums.UsersStatus;
import com.decagon.fintechpaymentapisqd11b.repository.UsersRepository;
import com.decagon.fintechpaymentapisqd11b.repository.WalletRepository;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UsersServiceImpl.class})
@ExtendWith(SpringExtension.class)
class UsersServiceImplTest {
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

    /**
     * Method under test: {@link UsersServiceImpl#registerUser(UsersDTO)}
     */
    @Test
    void testRegisterUser() throws JSONException {
        doNothing().when(confirmationTokenServiceImpl).saveConfirmationToken((ConfirmationToken) any());

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

        Wallet wallet5 = new Wallet();
        wallet5.setAccountNumber("42");
        wallet5.setBalance(BigDecimal.valueOf(42L));
        wallet5.setBankName("Bank Name");
        wallet5.setCreateAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet5.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet5.setId(123L);
        wallet5.setModifyAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet5.setTransactions(new ArrayList<>());
        wallet5.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet5.setUsers(users3);
        when(walletRepository.save((Wallet) any())).thenReturn(wallet5);

        Users users4 = new Users();
        users4.setBVN("BVN");
        users4.setCreatedAt(null);
        users4.setEmail("jane.doe@example.org");
        users4.setFirstName("Jane");
        users4.setId(123L);
        users4.setLastName("Doe");
        users4.setPassword("iloveyou");
        users4.setPhoneNumber("4105551212");
        users4.setPin("Pin");
        users4.setRole("Role");
        users4.setToken("ABC123");
        users4.setUpdatedAt(null);
        users4.setUsername("janedoe");
        users4.setUsersStatus(UsersStatus.ACTIVE);
        users4.setWallet(new Wallet());

        Wallet wallet6 = new Wallet();
        wallet6.setAccountNumber("42");
        wallet6.setBalance(BigDecimal.valueOf(42L));
        wallet6.setBankName("Bank Name");
        wallet6.setCreateAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet6.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet6.setId(123L);
        wallet6.setModifyAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet6.setTransactions(new ArrayList<>());
        wallet6.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet6.setUsers(users4);

        Users users5 = new Users();
        users5.setBVN("BVN");
        users5.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users5.setEmail("jane.doe@example.org");
        users5.setFirstName("Jane");
        users5.setId(123L);
        users5.setLastName("Doe");
        users5.setPassword("iloveyou");
        users5.setPhoneNumber("4105551212");
        users5.setPin("Pin");
        users5.setRole("Role");
        users5.setToken("ABC123");
        users5.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users5.setUsername("janedoe");
        users5.setUsersStatus(UsersStatus.ACTIVE);
        users5.setWallet(wallet6);

        Wallet wallet7 = new Wallet();
        wallet7.setAccountNumber("42");
        wallet7.setBalance(BigDecimal.valueOf(42L));
        wallet7.setBankName("Bank Name");
        wallet7.setCreateAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet7.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet7.setId(123L);
        wallet7.setModifyAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet7.setTransactions(new ArrayList<>());
        wallet7.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet7.setUsers(users5);

        Users users6 = new Users();
        users6.setBVN("BVN");
        users6.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users6.setEmail("jane.doe@example.org");
        users6.setFirstName("Jane");
        users6.setId(123L);
        users6.setLastName("Doe");
        users6.setPassword("iloveyou");
        users6.setPhoneNumber("4105551212");
        users6.setPin("Pin");
        users6.setRole("Role");
        users6.setToken("ABC123");
        users6.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users6.setUsername("janedoe");
        users6.setUsersStatus(UsersStatus.ACTIVE);
        users6.setWallet(wallet7);

        Wallet wallet8 = new Wallet();
        wallet8.setAccountNumber("42");
        wallet8.setBalance(null);
        wallet8.setBankName("Bank Name");
        wallet8.setCreateAt(null);
        wallet8.setCreatedAt(null);
        wallet8.setId(123L);
        wallet8.setModifyAt(null);
        wallet8.setTransactions(new ArrayList<>());
        wallet8.setUpdatedAt(null);
        wallet8.setUsers(new Users());

        Users users7 = new Users();
        users7.setBVN("BVN");
        users7.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users7.setEmail("jane.doe@example.org");
        users7.setFirstName("Jane");
        users7.setId(123L);
        users7.setLastName("Doe");
        users7.setPassword("iloveyou");
        users7.setPhoneNumber("4105551212");
        users7.setPin("Pin");
        users7.setRole("Role");
        users7.setToken("ABC123");
        users7.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users7.setUsername("janedoe");
        users7.setUsersStatus(UsersStatus.ACTIVE);
        users7.setWallet(wallet8);

        Wallet wallet9 = new Wallet();
        wallet9.setAccountNumber("42");
        wallet9.setBalance(BigDecimal.valueOf(42L));
        wallet9.setBankName("Bank Name");
        wallet9.setCreateAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet9.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet9.setId(123L);
        wallet9.setModifyAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet9.setTransactions(new ArrayList<>());
        wallet9.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet9.setUsers(users7);

        Users users8 = new Users();
        users8.setBVN("BVN");
        users8.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users8.setEmail("jane.doe@example.org");
        users8.setFirstName("Jane");
        users8.setId(123L);
        users8.setLastName("Doe");
        users8.setPassword("iloveyou");
        users8.setPhoneNumber("4105551212");
        users8.setPin("Pin");
        users8.setRole("Role");
        users8.setToken("ABC123");
        users8.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users8.setUsername("janedoe");
        users8.setUsersStatus(UsersStatus.ACTIVE);
        users8.setWallet(wallet9);
        Optional<Users> ofResult = Optional.of(users8);
        when(usersRepository.save((Users) any())).thenReturn(users6);
        when(usersRepository.findByEmail((String) any())).thenReturn(ofResult);
        assertThrows(EmailTakenException.class, () -> usersServiceImpl.registerUser(new UsersDTO("Jane", "Doe", "janedoe",
                "BVN", "jane.doe@example.org", "4105551212", "iloveyou", "iloveyou", "Pin")));
        verify(usersRepository).findByEmail((String) any());
    }

    /**
     * Method under test: {@link UsersServiceImpl#registerUser(UsersDTO)}
     */
    @Test
    void testRegisterUser2() throws JSONException {
        doNothing().when(confirmationTokenServiceImpl).saveConfirmationToken((ConfirmationToken) any());

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

        Wallet wallet5 = new Wallet();
        wallet5.setAccountNumber("42");
        wallet5.setBalance(BigDecimal.valueOf(42L));
        wallet5.setBankName("Bank Name");
        wallet5.setCreateAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet5.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet5.setId(123L);
        wallet5.setModifyAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet5.setTransactions(new ArrayList<>());
        wallet5.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet5.setUsers(users3);
        when(walletRepository.save((Wallet) any())).thenReturn(wallet5);
        when(usersRepository.save((Users) any())).thenThrow(new UserNotFoundException("An error occurred"));
        when(usersRepository.findByEmail((String) any())).thenThrow(new UserNotFoundException("An error occurred"));
        assertThrows(UserNotFoundException.class, () -> usersServiceImpl.registerUser(new UsersDTO("Jane", "Doe", "janedoe",
                "BVN", "jane.doe@example.org", "4105551212", "iloveyou", "iloveyou", "Pin")));
        verify(usersRepository).findByEmail((String) any());
    }
}

