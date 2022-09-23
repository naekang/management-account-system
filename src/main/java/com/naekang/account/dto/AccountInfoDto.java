package com.naekang.account.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountInfoDto {

    private String accountNumber;

    private Long balance;

}
