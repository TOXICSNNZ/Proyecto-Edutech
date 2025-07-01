package com.edutech.cl.edutech.service;

import com.edutech.cl.edutech.model.Usuario;
import com.edutech.cl.edutech.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @MockitoBean
    private UsuarioRepository usuarioRepository;

    @Test
    void testFindAll() {
        when(usuarioRepository.findAll()).thenReturn(List.of(new Usuario(1, "Carlos", "c@correo.com", "1234")));
        List<Usuario> usuarios = usuarioService.findAll();

        assertNotNull(usuarios);
        assertEquals(1, usuarios.size());
    }

    @Test
    void testFindById() {
        Usuario usuario = new Usuario(1, "Laura", "laura@correo.com", "abcd");
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        Usuario encontrado = usuarioService.findById(1);

        assertNotNull(encontrado);
        assertEquals("Laura", encontrado.getNombre());
    }

    @Test
    void testSave() {
        Usuario usuario = new Usuario(1, "Mario", "mario@correo.com", "pass");
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario guardado = usuarioService.save(usuario);

        assertNotNull(guardado);
        assertEquals("Mario", guardado.getNombre());
    }

    @Test
    void testDelete() {
        doNothing().when(usuarioRepository).deleteById(1);
        usuarioService.delete(1);
        verify(usuarioRepository, times(1)).deleteById(1);
    }
}
