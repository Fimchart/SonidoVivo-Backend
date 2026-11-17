package cl.duoc.msUsuarios.repository;

import java.util.Optional;
import cl.duoc.msUsuarios.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuariosRepository  extends JpaRepository<Usuarios,Long>{
    Optional<Usuarios> findByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCase(String email);
}
