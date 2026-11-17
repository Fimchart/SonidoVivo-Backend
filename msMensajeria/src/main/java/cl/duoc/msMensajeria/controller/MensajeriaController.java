package cl.duoc.msMensajeria.controller;

import cl.duoc.msMensajeria.model.Mensajeria;
import cl.duoc.msMensajeria.service.MensajeriaService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mensajes")
@CrossOrigin(origins = "http://localhost:5173")
public class MensajeriaController {


    private final MensajeriaService service;

    public MensajeriaController(MensajeriaService service) {
        this.service = service;
    }



    @GetMapping
    public ResponseEntity<List<Mensajeria>> listar() {
        return ResponseEntity.ok(service.listar());
    }


    @PostMapping
    public ResponseEntity<Mensajeria> guardar(@Valid @RequestBody Mensajeria contacto) {
        Mensajeria nuevo = service.guardar(contacto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }


    @PutMapping("/{id}/responder")
    public ResponseEntity<Mensajeria> responder(@PathVariable Long id) {
        Mensajeria actualizado = service.marcarRespondido(id);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (service.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}  
  


