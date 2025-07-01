package com.edutech.cl.edutech.service;

import com.edutech.cl.edutech.model.Gerente;
import com.edutech.cl.edutech.repository.GerenteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GerenteServiceTest {

    @Autowired private GerenteService gerenteService;

    @MockitoBean
    private GerenteRepository gerenteRepository;

    @Test
    void testFindAll() {
        when(gerenteRepository.findAll()).thenReturn(List.of(new Gerente(1, "Lucía", "lucia@edu.cl", "clave")));

        List<Gerente> lista = gerenteService.findAll();

        assertNotNull(lista);
        assertEquals(1, lista.size());
        assertEquals("Lucía", lista.get(0).getNombre());
    }

    @Test
    void testFindById() {
        Gerente gerente = new Gerente(1, "Pedro", "pedro@edu.cl", "admin123");
        when(gerenteRepository.findById(1)).thenReturn(Optional.of(gerente));

        Gerente encontrado = gerenteService.findById(1);

        assertNotNull(encontrado);
        assertEquals("Pedro", encontrado.getNombre());
    }

    @Test
    void testSave() {
        Gerente gerente = new Gerente(1, "Sofía", "sofia@edu.cl", "contraseña");
        when(gerenteRepository.save(gerente)).thenReturn(gerente);

        Gerente guardado = gerenteService.save(gerente);

        assertNotNull(guardado);
        assertEquals("Sofía", guardado.getNombre());
    }

    @Test
    void testDelete() {
        doNothing().when(gerenteRepository).deleteById(1);

        gerenteService.delete(1);

        verify(gerenteRepository, times(1)).deleteById(1);
    }
}
