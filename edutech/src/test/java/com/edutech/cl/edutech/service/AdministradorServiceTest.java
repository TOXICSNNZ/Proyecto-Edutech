package com.edutech.cl.edutech.service;

import com.edutech.cl.edutech.model.Administrador;
import com.edutech.cl.edutech.repository.AdministradorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AdministradorServiceTest {

    @Autowired
    private AdministradorService administradorService;

    @MockitoBean
    private AdministradorRepository administradorRepository;

    @Test
    void testFindAll() {
        when(administradorRepository.findAll()).thenReturn(List.of(new Administrador(1, "admin@edu.cl", "1234")));
        assertEquals(1, administradorService.findAll().size());
    }

    @Test
    void testFindById() {
        when(administradorRepository.findById(1)).thenReturn(Optional.of(new Administrador(1, "admin@edu.cl", "1234")));
        assertEquals("admin@edu.cl", administradorService.findById(1).getEmail());
    }

    @Test
    void testSave() {
        Administrador a = new Administrador(1, "nuevo@edu.cl", "clave");
        when(administradorRepository.save(a)).thenReturn(a);
        assertEquals("nuevo@edu.cl", administradorService.save(a).getEmail());
    }

    @Test
    void testDelete() {
        doNothing().when(administradorRepository).deleteById(1);
        administradorService.delete(1);
        verify(administradorRepository, times(1)).deleteById(1);
    }
}
