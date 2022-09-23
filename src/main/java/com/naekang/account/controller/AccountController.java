package com.naekang.account.controller;

import com.naekang.account.domain.Account;
import com.naekang.account.dto.AccountInfoDto;
import com.naekang.account.dto.CreateAccountDto;
import com.naekang.account.dto.DeleteAccountDto;
import com.naekang.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    /**
     * 계좌 생성 API
     */
    @PostMapping("/account")
    public CreateAccountDto.Response createAccount(
            @RequestBody @Valid CreateAccountDto.Request request
    ) {

        return CreateAccountDto.Response
                .from(accountService.createAccount(
                        request.getUserId(),
                        request.getInitialBalance()
                ));
    }

    @DeleteMapping("/account")
    public DeleteAccountDto.Response createAccount(
            @RequestBody @Valid DeleteAccountDto.Request request
    ) {

        return DeleteAccountDto.Response
                .from(accountService.deleteAccount(
                        request.getUserId(),
                        request.getAccountNumber()
                ));
    }

    @GetMapping("/account")
    public List<AccountInfoDto> getAccountByUserId(
            @RequestParam("user_id") Long userId
    ) {
        return accountService.getAccountByUserId(userId)
                .stream().map(accountDto -> AccountInfoDto.builder()
                        .accountNumber(accountDto.getAccountNumber())
                        .balance(accountDto.getBalance())
                        .build())
                .collect(Collectors.toList());
    }

    @GetMapping("/account/{id}")
    public Account getAccount(
            @PathVariable Long id) {
        return accountService.getAccount(id);
    }
}
