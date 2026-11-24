package cl.duoc.msVentas.controller;


import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.msVentas.model.Venta;
import cl.duoc.msVentas.service.VentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/ventas")
@CrossOrigin(origins = "*")
@Tag(name = "Ventas", description = "Endpoints para registro y consulta de compras de Sonido Vivo")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @PostMapping
    @Operation(summary = "Registrar una nueva venta desde el carrito")
    public ResponseEntity<?> crearVenta(@RequestBody Venta venta) {
        try {
            Venta guardada = ventaService.registrarVenta(venta);
            return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    @Operation(summary = "Listar todas las ventas (Administrador)")
    public ResponseEntity<List<Venta>> listarTodas() {
        return ResponseEntity.ok(ventaService.listarTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener boleta / detalle de una venta por ID")
    public ResponseEntity<Venta> obtenerPorId(@PathVariable Long id) {
        return ventaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{email}")
    @Operation(summary = "Listar historial de compras por correo del cliente")
    public ResponseEntity<List<Venta>> listarPorEmail(@PathVariable String email) {
        return ResponseEntity.ok(ventaService.listarPorEmail(email));
    }

    @PatchMapping("/{id}/estado")
    @Operation(summary = "Actualizar estado de una venta (COMPLETADA, ENVIADO, ENTREGADO, ANULADA)")
    public ResponseEntity<Venta> actualizarEstado(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        String nuevoEstado = body.get("estado");
        if (nuevoEstado == null || nuevoEstado.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        return ventaService.actualizarEstado(id, nuevoEstado)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}


