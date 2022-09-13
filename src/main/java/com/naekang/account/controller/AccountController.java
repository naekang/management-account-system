package com.naekang.account.controller;

import com.naekang.account.domain.Account;
import com.naekang.account.dto.CreateAccountDto;
import com.naekang.account.dto.DeleteAccountDto;
import com.naekang.account.service.AccountService;
import com.naekang.account.service.RedisTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final RedisTestService redisTestService;

    @GetMapping("/get-lock")
    public String getLock() {
        return redisTestService.getLock();
    }

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

    @GetMapping("/account/{id}")
    public Account getAccount(
            @PathVariable Long id) {
        return accountService.getAccount(id);
    }
}
