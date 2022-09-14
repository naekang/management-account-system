package com.naekang.account.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountInfoDto {

    private String accountNumber;

    private Long balance;

}
