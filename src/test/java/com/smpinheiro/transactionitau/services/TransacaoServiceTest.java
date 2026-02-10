package com.smpinheiro.transactionitau.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.OffsetDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.smpinheiro.transactionitau.exceptions.UnprocessableEntity;
import com.smpinheiro.transactionitau.objects.TransacaoRequestDTO;

@ExtendWith(MockitoExtension.class)
public class TransacaoServiceTest {

	@InjectMocks
	TransacaoService transacaoService;
	
	TransacaoRequestDTO transacao;
	
	@BeforeEach
	void setUp() {
		transacao = new TransacaoRequestDTO(20.0, OffsetDateTime.now() );
	}
	
	@Test
	void deveAdicionarTransacaoComSucesso() {
		transacaoService.addTransacoes(transacao);
		
		List<TransacaoRequestDTO> transacoes = transacaoService.getReportTransacoes(5000);
		
		assertTrue(transacoes.contains(transacao) );
	}
	
	@Test
	void deveLancarExcecaoCasoValorSejaNegativo() {
		UnprocessableEntity exception = assertThrows(UnprocessableEntity.class,
				() -> transacaoService.addTransacoes(new TransacaoRequestDTO(-10.0, OffsetDateTime.now())) );
		
		assertEquals("Valor não pode ser menor que R$0", exception.getMessage());
	}
	
	@Test
	void deveLancarExcecaoCasoDataOuHoraMaiorQueAtual() {
		UnprocessableEntity exception = assertThrows(UnprocessableEntity.class,
				() -> transacaoService.addTransacoes(new TransacaoRequestDTO(10.0, OffsetDateTime.now().plusHours(1))) );
		
		assertEquals("Data e hora da transação maior que, data e hora atual", exception.getMessage() );
	}
	
	@Test
	void deveLimparTransacoesComSucesso() {
		transacaoService.addTransacoes(transacao);
		
		transacaoService.clearAllTransacoes();
		
		List<TransacaoRequestDTO> transacoes = transacaoService.getReportTransacoes(5000);
		
		assertTrue(transacoes.isEmpty() );
	}
	
	@Test
	void deveBuscarTransacaoEmIntervalo() {
		TransacaoRequestDTO dto = new TransacaoRequestDTO(10.0, OffsetDateTime.now().minusHours(1) );
		
		transacaoService.addTransacoes(transacao);
		transacaoService.addTransacoes(dto);
		
		List<TransacaoRequestDTO> transacoes = transacaoService.getReportTransacoes(60);
		
		assertTrue(transacoes.contains(transacao));
		assertFalse(transacoes.contains(dto));
	}
	
}