package com.smpinheiro.transactionitau.services;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.smpinheiro.transactionitau.exceptions.UnprocessableEntity;
import com.smpinheiro.transactionitau.objects.TransacaoRequestDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransacaoService {

	private final List<TransacaoRequestDTO> listOfTransacoes = new ArrayList<>();
	
	public void addTransacoes(TransacaoRequestDTO dto) {
		
		log.info("Iniciado o processamento das transaçãos");
		
		if(dto.dataHora().isAfter(OffsetDateTime.now()) ) {
			log.error("Data e hora maiores que a data e hora atual");
			throw new UnprocessableEntity("Data e hora da transação maior que, data e hora atual");
		}
		
		if(dto.value() < 0) {
			log.error("Valor não pode ser menor que zero");
			throw new UnprocessableEntity("Valor não pode ser menor que R$0");
		}
		
		listOfTransacoes.add(dto);
	}
	
	public void clearAllTransacoes() {
		
		if(listOfTransacoes.isEmpty()) {
			log.error("Não há transações, não o que limpar");
			throw new UnprocessableEntity("Não há transações a eliminar");
		}
		
		listOfTransacoes.clear();
	}
	
	public List<TransacaoRequestDTO> getReportTransacoes(Integer intervalo) {
		
		OffsetDateTime intervaloOffset = OffsetDateTime.now().minusSeconds(intervalo);
		
		List<TransacaoRequestDTO> listReturn = listOfTransacoes.stream()
					.filter(trs -> trs.dataHora().isAfter(intervaloOffset)).toList() ;
		
		return listReturn;
	}
}
