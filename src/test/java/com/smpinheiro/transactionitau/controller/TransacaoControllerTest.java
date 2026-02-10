package com.smpinheiro.transactionitau.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.smpinheiro.transactionitau.controllers.TransacaoController;
import com.smpinheiro.transactionitau.exceptions.UnprocessableEntity;
import com.smpinheiro.transactionitau.objects.TransacaoRequestDTO;
import com.smpinheiro.transactionitau.services.TransacaoService;

@ExtendWith(MockitoExtension.class)
public class TransacaoControllerTest {

	@InjectMocks
    TransacaoController transacaoController;

    @Mock
    TransacaoService transacaoService;

    TransacaoRequestDTO transacao;

    MockMvc mockmvc;
    
    @Autowired
    final ObjectMapper objectMapper = new ObjectMapper();
    
    @BeforeEach
    void setup(){
        objectMapper.registerModule(new JavaTimeModule());
        mockmvc = MockMvcBuilders.standaloneSetup(transacaoController).build();
        transacao = new TransacaoRequestDTO(20.0, OffsetDateTime.of(2026, 2, 9, 11, 30, 0, 0, ZoneOffset.UTC));
    }
    
    @Test
    void deveAdicionarTransacaoComSucesso() throws Exception {

        doNothing().when(transacaoService).addTransacoes(transacao);

        RequestBuilder request = MockMvcRequestBuilders
        							.post("/transacao")
									.contentType(MediaType.APPLICATION_JSON)
									.content(objectMapper.writeValueAsString(transacao) );
        mockmvc.perform(request).andExpect(status().isCreated() );
    }

    @Test
    void deveGerarExcecaoAoAdicionarTransacao() throws Exception {
        doThrow(new UnprocessableEntity("Erro de requisição")).when(transacaoService).addTransacoes(transacao);
        
        RequestBuilder request = MockMvcRequestBuilders
    								.post("/transacao")
    								.content(objectMapper.writeValueAsString(transacao) );    								
        mockmvc.perform(request).andExpect(status().is4xxClientError());
    }
    
    @Test
    void deveDeletarTransacoesComSucesso() throws Exception {
        doNothing().when(transacaoService).clearAllTransacoes();
        
        RequestBuilder request = MockMvcRequestBuilders.delete("/transacao");
        
        mockmvc.perform(request).andExpect(status().isOk());
    }
    
    
}
