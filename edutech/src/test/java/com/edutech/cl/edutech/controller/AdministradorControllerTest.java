package com.edutech.cl.edutech.controller;

import com.edutech.cl.edutech.model.Administrador;
import com.edutech.cl.edutech.service.AdministradorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdministradorController.class)
public class AdministradorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AdministradorService administradorService;

    @Autowired
    private ObjectMapper objectMapper;

    private Administrador admin;

    @BeforeEach
    void setUp() {
        admin = new Administrador(1, "admin@correo.com", "12345");
    }

    @Test
    void testListarAdministradores() throws Exception {
        when(administradorService.findAll()).thenReturn(List.of(admin));

        mockMvc.perform(get("/api/v1/administradores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("admin@correo.com"));
    }

    @Test
    void testBuscarPorId() throws Exception {
        when(administradorService.findById(1)).thenReturn(admin);

        mockMvc.perform(get("/api/v1/administradores/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("admin@correo.com"));
    }

    @Test
    void testGuardarAdministrador() throws Exception {
        when(administradorService.save(any(Administrador.class))).thenReturn(admin);

        mockMvc.perform(post("/api/v1/administradores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(admin)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("admin@correo.com"));
    }

    @Test
    void testActualizarAdministrador() throws Exception {
        when(administradorService.findById(1)).thenReturn(admin);
        when(administradorService.save(any())).thenReturn(admin);

        mockMvc.perform(put("/api/v1/administradores/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(admin)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("admin@correo.com"));
    }

    @Test
    void testEliminarAdministrador() throws Exception {
        doNothing().when(administradorService).delete(1);

        mockMvc.perform(delete("/api/v1/administradores/1"))
                .andExpect(status().isNoContent());
    }
}
