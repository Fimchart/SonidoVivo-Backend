package cl.duoc.msVentas.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Solicitud para actualizar el estado de una venta")
public class EstadoVentaRequest {

    @Schema(
        description = "Nuevo estado de la venta",
        example = "ENVIADO",
        allowableValues = {"COMPLETADA", "ENVIADO", "ENTREGADO", "ANULADA"}
    )
    private String estado;

    public EstadoVentaRequest() {
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}