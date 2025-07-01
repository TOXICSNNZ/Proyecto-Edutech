package com.edutech.cl.edutech.controller;

import com.edutech.cl.edutech.model.Instructor;
import com.edutech.cl.edutech.service.InstructorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/instructores")
@Tag(name = "Instructores", description = "Operaciones relacionadas con los Instructores")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @GetMapping
    @Operation(summary = "Obtener todos los Instructores", description = "Obtiene una lista de todos los Instructores")
    public ResponseEntity<List<Instructor>> listar() {
        List<Instructor> lista = instructorService.findAll();
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }

    @PostMapping
    @Operation(summary = "Crea un nuevo Instructor", description = "Añade un nuevo Instructor")
    public ResponseEntity<Instructor> guardar(@RequestBody Instructor instructor) {
        return ResponseEntity.status(HttpStatus.CREATED).body(instructorService.save(instructor));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un Instructor por Id", description = "Obtiene un Instructor por su Id")
    public ResponseEntity<Instructor> buscar(@PathVariable Integer id) {
        try {
            Instructor inst = instructorService.findById(id);
            return ResponseEntity.ok(inst);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza un Instructor", description = "Actualiza un Instructor")

    public ResponseEntity<Instructor> actualizar(@PathVariable Integer id, @RequestBody Instructor instructor) {
        try {
            Instructor inst = instructorService.findById(id);
            inst.setNombre(instructor.getNombre());
            inst.setEmail(instructor.getEmail());
            inst.setContrasena(instructor.getContrasena());
            inst.setCurso(instructor.getCurso());
            inst.setGerente(instructor.getGerente());
            instructorService.save(inst);
            return ResponseEntity.ok(inst);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un Instructor ", description = "Eliminar un Instructor de los que hay")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            instructorService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
