package com.decagon.fintechpaymentapisqd11b.service.serviceImpl;

import com.decagon.fintechpaymentapisqd11b.dto.WalletDto;
import com.decagon.fintechpaymentapisqd11b.entities.Transaction;
import com.decagon.fintechpaymentapisqd11b.entities.Users;
import com.decagon.fintechpaymentapisqd11b.entities.Wallet;
import com.decagon.fintechpaymentapisqd11b.enums.UsersStatus;
import com.decagon.fintechpaymentapisqd11b.repository.UsersRepository;
import com.decagon.fintechpaymentapisqd11b.repository.WalletRepository;
import com.decagon.fintechpaymentapisqd11b.security.filter.JwtUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {WalletServiceImpl.class})
@ExtendWith(SpringExtension.class)
class WalletServiceImplTest {

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private UsersRepository usersRepository;

    @MockBean
    private WalletRepository walletRepository;

    @Autowired
    private WalletServiceImpl walletServiceImpl;

    /**
     * Method under test: {@link WalletServiceImpl#viewWalletDetails()}
     */
    @Test
    void testViewWalletDetails() {
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
        when(walletRepository.findWalletByUsers((Users) any())).thenReturn(wallet2);

        Users users2 = new Users();
        users2.setBVN("BVN");
        users2.setCreatedAt(null);
        users2.setEmail("jane.doe@example.org");
        users2.setFirstName("Jane");
        users2.setId(123L);
        users2.setLastName("Doe");
        users2.setPassword("iloveyou");
        users2.setPhoneNumber("4105551212");
        users2.setPin("Pin");
        users2.setRole("Role");
        users2.setToken("ABC123");
        users2.setUpdatedAt(null);
        users2.setUsername("janedoe");
        users2.setUsersStatus(UsersStatus.ACTIVE);
        users2.setWallet(new Wallet());

        Wallet wallet3 = new Wallet();
        wallet3.setAccountNumber("42");
        wallet3.setBalance(BigDecimal.valueOf(42L));
        wallet3.setBankName("Bank Name");
        wallet3.setCreateAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet3.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet3.setId(123L);
        wallet3.setModifyAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet3.setTransactions(new ArrayList<>());
        wallet3.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet3.setUsers(users2);

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
        users3.setWallet(wallet3);

        Wallet wallet4 = new Wallet();
        wallet4.setAccountNumber("42");
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        wallet4.setBalance(valueOfResult);
        wallet4.setBankName("Bank Name");
        wallet4.setCreateAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet4.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet4.setId(123L);
        wallet4.setModifyAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet4.setTransactions(new ArrayList<>());
        wallet4.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet4.setUsers(users3);

        Users users4 = new Users();
        users4.setBVN("BVN");
        users4.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users4.setEmail("jane.doe@example.org");
        users4.setFirstName("Jane");
        users4.setId(123L);
        users4.setLastName("Doe");
        users4.setPassword("iloveyou");
        users4.setPhoneNumber("4105551212");
        users4.setPin("Pin");
        users4.setRole("Role");
        users4.setToken("ABC123");
        users4.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users4.setUsername("janedoe");
        users4.setUsersStatus(UsersStatus.ACTIVE);
        users4.setWallet(wallet4);
        when(usersRepository.findUsersByEmail((String) any())).thenReturn(users4);
        WalletDto actualViewWalletDetailsResult = walletServiceImpl.viewWalletDetails();
        assertEquals("42", actualViewWalletDetailsResult.getAccountNumber());
        assertEquals("Bank Name", actualViewWalletDetailsResult.getBankName());
        BigDecimal balance = actualViewWalletDetailsResult.getBalance();
        assertEquals(valueOfResult, balance);
        assertEquals("42", balance.toString());
        verify(walletRepository).findWalletByUsers((Users) any());
        verify(usersRepository).findUsersByEmail((String) any());
    }

    /**
     * Method under test: {@link WalletServiceImpl#viewWalletDetails()}
     */
    @Test
    void testViewWalletDetails2() {
        when(walletRepository.findWalletByUsers((Users) any())).thenThrow(new UsernameNotFoundException("Wema Bank"));

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
        assertThrows(UsernameNotFoundException.class, () -> walletServiceImpl.viewWalletDetails());
        verify(walletRepository).findWalletByUsers((Users) any());
        verify(usersRepository).findUsersByEmail((String) any());
    }

    /**
     * Method under test: {@link WalletServiceImpl#viewWalletDetails()}
     */
    @Test
    void testViewWalletDetails3() {
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
        Wallet wallet2 = mock(Wallet.class);
        when(wallet2.getAccountNumber()).thenReturn("42");
        when(wallet2.getBankName()).thenReturn("Bank Name");
        when(wallet2.getBalance()).thenReturn(BigDecimal.valueOf(42L));
        doNothing().when(wallet2).setCreatedAt((LocalDateTime) any());
        doNothing().when(wallet2).setId((Long) any());
        doNothing().when(wallet2).setUpdatedAt((LocalDateTime) any());
        doNothing().when(wallet2).setAccountNumber((String) any());
        doNothing().when(wallet2).setBalance((BigDecimal) any());
        doNothing().when(wallet2).setBankName((String) any());
        doNothing().when(wallet2).setCreateAt((LocalDateTime) any());
        doNothing().when(wallet2).setModifyAt((LocalDateTime) any());
        doNothing().when(wallet2).setTransactions((List<Transaction>) any());
        doNothing().when(wallet2).setUsers((Users) any());
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
        when(walletRepository.findWalletByUsers((Users) any())).thenReturn(wallet2);

        Users users2 = new Users();
        users2.setBVN("BVN");
        users2.setCreatedAt(null);
        users2.setEmail("jane.doe@example.org");
        users2.setFirstName("Jane");
        users2.setId(123L);
        users2.setLastName("Doe");
        users2.setPassword("iloveyou");
        users2.setPhoneNumber("4105551212");
        users2.setPin("Pin");
        users2.setRole("Role");
        users2.setToken("ABC123");
        users2.setUpdatedAt(null);
        users2.setUsername("janedoe");
        users2.setUsersStatus(UsersStatus.ACTIVE);
        users2.setWallet(new Wallet());

        Wallet wallet3 = new Wallet();
        wallet3.setAccountNumber("42");
        wallet3.setBalance(BigDecimal.valueOf(42L));
        wallet3.setBankName("Bank Name");
        wallet3.setCreateAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet3.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet3.setId(123L);
        wallet3.setModifyAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet3.setTransactions(new ArrayList<>());
        wallet3.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet3.setUsers(users2);

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
        users3.setWallet(wallet3);

        Wallet wallet4 = new Wallet();
        wallet4.setAccountNumber("42");
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        wallet4.setBalance(valueOfResult);
        wallet4.setBankName("Bank Name");
        wallet4.setCreateAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet4.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet4.setId(123L);
        wallet4.setModifyAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet4.setTransactions(new ArrayList<>());
        wallet4.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet4.setUsers(users3);

        Users users4 = new Users();
        users4.setBVN("BVN");
        users4.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users4.setEmail("jane.doe@example.org");
        users4.setFirstName("Jane");
        users4.setId(123L);
        users4.setLastName("Doe");
        users4.setPassword("iloveyou");
        users4.setPhoneNumber("4105551212");
        users4.setPin("Pin");
        users4.setRole("Role");
        users4.setToken("ABC123");
        users4.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users4.setUsername("janedoe");
        users4.setUsersStatus(UsersStatus.ACTIVE);
        users4.setWallet(wallet4);
        when(usersRepository.findUsersByEmail((String) any())).thenReturn(users4);
        WalletDto actualViewWalletDetailsResult = walletServiceImpl.viewWalletDetails();
        assertEquals("42", actualViewWalletDetailsResult.getAccountNumber());
        assertEquals("Bank Name", actualViewWalletDetailsResult.getBankName());
        BigDecimal balance = actualViewWalletDetailsResult.getBalance();
        assertEquals(valueOfResult, balance);
        assertEquals("42", balance.toString());
        verify(walletRepository).findWalletByUsers((Users) any());
        verify(wallet2).getAccountNumber();
        verify(wallet2).getBankName();
        verify(wallet2).getBalance();
        verify(wallet2).setCreatedAt((LocalDateTime) any());
        verify(wallet2).setId((Long) any());
        verify(wallet2).setUpdatedAt((LocalDateTime) any());
        verify(wallet2).setAccountNumber((String) any());
        verify(wallet2).setBalance((BigDecimal) any());
        verify(wallet2).setBankName((String) any());
        verify(wallet2).setCreateAt((LocalDateTime) any());
        verify(wallet2).setModifyAt((LocalDateTime) any());
        verify(wallet2).setTransactions((List<Transaction>) any());
        verify(wallet2).setUsers((Users) any());
        verify(usersRepository).findUsersByEmail((String) any());
    }


}