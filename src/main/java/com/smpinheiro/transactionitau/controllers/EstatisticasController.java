package com.smpinheiro.transactionitau.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smpinheiro.transactionitau.objects.EstatisticasResponseDTO;
import com.smpinheiro.transactionitau.services.EstatisticasService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/estatistica")
public class EstatisticasController {

	private final EstatisticasService estatisticasService;
	
	@GetMapping
	@Operation(description = "Endpoint responsável pela buscar das estatísticas das transações")
	@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca efetuada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na busca de estatísticas de transações"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor") } )
	public ResponseEntity<EstatisticasResponseDTO> getEstatisticas(@RequestParam(value="intervaloBusca", required = false, defaultValue = "60") Integer intervaloBusca) {
				
		return ResponseEntity.ok(estatisticasService.getEstatisticasTransacoes(intervaloBusca) );
	}
}
