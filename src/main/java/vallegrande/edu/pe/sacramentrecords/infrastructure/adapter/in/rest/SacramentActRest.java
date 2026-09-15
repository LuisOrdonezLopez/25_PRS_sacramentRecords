package vallegrande.edu.pe.sacramentrecords.infrastructure.adapter.in.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vallegrande.edu.pe.sacramentrecords.domain.model.SacramentAct;
import vallegrande.edu.pe.sacramentrecords.domain.port.in.SacramentActUseCase;

import java.util.UUID;

/** Adaptador de entrada para las operaciones HTTP de actas. */
@RestController
@RequestMapping("/api/v1/sacrament-acts")
@RequiredArgsConstructor
public class SacramentActRest {

    private final SacramentActUseCase useCase;

    @GetMapping
    @PreAuthorize("hasRole('PARROCO')")
    public Flux<SacramentAct> listarTodos() {
        return useCase.listarTodos();
    }

    @GetMapping("/tenant/{tenantId}")
    @PreAuthorize("hasRole('PARROCO')")
    public Flux<SacramentAct> listarPorTenant(@PathVariable Integer tenantId) {
        return useCase.listarPorTenant(tenantId);
    }

    @GetMapping("/registro/{recordId}")
    @PreAuthorize("hasRole('PARROCO')")
    public Mono<ResponseEntity<SacramentAct>> buscarPorRegistro(@PathVariable UUID recordId) {
        return useCase.buscarPorRegistro(recordId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('PARROCO')")
    public Mono<ResponseEntity<SacramentAct>> buscarPorId(@PathVariable UUID id) {
        return useCase.buscarPorId(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('PARROCO')")
    public Mono<SacramentAct> guardar(@RequestBody SacramentAct act) {
        return useCase.guardar(act);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PARROCO')")
    public Mono<ResponseEntity<SacramentAct>> actualizar(@PathVariable UUID id,
            @RequestBody SacramentAct act) {
        return useCase.actualizar(id, act)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
