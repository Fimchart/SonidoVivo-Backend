package cl.duoc.msProductos;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
    info = @Info(
        title = "API Microservicio Productos - Sonido Vivo",
        version = "1.0",
        description = "Gestión del catálogo de instrumentos musicales y equipamiento de audio (CRUD completo)."
    )
)
@SpringBootApplication
public class MsProductosApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsProductosApplication.class, args);
    }

}