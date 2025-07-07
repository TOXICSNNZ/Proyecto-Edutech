package com.edutech.cl.edutech.controller;

import com.edutech.cl.edutech.assemblers.AdministradorModelAssembler;
import com.edutech.cl.edutech.model.Administrador;
import com.edutech.cl.edutech.service.AdministradorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/administradores")
@Tag(name = "AdministradoresV2", description = "Operaciones relacionadas con los admins del sistema")
public class AdministradorControllerV2 {

    @Autowired
    private AdministradorService administradorService;

    @Autowired
    private AdministradorModelAssembler assembler;


    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todos los Administradores", description = "Obtiene una lista de todos los Administradores")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    public CollectionModel<EntityModel<Administrador>> getAllAdministradores() {
        List<EntityModel<Administrador>> admins = administradorService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(admins,
                linkTo(methodOn(AdministradorControllerV2.class).getAllAdministradores()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaTypes.HAL_JSON_VALUE })
    @Operation(summary = "Obtener todos los Administradores por Id", description = "Obtiene los datos de un Administrador por su Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Administrador encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Administrador.class))),
            @ApiResponse(responseCode = "404", description = "Administrador no encontrado")
    })
    public EntityModel<Administrador> getAdministradorById(@PathVariable Integer id) {
        Administrador admin = administradorService.findById(id);
        return assembler.toModel(admin);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crea un nuevo Administrador", description = "Añade un nuevo Administrador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Administrador creado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Administrador.class))),
            @ApiResponse(responseCode = "404", description = "Administrador no encontrado")
    })
    public ResponseEntity<EntityModel<Administrador>> createAdministrador(@RequestBody Administrador admin) {
        Administrador nuevo = administradorService.save(admin);
        return ResponseEntity
                .created(linkTo(methodOn(AdministradorControllerV2.class).getAdministradorById(nuevo.getId())).toUri())
                .body(assembler.toModel(nuevo));
    }

    @PutMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaTypes.HAL_JSON_VALUE })
    @Operation(summary = "Actualiza un Administrador", description = "Actualiza un Administrador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Administrador actualizado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Administrador.class))),
            @ApiResponse(responseCode = "404", description = "Administrador no encontrado")
    })
    public ResponseEntity<EntityModel<Administrador>> updateAdministrador(@PathVariable Integer id, @RequestBody Administrador admin) {
        Administrador existente = administradorService.findById(id);
        existente.setEmail(admin.getEmail());
        existente.setContrasena(admin.getContrasena());
        Administrador actualizado = administradorService.save(existente);
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @DeleteMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaTypes.HAL_JSON_VALUE })
    @Operation(summary = "Elimina un Administrador ", description = "Eliminar un Administrador por su Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Administrador Eliminado"),
            @ApiResponse(responseCode = "404", description = "Administrador no encontrado")
    })
    public ResponseEntity<?> deleteAdministrador(@PathVariable Integer id) {
        administradorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

