package com.smpinheiro.transactionitau.services;

import java.util.DoubleSummaryStatistics;
import java.util.List;

import org.springframework.stereotype.Service;

import com.smpinheiro.transactionitau.objects.EstatisticasResponseDTO;
import com.smpinheiro.transactionitau.objects.TransacaoRequestDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EstatisticasService {

	public final TransacaoService transacaoService;
	
	public EstatisticasResponseDTO getEstatisticasTransacoes(Integer intervalo) {
		
		log.info("Iniciando busca de estatísticas das transações existentes para o intervalo de " + intervalo + " segundos");
		
		List<TransacaoRequestDTO> transacoes = transacaoService.getReportTransacoes(intervalo);
		
		if(transacoes.isEmpty()) {
			log.info("A lista de transações está vazia");
			return new EstatisticasResponseDTO(0L, 0.0, 0.0, 0.0, 0.0);
		}
		
		DoubleSummaryStatistics estatisticas = transacoes.stream().mapToDouble(TransacaoRequestDTO::value).summaryStatistics();
		
		EstatisticasResponseDTO response = new EstatisticasResponseDTO( estatisticas.getCount(), estatisticas.getSum(),
				estatisticas.getAverage(), estatisticas.getMin(),estatisticas.getMax());
		
		log.info("E statísticas obtidas com sucesso");
		return response;
	}
}
