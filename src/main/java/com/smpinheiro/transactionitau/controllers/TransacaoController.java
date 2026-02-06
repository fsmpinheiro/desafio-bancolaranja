package com.smpinheiro.transactionitau.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smpinheiro.transactionitau.objects.TransacaoRequestDTO;
import com.smpinheiro.transactionitau.services.TransacaoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/transacao")
public class TransacaoController {
	
	private final TransacaoService transacaoService;

	@PostMapping
	@Operation(description = "Endpoint encarregado da tarefa de adicionar transações")
	
	@ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transação gravada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Campos não atendem os requisitos da transação"),
            @ApiResponse(responseCode = "400", description = "Erro de requisição"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor") } )
	public ResponseEntity<Void> addTransacao(@RequestBody TransacaoRequestDTO dto) {
		
		transacaoService.addTransacoes(dto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	
	@DeleteMapping
	@Operation(description = "Endpoint responsável por deletar todas as transações")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transação deletadas com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de requisição"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor") } )
	public ResponseEntity<Void> clearTransacoes() {
		
		transacaoService.clearAllTransacoes();
		return ResponseEntity.ok().build();
	}
}
