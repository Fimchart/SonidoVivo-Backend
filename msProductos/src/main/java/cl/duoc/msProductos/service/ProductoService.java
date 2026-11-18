package cl.duoc.msProductos.service;


import cl.duoc.msProductos.model.Producto;
import cl.duoc.msProductos.repository.ProductoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ProductoService {

    private final ProductoRepository repository;

    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }

    public List<Producto> listarTodos() {
        return repository.findAll();
    }

    public Optional<Producto> obtenerPorId(Long id) {
        return repository.findById(id);
    }

    public List<Producto> listarPorCategoria(String categoria) {
        return repository.findByCategoriaIgnoreCase(categoria);
    }

    public Producto crear(Producto producto) {
        if (repository.existsByCodigo(producto.getCodigo())) {
            throw new RuntimeException("Ya existe un producto con el código: " + producto.getCodigo());
        }
        producto.setId(null);
        return repository.save(producto);
    }

    public Optional<Producto> actualizar(Long id, Producto datos) {
        return repository.findById(id).map(existente -> {
            existente.setCodigo(datos.getCodigo());
            existente.setTitulo(datos.getTitulo());
            existente.setDescripcion(datos.getDescripcion());
            existente.setDescripcionLarga(datos.getDescripcionLarga());
            existente.setEspecificaciones(datos.getEspecificaciones());
            existente.setPrecio(datos.getPrecio());
            existente.setCategoria(datos.getCategoria());
            existente.setImagen(datos.getImagen());
            return repository.save(existente);
        });
    }

    public boolean eliminar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}