package com.edutech.cl.edutech.controller;

import com.edutech.cl.edutech.assemblers.UsuarioModelAssembler;
import com.edutech.cl.edutech.model.Usuario;
import com.edutech.cl.edutech.service.UsuarioService;
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
@RequestMapping("/api/v2/usuarios")
public class UsuarioControllerV2 {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todos los Usuarios", description = "Obtiene una lista de todos los Usuarios")
    public CollectionModel<EntityModel<Usuario>> getAllUsuarios() {
        List<EntityModel<Usuario>> usuarios = usuarioService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(usuarios,
                linkTo(methodOn(UsuarioControllerV2.class).getAllUsuarios()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todos los Usuarios por Id", description = "Obtiene los datos de un Usuario por su Id")
    public EntityModel<Usuario> getUsuarioById(@PathVariable Integer id) {
        Usuario usuario = usuarioService.findById(id);
        return assembler.toModel(usuario);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crea un nuevo Usuario", description = "Añade un nuevo Usuario")
    public ResponseEntity<EntityModel<Usuario>> createUsuario(@RequestBody Usuario usuario) {
        Usuario nuevo = usuarioService.save(usuario);
        return ResponseEntity
                .created(linkTo(methodOn(UsuarioControllerV2.class).getUsuarioById(nuevo.getId())).toUri())
                .body(assembler.toModel(nuevo));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualiza un Usuario", description = "Actualiza un Usuario")
    public ResponseEntity<EntityModel<Usuario>> updateUsuario(@PathVariable Integer id, @RequestBody Usuario usuario) {
        Usuario existente = usuarioService.findById(id);
        existente.setNombre(usuario.getNombre());
        existente.setEmail(usuario.getEmail());
        existente.setContrasena(usuario.getContrasena());
        Usuario actualizado = usuarioService.save(existente);
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Elimina un Usuario ", description = "Eliminar un Usuario por su Id")
    public ResponseEntity<?> deleteUsuario(@PathVariable Integer id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
