package com.Controller;

import com.Model.EntidadBancaria;
import com.Service.EntidadBancariaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/entidades")
@Tag(name = "Entidades Bancarias", description = "Operaciones relacionadas con entidades bancarias")
public class EntidadBancariaController {

    @Autowired
    private EntidadBancariaService service;

    @GetMapping
    @Operation(summary = "Obtener todas las entidades bancarias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de entidades bancarias obtenida", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EntidadBancaria.class))),
            @ApiResponse(responseCode = "204", description = "No hay entidades bancarias registradas")
    })
    public ResponseEntity<List<EntidadBancaria>> obtenerTodas() {
        List<EntidadBancaria> entidades = service.obtenerTodas();
        if (entidades.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content si no hay entidades
        } else {
            return ResponseEntity.ok(entidades); // 200 OK con la lista de entidades
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una entidad bancaria por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entidad encontrada"),
            @ApiResponse(responseCode = "404", description = "Entidad no encontrada")
    })
    public ResponseEntity<EntidadBancaria> obtenerPorId(@PathVariable Long id) {
        try {
            EntidadBancaria entidad = service.obtenerPorId(id);
            return ResponseEntity.ok(entidad); // 200 OK
        } catch (ResponseStatusException ex) {
            return ResponseEntity.status(ex.getStatusCode()).build(); // 404 Not Found
        }
    }
    @PostMapping
    @Operation(summary = "Crear una nueva entidad bancaria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Entidad creada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EntidadBancaria.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida (errores de validación, ID existente, etc.)")
    })
    public ResponseEntity<EntidadBancaria> crear(@RequestBody EntidadBancaria entidad, UriComponentsBuilder uriBuilder) {
        try {
            EntidadBancaria nuevaEntidad = service.crear(entidad);
            URI location = uriBuilder.path("/api/entidades/{id}").buildAndExpand(nuevaEntidad.getId()).toUri();
            return ResponseEntity.created(location).body(nuevaEntidad); // 201 Created
        } catch (ResponseStatusException ex) {
            return ResponseEntity.status(ex.getStatusCode()).build(); // 400 Bad Request
        }
    }
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una entidad bancaria existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entidad actualizada"),
            @ApiResponse(responseCode = "404", description = "Entidad no encontrada")
    })
    public ResponseEntity<EntidadBancaria> actualizar(@PathVariable Long id, @RequestBody EntidadBancaria entidad) {
        try {
            EntidadBancaria entidadActualizada = service.actualizar(id, entidad);
            return ResponseEntity.ok(entidadActualizada); // 200 OK
        } catch (ResponseStatusException ex) {
            return ResponseEntity.status(ex.getStatusCode()).build(); // 404 Not Found
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una entidad bancaria por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Entidad eliminada"),
            @ApiResponse(responseCode = "404", description = "Entidad no encontrada")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (ResponseStatusException ex) {
            return ResponseEntity.status(ex.getStatusCode()).build(); // 404 Not Found
        }
    }

}