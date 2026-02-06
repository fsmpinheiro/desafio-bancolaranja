package com.smpinheiro.transactionitau.services;

import java.util.DoubleSummaryStatistics;
import java.util.List;

import org.springframework.stereotype.Service;

import com.smpinheiro.transactionitau.objects.EstatisticasResponseDTO;
import com.smpinheiro.transactionitau.objects.TransacaoRequestDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EstatisticasService {

	public final TransacaoService transacaoService;
	
	public EstatisticasResponseDTO getEstatisticasTransacoes(Integer intervalo) {
		
		List<TransacaoRequestDTO> transacoes = transacaoService.getReportTransacoes(intervalo);
		
		DoubleSummaryStatistics estatisticas = transacoes.stream().mapToDouble(TransacaoRequestDTO::value).summaryStatistics();
		
		EstatisticasResponseDTO response = new EstatisticasResponseDTO( estatisticas.getCount(), estatisticas.getSum(),
				estatisticas.getAverage(), estatisticas.getMin(),estatisticas.getMax());
		return response;
	}
}
