package com.naekang.account.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.naekang.account.dto.TransactionDto;
import com.naekang.account.dto.UseBalanceDto;
import com.naekang.account.service.TransactionService;
import com.naekang.account.type.TransactionResultType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @MockBean
    private TransactionService transactionService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("계좌 사용 테스트")
    void successUseBalance() throws Exception {
        //given
        given(transactionService.useBalance(anyLong(), anyString(), anyLong()))
                .willReturn(TransactionDto.builder()
                        .accountNumber("1000000000")
                        .transactedAt(LocalDateTime.now())
                        .amount(12345L)
                        .transactionId("transactionId")
                        .transactionResultType(TransactionResultType.S)
                        .build());

        // when
        // then
        mockMvc.perform(post("/transaction/use")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new UseBalanceDto.Request(1L, "2000000000", 3000L)
                        )))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value("1000000000"))
                .andExpect(jsonPath("$.transactionResultType").value("S"))
                .andExpect(jsonPath("$.transactionId").value("transactionId"))
                .andExpect(jsonPath("$.amount").value(12345))
                .andDo(print());

    }

}