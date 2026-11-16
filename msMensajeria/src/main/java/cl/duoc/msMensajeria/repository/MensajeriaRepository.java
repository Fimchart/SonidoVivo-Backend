package cl.duoc.msMensajeria.repository;

import cl.duoc.msMensajeria.model.Mensajeria;
import org.springframework.data.jpa.repository.JpaRepository;

    // proporciona las operaciones de acceso a la base de datos
    // Mensajeria: entidad que administra el repositorio
    // Long: tipo de dato de la clave primaria (id)

public interface MensajeriaRepository 
        extends JpaRepository<Mensajeria,Long>{
    
    
}
