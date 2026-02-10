package com.smpinheiro.transactionitau.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.MediaType;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.smpinheiro.transactionitau.controllers.EstatisticasController;
import com.smpinheiro.transactionitau.objects.EstatisticasResponseDTO;
import com.smpinheiro.transactionitau.services.EstatisticasService;

@ExtendWith(MockitoExtension.class)
public class EstatisticaControllerTest {
	
	@InjectMocks
	EstatisticasController estatisticasController;
	
	@Mock
	EstatisticasService estatisticasService;
	
	EstatisticasResponseDTO estatisticas;
	
	MockMvc mockmvc;
	
	@BeforeEach
	void setUp() {
		mockmvc = MockMvcBuilders.standaloneSetup(estatisticasController).build();
		estatisticas = new EstatisticasResponseDTO(1L, 20.0, 20.0, 20.0, 20.0);
	}
	
	@Test
	void deveBuscarEstatisticasComSucesso() throws Exception {

        when(estatisticasService.getEstatisticasTransacoes(60)).thenReturn(estatisticas);

        mockmvc.perform(get("/estatistica")
                .param("intervaloBusca", "60")
                .accept(MediaType.APPLICATION_JSON.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON.toString()))
                .andExpect(jsonPath("$.count").value(estatisticas.count()));

    }
}
