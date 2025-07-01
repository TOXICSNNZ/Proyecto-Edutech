package com.edutech.cl.edutech.controller;

import com.edutech.cl.edutech.assemblers.AdministradorModelAssembler;
import com.edutech.cl.edutech.model.Administrador;
import com.edutech.cl.edutech.service.AdministradorService;
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
@RequestMapping("/api/v2/administradores")
public class AdministradorControllerV2 {

    @Autowired
    private AdministradorService administradorService;

    @Autowired
    private AdministradorModelAssembler assembler;


    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todos los Administradores", description = "Obtiene una lista de todos los Administradores")
    public CollectionModel<EntityModel<Administrador>> getAllAdministradores() {
        List<EntityModel<Administrador>> admins = administradorService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(admins,
                linkTo(methodOn(AdministradorControllerV2.class).getAllAdministradores()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todos los Administradores por Id", description = "Obtiene los datos de un Administrador por su Id")
    public EntityModel<Administrador> getAdministradorById(@PathVariable Integer id) {
        Administrador admin = administradorService.findById(id);
        return assembler.toModel(admin);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crea un nuevo Administrador", description = "Añade un nuevo Administrador")
    public ResponseEntity<EntityModel<Administrador>> createAdministrador(@RequestBody Administrador admin) {
        Administrador nuevo = administradorService.save(admin);
        return ResponseEntity
                .created(linkTo(methodOn(AdministradorControllerV2.class).getAdministradorById(nuevo.getId())).toUri())
                .body(assembler.toModel(nuevo));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualiza un Administrador", description = "Actualiza un Administrador")
    public ResponseEntity<EntityModel<Administrador>> updateAdministrador(@PathVariable Integer id, @RequestBody Administrador admin) {
        Administrador existente = administradorService.findById(id);
        existente.setEmail(admin.getEmail());
        existente.setContrasena(admin.getContrasena());
        Administrador actualizado = administradorService.save(existente);
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Elimina un Administrador ", description = "Eliminar un Administrador por su Id")
    public ResponseEntity<?> deleteAdministrador(@PathVariable Integer id) {
        administradorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

