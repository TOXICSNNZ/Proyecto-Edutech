package com.edutech.cl.edutech.controller;

import com.edutech.cl.edutech.assemblers.InstructorModelAssembler;
import com.edutech.cl.edutech.model.Instructor;
import com.edutech.cl.edutech.service.InstructorService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/instructores")
public class InstructorControllerV2 {

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private InstructorModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todos los Instructores", description = "Obtiene una lista de todos los Instructores")
    public CollectionModel<EntityModel<Instructor>> getAllInstructores() {
        List<EntityModel<Instructor>> instructores = instructorService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(instructores,
                linkTo(methodOn(InstructorControllerV2.class).getAllInstructores()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todos los Instructores por Id", description = "Obtiene los datos de un Instructor por su Id")
    public EntityModel<Instructor> getInstructorById(@PathVariable Integer id) {
        Instructor instructor = instructorService.findById(id);
        return assembler.toModel(instructor);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crea un nuevo Instructor", description = "Añade un nuevo Instructor")
    public ResponseEntity<EntityModel<Instructor>> createInstructor(@RequestBody Instructor instructor) {
        Instructor nuevo = instructorService.save(instructor);
        return ResponseEntity
                .created(linkTo(methodOn(InstructorControllerV2.class).getInstructorById(nuevo.getId())).toUri())
                .body(assembler.toModel(nuevo));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualiza un Instructor", description = "Actualiza un Instructor")
    public ResponseEntity<EntityModel<Instructor>> updateInstructor(@PathVariable Integer id, @RequestBody Instructor instructor) {
        Instructor existente = instructorService.findById(id);
        existente.setNombre(instructor.getNombre());
        existente.setEmail(instructor.getEmail());
        existente.setContrasena(instructor.getContrasena());
        existente.setCurso(instructor.getCurso());
        existente.setGerente(instructor.getGerente());
        Instructor actualizado = instructorService.save(existente);
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Elimina un Instructor ", description = "Eliminar un Instructor por su Id")
    public ResponseEntity<?> deleteInstructor(@PathVariable Integer id) {
        instructorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
