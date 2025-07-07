package com.edutech.cl.edutech.controller;

import com.edutech.cl.edutech.model.Administrador;
import com.edutech.cl.edutech.service.AdministradorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/administradores")
@Tag(name = "Administradores", description = "Operaciones relacionadas con la gestión de administradores")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;

    @GetMapping
    @Operation(summary = "Obtener todos los Administradores", description = "Obtiene una lista de todos los Administradores")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    public ResponseEntity<List<Administrador>> listar() {
        List<Administrador> lista = administradorService.findAll();
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }

    @PostMapping
    @Operation(summary = "Crea un nuevo Administrador", description = "Añade un nuevo Administrador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Administrador creado correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Administrador.class))),
            @ApiResponse(responseCode = "404", description = "Administrador no encontrado")
    })
    public ResponseEntity<Administrador> guardar(@RequestBody Administrador administrador) {
        return ResponseEntity.status(HttpStatus.CREATED).body(administradorService.save(administrador));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener todos los Administradores por Id", description = "Obtiene los datos de un Administrador por su Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Administrador encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Administrador.class))),
            @ApiResponse(responseCode = "404", description = "Administrador no encontrado")
    })
    public ResponseEntity<Administrador> buscar(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(administradorService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza un Administrador", description = "Actualiza un Administrador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Administrador Actualizado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Administrador.class))),
            @ApiResponse(responseCode = "404", description = "Administrador no encontrado")
    })
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Administrador Eliminado"),
            @ApiResponse(responseCode = "404", description = "Administrador no encontrado")
    })
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            administradorService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
