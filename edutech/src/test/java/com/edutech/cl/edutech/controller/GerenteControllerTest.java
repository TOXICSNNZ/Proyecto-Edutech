package com.edutech.cl.edutech.controller;

import com.edutech.cl.edutech.model.Gerente;
import com.edutech.cl.edutech.service.GerenteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GerenteController.class)
public class GerenteControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockitoBean
    private GerenteService gerenteService;

    @Autowired private ObjectMapper objectMapper;

    private Gerente gerente;

    @BeforeEach
    void setUp() {
        gerente = new Gerente(1, "Maria López", "maria@edu.cl", "admin123");
    }

    @Test
    void testListarGerentes() throws Exception {
        when(gerenteService.findAll()).thenReturn(List.of(gerente));

        mockMvc.perform(get("/api/v1/gerentes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Maria López"));
    }

    @Test
    void testBuscarGerentePorId() throws Exception {
        when(gerenteService.findById(1)).thenReturn(gerente);

        mockMvc.perform(get("/api/v1/gerentes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("maria@edu.cl"));
    }

    @Test
    void testGuardarGerente() throws Exception {
        when(gerenteService.save(any(Gerente.class))).thenReturn(gerente);

        mockMvc.perform(post("/api/v1/gerentes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gerente)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Maria López"));
    }

    @Test
    void testActualizarGerente() throws Exception {
        when(gerenteService.findById(1)).thenReturn(gerente);
        when(gerenteService.save(any(Gerente.class))).thenReturn(gerente);

        mockMvc.perform(put("/api/v1/gerentes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gerente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contrasena").value("admin123"));
    }

    @Test
    void testEliminarGerente() throws Exception {
        doNothing().when(gerenteService).delete(1);

        mockMvc.perform(delete("/api/v1/gerentes/1"))
                .andExpect(status().isNoContent());

        verify(gerenteService, times(1)).delete(1);
    }
}
