package cl.duoc.msVentas.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "detalle_ventas")
@Schema(description = "Ítem o producto incluido dentro de una venta")
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID autogenerado del detalle", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(name = "producto_id", nullable = false)
    @Schema(description = "ID del producto en msProductos", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long productoId;

    @Column(name = "producto_codigo", length = 50)
    @Schema(description = "Código del producto", example = "GA001")
    private String productoCodigo;

    @Column(name = "producto_titulo", nullable = false, length = 200)
    @Schema(description = "Nombre del producto", example = "Guitarra Acústica Yamaha F310", requiredMode = Schema.RequiredMode.REQUIRED)
    private String productoTitulo;

    @Column(name = "precio_unitario", nullable = false)
    @Schema(description = "Precio unitario en pesos chilenos", example = "149990", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long precioUnitario;

    @Column(nullable = false)
    @Schema(description = "Cantidad comprada", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer cantidad;

    @Column(nullable = false)
    @Schema(description = "Subtotal calculado automáticamente (precioUnitario * cantidad)", example = "149990", accessMode = Schema.AccessMode.READ_ONLY)
    private Long subtotal;

    public DetalleVenta() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public String getProductoCodigo() {
        return productoCodigo;
    }

    public void setProductoCodigo(String productoCodigo) {
        this.productoCodigo = productoCodigo;
    }

    public String getProductoTitulo() {
        return productoTitulo;
    }

    public void setProductoTitulo(String productoTitulo) {
        this.productoTitulo = productoTitulo;
    }

    public Long getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Long precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Long getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Long subtotal) {
        this.subtotal = subtotal;
    }
}