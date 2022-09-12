package com.naekang.account.controller;

import com.naekang.account.domain.Account;
import com.naekang.account.dto.CreatedAccountDTO;
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
     * @URI : /account
     * @Param: userId, initialBalance
     */
    @PostMapping("/account")
    public CreatedAccountDTO.Response createAccount(
            @RequestBody @Valid CreatedAccountDTO.Request request
    ) {
        accountService.createAccount(request.getUserId(), request.getInitialBalance());
    }

    @GetMapping("/account/{id}")
    public Account getAccount(
            @PathVariable Long id){
        return accountService.getAccount(id);
    }
}
