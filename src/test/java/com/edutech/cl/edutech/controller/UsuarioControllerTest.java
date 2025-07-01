package com.edutech.cl.edutech.controller;

import com.edutech.cl.edutech.model.Usuario;
import com.edutech.cl.edutech.service.UsuarioService;
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

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario(1, "Ana Torres", "ana@correo.com", "1234");
    }

    @Test
    void testListarUsuarios() throws Exception {
        when(usuarioService.findAll()).thenReturn(List.of(usuario));

        mockMvc.perform(get("/api/v1/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Ana Torres"));
    }

    @Test
    void testBuscarUsuarioPorId() throws Exception {
        when(usuarioService.findById(1)).thenReturn(usuario);

        mockMvc.perform(get("/api/v1/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("ana@correo.com"));
    }

    @Test
    void testGuardarUsuario() throws Exception {
        when(usuarioService.save(any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(post("/api/v1/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Ana Torres"));
    }

    @Test
    void testActualizarUsuario() throws Exception {
        when(usuarioService.findById(1)).thenReturn(usuario);
        when(usuarioService.save(any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(put("/api/v1/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("ana@correo.com"));
    }

    @Test
    void testEliminarUsuario() throws Exception {
        doNothing().when(usuarioService).delete(1);

        mockMvc.perform(delete("/api/v1/usuarios/1"))
                .andExpect(status().isNoContent());

        verify(usuarioService, times(1)).delete(1);
    }
}
