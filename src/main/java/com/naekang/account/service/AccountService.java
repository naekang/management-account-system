package com.naekang.account.service;

import com.naekang.account.domain.Account;
import com.naekang.account.domain.AccountStatus;
import com.naekang.account.dto.CreatedAccountDTO;
import com.naekang.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public void createAccount(Long userId, Long initialBalance) {

    }

    @Transactional
    public Account getAccount(Long id) {
        if(id < 0){
            throw new RuntimeException("Minus");
        }
        return accountRepository.findById(id).get();
    }

}
