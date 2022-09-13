package com.naekang.account.service;

import com.naekang.account.domain.Account;
import com.naekang.account.domain.AccountUser;
import com.naekang.account.dto.AccountDto;
import com.naekang.account.exception.AccountException;
import com.naekang.account.repository.AccountRepository;
import com.naekang.account.repository.AccountUserRepository;
import com.naekang.account.type.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountUserRepository accountUserRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    void createAccountSuccess() {
        //given
        AccountUser user = AccountUser.builder()
                .id(12L)
                .name("Pobi").build();

        given(accountUserRepository.findById(anyLong()))
                .willReturn(Optional.of(user));

        given(accountRepository.findFirstByOrderByIdDesc())
                .willReturn(Optional.of(Account.builder()
                        .accountNumber("1000000012").build()));

        given(accountRepository.save(any()))
                .willReturn(Account.builder()
                        .accountUser(user)
                        .accountNumber("1000000013").build());

        ArgumentCaptor<Account> captor = ArgumentCaptor.forClass(Account.class);

        //when
        AccountDto accountDto = accountService.createAccount(1L, 1000L);

        //then
        verify(accountRepository, times(1)).save(captor.capture());
        assertEquals(12L, accountDto.getUserId());
        assertEquals("1000000013", captor.getValue().getAccountNumber());

    }

    @Test
    void createFirstAccount() {
        //given
        AccountUser user = AccountUser.builder()
                .id(15L)
                .name("Pobi").build();

        given(accountUserRepository.findById(anyLong()))
                .willReturn(Optional.of(user));

        given(accountRepository.findFirstByOrderByIdDesc())
                .willReturn(Optional.empty());

        given(accountRepository.save(any()))
                .willReturn(Account.builder()
                        .accountUser(user)
                        .accountNumber("1000000013").build());

        ArgumentCaptor<Account> captor = ArgumentCaptor.forClass(Account.class);

        //when
        AccountDto accountDto = accountService.createAccount(1L, 1000L);

        //then
        verify(accountRepository, times(1)).save(captor.capture());
        assertEquals(15L, accountDto.getUserId());
        assertEquals("1000000000", captor.getValue().getAccountNumber());
    }

    @Test
    @DisplayName("해당 유저 없음 - 계좌 생성 실패")
    void createAccount_UserNotFound() {
        //given
        given(accountUserRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        //when
        AccountException exception = assertThrows(AccountException.class, () ->
                accountService.createAccount(1L, 1000L));

        //then
        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    @DisplayName("유저당 최대 계좌는 10개 - 계좌 생성 실패")
    void creatAccount_maxAccountIs10() {
        //given
        given(accountUserRepository.findById(anyLong()))
                .willReturn(Optional.of(AccountUser.builder()
                        .id(15L)
                        .name("Pobi").build()));

        given(accountRepository.countByAccountUser(any()))
                .willReturn(10);

        //when
        AccountException exception = assertThrows(AccountException.class, () ->
                accountService.createAccount(1L, 1000L));

        //then
        assertEquals(ErrorCode.MAX_ACCOUNT_PER_USER_10, exception.getErrorCode());
     }

}