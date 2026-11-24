package cl.duoc.msVentas.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.duoc.msVentas.model.DetalleVenta;
import cl.duoc.msVentas.model.Venta;
import cl.duoc.msVentas.repository.VentaRepository;

@Service
public class VentaService {

    private final VentaRepository ventaRepository;

    public VentaService(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    @Transactional
    public Venta registrarVenta(Venta venta) {
        if (venta.getDetalles() == null || venta.getDetalles().isEmpty()) {
            throw new IllegalArgumentException("La venta debe contener al menos un producto.");
        }

        long totalCalculado = 0L;

        for (DetalleVenta item : venta.getDetalles()) {
            long precio = item.getPrecioUnitario() != null ? item.getPrecioUnitario() : 0L;
            int cantidad = item.getCantidad() != null && item.getCantidad() > 0 ? item.getCantidad() : 1;
            long subtotal = precio * cantidad;

            item.setCantidad(cantidad);
            item.setSubtotal(subtotal);
            totalCalculado += subtotal;
        }

        venta.setTotal(totalCalculado);
        venta.setFecha(LocalDateTime.now());
        if (venta.getEstado() == null || venta.getEstado().isBlank()) {
            venta.setEstado("COMPLETADA");
        }

        return ventaRepository.save(venta);
    }

    public List<Venta> listarTodas() {
        return ventaRepository.findAllByOrderByFechaDesc();
    }

    public Optional<Venta> obtenerPorId(Long id) {
        return ventaRepository.findById(id);
    }

    public List<Venta> listarPorEmail(String email) {
        return ventaRepository.findByUsuarioEmailOrderByFechaDesc(email);
    }

    public List<Venta> listarPorUsuarioId(Long usuarioId) {
        return ventaRepository.findByUsuarioIdOrderByFechaDesc(usuarioId);
    }

    @Transactional
    public Optional<Venta> actualizarEstado(Long id, String nuevoEstado) {
        return ventaRepository.findById(id).map(venta -> {
            venta.setEstado(nuevoEstado);
            return ventaRepository.save(venta);
        });
    }
}



