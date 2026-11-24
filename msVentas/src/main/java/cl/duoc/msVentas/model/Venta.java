package cl.duoc.msVentas.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ventas")
@Schema(description = "Orden de compra o boleta registrada en Sonido Vivo")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID autogenerado de la venta", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(name = "usuario_id")
    @Schema(description = "ID del usuario comprador", example = "1")
    private Long usuarioId;

    @Column(name = "usuario_email", nullable = false, length = 150)
    @Schema(description = "Correo electrónico del comprador", example = "cliente@sonidovivo.cl", requiredMode = Schema.RequiredMode.REQUIRED)
    private String usuarioEmail;

    @Column(name = "usuario_nombre", nullable = false, length = 150)
    @Schema(description = "Nombre completo del comprador", example = "Juan Pérez", requiredMode = Schema.RequiredMode.REQUIRED)
    private String usuarioNombre;

    @Column(nullable = false)
    @Schema(description = "Fecha y hora de registro asignada por el servidor", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime fecha;

    @Column(nullable = false)
    @Schema(description = "Monto total calculado automáticamente en el servidor", example = "149990", accessMode = Schema.AccessMode.READ_ONLY)
    private Long total;

    @Column(nullable = false, length = 40)
    @Schema(description = "Estado actual de la venta", example = "COMPLETADA", allowableValues = {"COMPLETADA", "ENVIADO", "ENTREGADO", "ANULADA"})
    private String estado = "COMPLETADA";

    @Column(name = "metodo_pago", length = 80)
    @Schema(description = "Método de pago utilizado", example = "Webpay / Tarjeta")
    private String metodoPago = "Webpay / Tarjeta";

    @Column(name = "direccion_envio", length = 255)
    @Schema(description = "Dirección de despacho", example = "Av. Providencia 1234, Santiago")
    private String direccionEnvio;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "venta_id", nullable = false)
    @Schema(description = "Lista de productos incluidos en la compra", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<DetalleVenta> detalles = new ArrayList<>();

    public Venta() {
    }

    @PrePersist
    public void prePersist() {
        if (this.fecha == null) {
            this.fecha = LocalDateTime.now();
        }
        if (this.estado == null || this.estado.isBlank()) {
            this.estado = "COMPLETADA";
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuarioEmail() {
        return usuarioEmail;
    }

    public void setUsuarioEmail(String usuarioEmail) {
        this.usuarioEmail = usuarioEmail;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getDireccionEnvio() {
        return direccionEnvio;
    }

    public void setDireccionEnvio(String direccionEnvio) {
        this.direccionEnvio = direccionEnvio;
    }

    public List<DetalleVenta> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVenta> detalles) {
        this.detalles = detalles;
    }
}