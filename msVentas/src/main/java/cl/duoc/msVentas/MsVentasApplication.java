package cl.duoc.msVentas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
@OpenAPIDefinition(
    info = @Info(
        title = "API Microservicio Ventas - Sonido Vivo",
        version = "1.0",
        description = "Gestión de órdenes de compra, boletas e historial de ventas"
    )
)

@SpringBootApplication
public class MsVentasApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsVentasApplication.class, args);
	}

}
