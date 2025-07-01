package com.edutech.cl.edutech.controller;

import com.edutech.cl.edutech.model.Instructor;
import com.edutech.cl.edutech.service.InstructorService;
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

@WebMvcTest(InstructorController.class)
public class InstructorControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private InstructorService instructorService;
    @Autowired
    private ObjectMapper objectMapper;

    private Instructor instructor;

    @BeforeEach
    void setUp() {
        instructor = new Instructor(1, "Luis Vidal", "lvidal@edu.cl", "1234", "Matemáticas", 2);
    }

    @Test
    void testListar() throws Exception {
        when(instructorService.findAll()).thenReturn(List.of(instructor));
        mockMvc.perform(get("/api/v1/instructores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].curso").value("Matemáticas"));
    }

    @Test
    void testBuscar() throws Exception {
        when(instructorService.findById(1)).thenReturn(instructor);
        mockMvc.perform(get("/api/v1/instructores/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Luis Vidal"));
    }

    @Test
    void testGuardar() throws Exception {
        when(instructorService.save(any())).thenReturn(instructor);
        mockMvc.perform(post("/api/v1/instructores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(instructor)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("lvidal@edu.cl"));
    }

    @Test
    void testActualizar() throws Exception {
        when(instructorService.findById(1)).thenReturn(instructor);
        when(instructorService.save(any())).thenReturn(instructor);
        mockMvc.perform(put("/api/v1/instructores/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(instructor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.curso").value("Matemáticas"));
    }

    @Test
    void testEliminar() throws Exception {
        doNothing().when(instructorService).delete(1);
        mockMvc.perform(delete("/api/v1/instructores/1"))
                .andExpect(status().isNoContent());
    }
}
