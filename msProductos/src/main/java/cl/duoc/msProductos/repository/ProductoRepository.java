package cl.duoc.msProductos.repository;

import cl.duoc.msProductos.model.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    boolean existsByCodigo(String codigo);
    List<Producto> findByCategoriaIgnoreCase(String categoria);
}