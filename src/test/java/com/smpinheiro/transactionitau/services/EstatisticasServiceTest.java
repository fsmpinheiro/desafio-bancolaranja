package com.smpinheiro.transactionitau.services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.smpinheiro.transactionitau.objects.EstatisticasResponseDTO;
import com.smpinheiro.transactionitau.objects.TransacaoRequestDTO;

@ExtendWith(MockitoExtension.class)
public class EstatisticasServiceTest {

	@InjectMocks
	EstatisticasService estatisticasService;
	
	@Mock
	TransacaoService transacaoService;
	
	List<TransacaoRequestDTO> transacoes = new ArrayList<>() ;
	
	EstatisticasResponseDTO estatistica;
	
	@BeforeEach
	void setUp() {
		transacoes.add(new TransacaoRequestDTO(10.0, OffsetDateTime.now()) );
		transacoes.add(new TransacaoRequestDTO(20.0, OffsetDateTime.now().plusMinutes(2)) );
		estatistica = new EstatisticasResponseDTO(2L, 30.0, 15.0, 10.0, 20.0);
	}
	
	@Test
	void calcularEstatisticasComSucesso() {
		when(transacaoService.getReportTransacoes(60)).thenReturn(transacoes);
		
		EstatisticasResponseDTO resultado = estatisticasService.getEstatisticasTransacoes(60);
		
		verify(transacaoService, times(1)).getReportTransacoes(60);
		Assertions.assertThat(resultado).usingRecursiveComparison().isEqualTo(estatistica);
	}
	
	@Test
	void calcularEstatisticaQuandoListaVazia() {
		EstatisticasResponseDTO resultadoEsperado = new EstatisticasResponseDTO(0L, 0.0, 0.0, 0.0, 0.0);
		
		when(transacaoService.getReportTransacoes(60))
			.thenReturn(Collections.emptyList());
		
		EstatisticasResponseDTO resultadoObtido = estatisticasService.getEstatisticasTransacoes(60);
		
		verify(transacaoService, times(1)).getReportTransacoes(60);
		Assertions.assertThat(resultadoObtido).usingRecursiveComparison().isEqualTo(resultadoEsperado);
	}
	
}
