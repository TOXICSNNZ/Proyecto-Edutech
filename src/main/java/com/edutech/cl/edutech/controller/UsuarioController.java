package com.edutech.cl.edutech.controller;

import com.edutech.cl.edutech.model.Usuario;
import com.edutech.cl.edutech.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @Operation(summary = "Obtener todos los Usuarios", description = "Obtiene una lista de todos los Usuarios")
    public ResponseEntity<List<Usuario>> listar() {
        List<Usuario> usuarios = usuarioService.findAll();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    @Operation(summary = "Crea un nuevo Usuario", description = "Añade un nuevo Usuario")
    public ResponseEntity<Usuario> guardar(@RequestBody Usuario usuario) {
        Usuario nuevo = usuarioService.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener los Usuarios por Id", description = "Obtiene los datos de un Usuario por su Id")
    public ResponseEntity<Usuario> buscar(@PathVariable Integer id) {
        try {
            Usuario usuario = usuarioService.findById(id);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza un Usuario", description = "Actualiza un Usuario")
    public ResponseEntity<Usuario> actualizar(@PathVariable Integer id, @RequestBody Usuario usuario) {
        try {
            Usuario user = usuarioService.findById(id);
            user.setNombre(usuario.getNombre());
            user.setEmail(usuario.getEmail());
            user.setContrasena(usuario.getContrasena());
            usuarioService.save(user);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un Usuario ", description = "Eliminar un Usuario por su Id")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            usuarioService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
