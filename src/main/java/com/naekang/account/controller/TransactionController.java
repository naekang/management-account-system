package com.naekang.account.controller;

import com.naekang.account.aop.AccountLock;
import com.naekang.account.dto.CancelBalanceDto;
import com.naekang.account.dto.QueryTransactionResponseDto;
import com.naekang.account.dto.UseBalanceDto;
import com.naekang.account.exception.AccountException;
import com.naekang.account.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 잔액 관련 컨트롤러
 * 1. 잔액 사용
 * 2. 잔액 사용 취소
 * 3. 거래 확인
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/transaction/use")
    @AccountLock
    public UseBalanceDto.Response useBalance(
            @Valid @RequestBody UseBalanceDto.Request request
    ) throws InterruptedException {
        try {
            Thread.sleep(3000L);
            return UseBalanceDto.Response.from(
                    transactionService.useBalance(request.getUserId(),
                            request.getAccountNumber(), request.getAmount()));
        } catch (AccountException e) {
            log.error("Failed to use Balance. ");

            transactionService.saveFailedUseTransaction(
                    request.getAccountNumber(),
                    request.getAmount()
            );

            throw e;
        }
    }

    @PostMapping("/transaction/cancel")
    @AccountLock
    public CancelBalanceDto.Response cancelBalance(
            @Valid @RequestBody CancelBalanceDto.Request request
    ) {
        try {
            return CancelBalanceDto.Response.from(
                    transactionService.cancelBalance(request.getTransactionId(),
                            request.getAccountNumber(), request.getAmount()));
        } catch (AccountException e) {
            log.error("Failed to use Balance. ");

            transactionService.saveFailedCancelTransaction(
                    request.getAccountNumber(),
                    request.getAmount()
            );

            throw e;
        }
    }

    @GetMapping("/transaction/{transactionId}")
    public QueryTransactionResponseDto queryTransaction(
            @PathVariable String transactionId
    ) {
        return QueryTransactionResponseDto.from(
                transactionService.queryTransaction(transactionId)
        );
    }

}
