package com.naekang.account.dto;

import com.naekang.account.type.TransactionResultType;
import com.naekang.account.type.TransactionType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QueryTransactionResponseDto {
    private String accountNumber;
    private TransactionType transactionType;
    private TransactionResultType transactionResultType;
    private String transactionId;
    private Long amount;
    private LocalDateTime transactedAt;

    public static QueryTransactionResponseDto from(TransactionDto transactionDto) {
        return QueryTransactionResponseDto.builder()
                .accountNumber(transactionDto.getAccountNumber())
                .transactionType(transactionDto.getTransactionType())
                .transactionResultType(transactionDto.getTransactionResultType())
                .transactionId(transactionDto.getTransactionId())
                .amount(transactionDto.getAmount())
                .transactedAt(transactionDto.getTransactedAt())
                .build();
    }
}
