package cl.duoc.msMensajeria.service;

import java.util.List;
import org.springframework.stereotype.Service;
import cl.duoc.msMensajeria.model.Mensajeria;
import cl.duoc.msMensajeria.repository.MensajeriaRepository;


// indica que contiene logica de negocio
@Service 
public class MensajeriaService {
    private final MensajeriaRepository repository;

    // Spring inyecta el repositorio mendiante constructor
    public MensajeriaService(MensajeriaRepository repository) {
        this.repository = repository;
    }


    // Guarda nuevo contacto
    public Mensajeria guardar(Mensajeria contacto){
        // Asegura que se cree un registro nuevo
        contacto.setId(null);
        // Todo contacto nuevo comienza como pendiente
        contacto.setEstado("PENDIENTE");
        // La fecha se asigna desde el @PrePersist del modelo
        return repository.save(contacto);
    }

    // Consulta todos los contactos alamacendos
    public List<Mensajeria> listar() {
        return repository.findAll();
    }

    // Actualizar estado a RESPONDIDO (para panel admin)
    public Mensajeria marcarRespondido(Long id) {
        return repository.findById(id).map(msg -> {
            msg.setEstado("RESPONDIDO");
            return repository.save(msg);
        }).orElse(null);
    }

    // Eliminar mensaje por ID
    public boolean eliminar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }


}
