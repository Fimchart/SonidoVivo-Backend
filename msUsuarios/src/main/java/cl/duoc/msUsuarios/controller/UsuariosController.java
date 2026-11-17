package cl.duoc.msUsuarios.controller;

import cl.duoc.msUsuarios.model.Usuarios;
import cl.duoc.msUsuarios.service.UsuariosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Usuarios y Autenticación", description = "Endpoints para registro de clientes, inicio de sesión y consulta de usuarios")
@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:5173")
public class UsuariosController {

    private final UsuariosService service;

    public UsuariosController(UsuariosService service) {
        this.service = service;
    }

    @Operation(
        summary = "Registrar nuevo usuario",
        description = "Crea una cuenta nueva encriptando la contraseña con BCrypt y asignando automáticamente el rol CLIENTE."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Usuario registrado exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuarios.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Correo ya registrado o datos inválidos",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(value = "{\"mensaje\": \"El correo ya está registrado\"}")
            )
        )
    })
    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@RequestBody Usuarios usuario) {
        try {
            Usuarios nuevo = service.guardar(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", e.getMessage()));
        }
    }

    @Operation(
        summary = "Iniciar sesión",
        description = "Valida correo y contraseña encriptada contra MySQL y retorna los datos del usuario junto con su rol."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Autenticación exitosa",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuarios.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Faltan credenciales en la petición",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(value = "{\"mensaje\": \"Faltan credenciales\"}")
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Credenciales incorrectas",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(value = "{\"mensaje\": \"Correo o contraseña incorrectos\"}")
            )
        )
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Schema(example = "{\"correo\": \"ThomasAdmin@sonidovivo.cl\", \"password\": \"Thomas2024!\"}")
            @RequestBody Map<String, String> credenciales) {
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

    @Operation(
        summary = "Listar todos los usuarios",
        description = "Retorna el listado completo de usuarios registrados en el sistema."
    )
    @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<Usuarios>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

}