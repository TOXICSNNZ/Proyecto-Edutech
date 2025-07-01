package com.edutech.cl.edutech.controller;

import com.edutech.cl.edutech.assemblers.GerenteModelAssembler;
import com.edutech.cl.edutech.model.Gerente;
import com.edutech.cl.edutech.service.GerenteService;
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
@RequestMapping("/api/v2/gerentes")
public class GerenteControllerV2 {

    @Autowired
    private GerenteService gerenteService;

    @Autowired
    private GerenteModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todos los Gerentes", description = "Obtiene una lista de todos los Gerentes")
    public CollectionModel<EntityModel<Gerente>> getAllGerentes() {
        List<EntityModel<Gerente>> gerentes = gerenteService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(gerentes,
                linkTo(methodOn(GerenteControllerV2.class).getAllGerentes()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todos los Gerentes por Id", description = "Obtiene los datos de un Gerente por su Id")
    public EntityModel<Gerente> getGerenteById(@PathVariable Integer id) {
        Gerente gerente = gerenteService.findById(id);
        return assembler.toModel(gerente);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crea un nuevo Gerente", description = "Añade un nuevo Gerente")
    public ResponseEntity<EntityModel<Gerente>> createGerente(@RequestBody Gerente gerente) {
        Gerente nuevo = gerenteService.save(gerente);
        return ResponseEntity
                .created(linkTo(methodOn(GerenteControllerV2.class).getGerenteById(nuevo.getId())).toUri())
                .body(assembler.toModel(nuevo));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualiza un Gerente", description = "Actualiza un Gerente")
    public ResponseEntity<EntityModel<Gerente>> updateGerente(@PathVariable Integer id, @RequestBody Gerente gerente) {
        Gerente existente = gerenteService.findById(id);
        existente.setNombre(gerente.getNombre());
        existente.setEmail(gerente.getEmail());
        existente.setContrasena(gerente.getContrasena());
        Gerente actualizado = gerenteService.save(existente);
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Elimina un Gerente ", description = "Eliminar un Gerente por su Id")
    public ResponseEntity<?> deleteGerente(@PathVariable Integer id) {
        gerenteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
