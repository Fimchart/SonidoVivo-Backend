package cl.duoc.msVentas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.msVentas.model.Venta;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {

    List<Venta> findAllByOrderByFechaDesc();

    List<Venta> findByUsuarioEmailOrderByFechaDesc(String usuarioEmail);

    List<Venta> findByUsuarioIdOrderByFechaDesc(Long usuarioId);
}
