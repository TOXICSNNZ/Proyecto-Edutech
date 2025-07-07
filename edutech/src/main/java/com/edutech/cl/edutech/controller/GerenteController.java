package com.edutech.cl.edutech.controller;

import com.edutech.cl.edutech.model.Gerente;
import com.edutech.cl.edutech.service.GerenteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/gerentes")
public class GerenteController {

    @Autowired
    private GerenteService gerenteService;

    @GetMapping
    @Operation(summary = "Listar todos los Gerentes", description = "Obtiene una lista de todos los Gerentes")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    public ResponseEntity<List<Gerente>> listar() {
        List<Gerente> lista = gerenteService.findAll();
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }

    @PostMapping
    @Operation(summary = "Crea un nuevo Gerente", description = "Añade un nuevo Gerente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Gerente creado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Gerente.class))),
    @ApiResponse(responseCode = "404", description = "Gerente no encontrado")
    })
    public ResponseEntity<Gerente> guardar(@RequestBody Gerente gerente) {
        return ResponseEntity.status(HttpStatus.CREATED).body(gerenteService.save(gerente));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener todos los Gerentes por Id", description = "Obtiene un Gerente específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Gerente encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Gerente.class))),
            @ApiResponse(responseCode = "404", description = "Gerente no encontrado")
    })
    public ResponseEntity<Gerente> buscar(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(gerenteService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza a los Gerentes", description = "Actualiza a un Gerente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Gerente actualizado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Gerente.class))),
            @ApiResponse(responseCode = "404", description = "Gerente no encontrado")
    })
    public ResponseEntity<Gerente> actualizar(@PathVariable Integer id, @RequestBody Gerente gerente) {
        try {
            Gerente g = gerenteService.findById(id);
            g.setNombre(gerente.getNombre());
            g.setEmail(gerente.getEmail());
            g.setContrasena(gerente.getContrasena());
            gerenteService.save(g);
            return ResponseEntity.ok(g);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un Gerente ", description = "Eliminar un Gerente de los que hay")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Gerente Eliminado"),
            @ApiResponse(responseCode = "404", description = "Gerente no encontrado")
    })
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            gerenteService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
