package cl.duoc.msUsuarios;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
    info = @Info(
        title = "API Microservicio Usuarios - Sonido Vivo",
        version = "1.0",
        description = "Gestión de registro, autenticación con BCrypt y administración de roles (ADMIN / CLIENTE)."
    )
)

@SpringBootApplication
public class MsUsuariosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsUsuariosApplication.class, args);
	}

}
