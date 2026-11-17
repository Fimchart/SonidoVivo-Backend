package cl.duoc.msUsuarios.service;

import java.util.List;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import cl.duoc.msUsuarios.model.Usuarios;
import cl.duoc.msUsuarios.repository.UsuariosRepository;
import jakarta.annotation.PostConstruct;

@Service
public class UsuariosService {

    private final UsuariosRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UsuariosService(UsuariosRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void crearAdminsIniciales() {
        crearAdminSiNoExiste("Thomas", "Escobar", "ThomasAdmin@sonidovivo.cl", "Thomas2024!");
        crearAdminSiNoExiste("Matias", "Pavez", "MatiasAdmin@sonidovivo.cl", "Matias2024!");
    }

    private void crearAdminSiNoExiste(String nombre, String apellido, String email, String password) {
        if (!repository.existsByEmailIgnoreCase(email)) {
            Usuarios admin = new Usuarios();
            admin.setNombre(nombre);
            admin.setApellido(apellido);
            admin.setEmail(email);
            admin.setPassword(passwordEncoder.encode(password));
            admin.setRol("ADMIN");
            repository.save(admin);
        }
    }

    public Usuarios guardar(Usuarios usuario) {
        if (repository.existsByEmailIgnoreCase(usuario.getEmail())) {
            throw new RuntimeException("El correo ya está registrado");
        }
        usuario.setId(null);
        usuario.setRol("CLIENTE");
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return repository.save(usuario);
    }

    public Optional<Usuarios> login(String email, String password) {
        return repository.findByEmailIgnoreCase(email)
                .filter(u -> passwordEncoder.matches(password, u.getPassword()));
    }

    public List<Usuarios> listarTodos() {
        return repository.findAll();
    }
}