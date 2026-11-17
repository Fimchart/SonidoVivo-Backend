package cl.duoc.msMensajeria;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
    info = @Info(
        title = "API Microservicio Mensajería - Sonido Vivo",
        version = "1.0",
        description = "Gestión de mensajes de contacto enviados desde el formulario web y bandeja administrativa."
    )
)


@SpringBootApplication
public class MsMensajeriaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsMensajeriaApplication.class, args);
	}

}
