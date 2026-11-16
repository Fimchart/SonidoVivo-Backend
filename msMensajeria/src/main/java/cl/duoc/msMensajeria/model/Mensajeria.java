package cl.duoc.msMensajeria.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "contacto")
public class Mensajeria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre completo es obligatorio")
    @Column(name = "nombre_completo", nullable = false, length = 150)
    private String nombreCompleto;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El formato del correo no es valido")
    @Column(nullable = false, length = 150)
    private String correo;

    @NotBlank(message = "El mensaje no puede estar vacío")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensaje;

    // ESTADO INICIAL DEL MENSAJE CONTACTO
    @Column(name = "estado", nullable = false, length = 20)
    private String estado = "PENDIENTE";

    // VINCULA EL ATRIBUTO JAVA CON LA COLUMNA FECHA_CREACION DE MYSQL
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    // ASIGNA AUTOMATICAMENTE LA FECHA Y HORA ANTES DE INSERTAR EL CONTACTO
    @PrePersist
    protected void asignarFechaCreacion() {
        this.fechaCreacion = LocalDateTime.now();
    }
}