package cl.duoc.msMensajeria.controller;

import cl.duoc.msMensajeria.model.Mensajeria;
import cl.duoc.msMensajeria.service.MensajeriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Mensajería", description = "Gestión de mensajes de contacto enviados desde la tienda web")
@RestController
@RequestMapping("/api/mensajes")
@CrossOrigin(origins = "http://localhost:5173")
public class MensajeriaController {

    private final MensajeriaService service;

    public MensajeriaController(MensajeriaService service) {
        this.service = service;
    }

    @Operation(
        summary = "Listar todos los mensajes",
        description = "Retorna la lista completa de mensajes de contacto registrados en MySQL."
    )
    @ApiResponse(responseCode = "200", description = "Lista de mensajes obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<Mensajeria>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @Operation(
        summary = "Crear mensaje de contacto",
        description = "Guarda un nuevo mensaje enviado desde el formulario público con estado inicial PENDIENTE."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Mensaje registrado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o campos obligatorios vacíos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Mensajeria> guardar(@Valid @RequestBody Mensajeria contacto) {
        Mensajeria nuevo = service.guardar(contacto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @Operation(
        summary = "Marcar mensaje como VISTO",
        description = "Actualiza el estado del mensaje a VISTO cuando el administrador lo abre en el panel."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Estado actualizado a VISTO"),
        @ApiResponse(responseCode = "404", description = "Mensaje no encontrado con el ID indicado", content = @Content)
    })
    @PutMapping("/{id}/visto")
    public ResponseEntity<Mensajeria> marcarVisto(
            @Parameter(description = "ID del mensaje a actualizar", example = "1")
            @PathVariable Long id) {
        Mensajeria actualizado = service.marcarVisto(id);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(
        summary = "Eliminar mensaje",
        description = "Elimina permanentemente un mensaje de contacto según su ID."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Mensaje eliminado correctamente", content = @Content),
        @ApiResponse(responseCode = "404", description = "Mensaje no encontrado con el ID indicado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del mensaje a eliminar", example = "1")
            @PathVariable Long id) {
        if (service.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}