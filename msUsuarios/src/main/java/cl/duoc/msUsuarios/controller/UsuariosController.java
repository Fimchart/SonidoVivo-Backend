package cl.duoc.msUsuarios.controller;

import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.msUsuarios.model.Usuarios;
import cl.duoc.msUsuarios.service.UsuariosService;

@RestController 
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:5173")
public class UsuariosController {
    
    private final UsuariosService service;

    public UsuariosController(UsuariosService service) {
        this.service = service;
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@RequestBody Usuarios usuario) {
        try {
            Usuarios nuevo = service.guardar(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales) {
        String correo = credenciales.getOrDefault("correo", credenciales.get("email"));
        String password = credenciales.get("password");

        if (correo == null || password == null) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "Faltan credenciales"));
        }

        return service.login(correo, password)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("mensaje", "Correo o contraseña incorrectos")));
    }
    @GetMapping
    public ResponseEntity<List<Usuarios>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }


}
