package cl.duoc.msVentas.controller;

import cl.duoc.msVentas.dto.EstadoVentaRequest;
import cl.duoc.msVentas.model.Venta;
import cl.duoc.msVentas.service.VentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ventas")
@CrossOrigin(origins = "*")
@Tag(name = "Ventas", description = "Endpoints para registro, consulta y seguimiento de compras de Sonido Vivo")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @PostMapping
    @Operation(
        summary = "Registrar una nueva venta desde el carrito",
        description = "Calcula subtotales y total en el servidor y guarda la venta junto con sus detalles en MySQL."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Venta registrada correctamente", content = @Content(schema = @Schema(implementation = Venta.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o carrito sin productos")
    })
    public ResponseEntity<?> crearVenta(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos de la venta y productos del carrito",
                required = true,
                content = @Content(
                    schema = @Schema(implementation = Venta.class),
                    examples = @ExampleObject(
                        name = "Compra de ejemplo",
                        value = """
                        {
                          "usuarioId": 1,
                          "usuarioEmail": "cliente@sonidovivo.cl",
                          "usuarioNombre": "Juan Pérez",
                          "metodoPago": "Webpay / Tarjeta",
                          "direccionEnvio": "Av. Providencia 1234, Santiago",
                          "detalles": [
                            {
                              "productoId": 1,
                              "productoCodigo": "GA001",
                              "productoTitulo": "Guitarra Acústica Yamaha F310",
                              "precioUnitario": 149990,
                              "cantidad": 1
                            }
                          ]
                        }
                        """
                    )
                )
            )
            @RequestBody Venta venta) {
        try {
            Venta guardada = ventaService.registrarVenta(venta);
            return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    @Operation(summary = "Listar todas las ventas (Administrador)")
    @ApiResponse(responseCode = "200", description = "Listado de ventas ordenado por fecha descendente")
    public ResponseEntity<List<Venta>> listarTodas() {
        return ResponseEntity.ok(ventaService.listarTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener boleta / detalle de una venta por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Venta encontrada"),
        @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
    public ResponseEntity<Venta> obtenerPorId(@PathVariable Long id) {
        return ventaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{email}")
    @Operation(summary = "Listar historial de compras por correo del cliente")
    @ApiResponse(responseCode = "200", description = "Historial de compras del usuario")
    public ResponseEntity<List<Venta>> listarPorEmail(@PathVariable String email) {
        return ResponseEntity.ok(ventaService.listarPorEmail(email));
    }

    @PatchMapping("/{id}/estado")
    @Operation(summary = "Actualizar estado de una venta (COMPLETADA, ENVIADO, ENTREGADO, ANULADA)")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Estado actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "El campo 'estado' es obligatorio"),
        @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
    public ResponseEntity<Venta> actualizarEstado(
            @PathVariable Long id,
            @RequestBody EstadoVentaRequest body) {
        if (body == null || body.getEstado() == null || body.getEstado().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        return ventaService.actualizarEstado(id, body.getEstado().trim())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}