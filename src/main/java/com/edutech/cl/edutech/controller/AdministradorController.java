package com.edutech.cl.edutech.controller;

import com.edutech.cl.edutech.model.Administrador;
import com.edutech.cl.edutech.service.AdministradorService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/administradores")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;

    @GetMapping
    @Operation(summary = "Obtener todos los Administradores", description = "Obtiene una lista de todos los Administradores")
    public ResponseEntity<List<Administrador>> listar() {
        List<Administrador> lista = administradorService.findAll();
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }

    @PostMapping
    @Operation(summary = "Crea un nuevo Administrador", description = "Añade un nuevo Administrador")
    public ResponseEntity<Administrador> guardar(@RequestBody Administrador administrador) {
        return ResponseEntity.status(HttpStatus.CREATED).body(administradorService.save(administrador));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener todos los Administradores por Id", description = "Obtiene los datos de un Administrador por su Id")
    public ResponseEntity<Administrador> buscar(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(administradorService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza un Administrador", description = "Actualiza un Administrador")
    public ResponseEntity<Administrador> actualizar(@PathVariable Integer id, @RequestBody Administrador administrador) {
        try {
            Administrador admin = administradorService.findById(id);
            admin.setEmail(administrador.getEmail());
            admin.setContrasena(administrador.getContrasena());
            administradorService.save(admin);
            return ResponseEntity.ok(admin);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un Administrador ", description = "Eliminar un Administrador por su Id")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            administradorService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
