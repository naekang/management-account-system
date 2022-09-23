package com.naekang.account.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class DeleteAccountDto {

    @Getter
    @AllArgsConstructor
    public static class Request {
        @NotNull
        @Min(1)
        private Long userId;

        @NotBlank
        @Size(min = 10, max = 10)
        private String accountNumber;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long userId;
        private String accountNumber;
        private LocalDateTime unregisteredAt;

        public static Response from(AccountDto accountDto) {
            return DeleteAccountDto.Response.builder()
                    .userId(accountDto.getUserId())
                    .accountNumber(accountDto.getAccountNumber())
                    .unregisteredAt(accountDto.getUnregisteredAt())
                    .build();
        }

    }
}
