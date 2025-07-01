package com.edutech.cl.edutech.service;

import com.edutech.cl.edutech.model.Instructor;
import com.edutech.cl.edutech.repository.InstructorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class InstructorServiceTest {

    @Autowired private InstructorService instructorService;
    @MockitoBean
    private InstructorRepository instructorRepository;

    @Test
    void testFindAll() {
        when(instructorRepository.findAll()).thenReturn(List.of(new Instructor(1, "Pedro", "p@edu.cl", "pass", "Redes", 1)));
        assertEquals(1, instructorService.findAll().size());
    }

    @Test
    void testFindById() {
        Instructor i = new Instructor(1, "Pedro", "p@edu.cl", "pass", "Redes", 1);
        when(instructorRepository.findById(1)).thenReturn(Optional.of(i));
        assertEquals("Redes", instructorService.findById(1).getCurso());
    }

    @Test
    void testSave() {
        Instructor i = new Instructor(1, "Ana", "ana@edu.cl", "clave", "Java", 2);
        when(instructorRepository.save(i)).thenReturn(i);
        assertEquals("Java", instructorService.save(i).getCurso());
    }

    @Test
    void testDelete() {
        doNothing().when(instructorRepository).deleteById(1);
        instructorService.delete(1);
        verify(instructorRepository).deleteById(1);
    }
}

