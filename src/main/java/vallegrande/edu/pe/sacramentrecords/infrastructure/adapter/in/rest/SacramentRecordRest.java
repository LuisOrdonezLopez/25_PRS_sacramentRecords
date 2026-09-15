package vallegrande.edu.pe.sacramentrecords.infrastructure.adapter.in.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vallegrande.edu.pe.sacramentrecords.domain.model.SacramentRecord;
import vallegrande.edu.pe.sacramentrecords.domain.port.in.SacramentRecordUseCase;

import java.util.UUID;

/** Adaptador de entrada para las operaciones HTTP de registros. */
@RestController
@RequestMapping("/api/v1/sacrament-records")
@RequiredArgsConstructor
public class SacramentRecordRest {

    private final SacramentRecordUseCase useCase;

    @GetMapping
    @PreAuthorize("hasRole('PARROCO')")
    public Flux<SacramentRecord> listarTodos() {
        return useCase.listarTodos();
    }

    @GetMapping("/pendientes")
    @PreAuthorize("hasRole('PARROCO')")
    public Flux<SacramentRecord> listarPendientes() {
        return useCase.listarPendientes();
    }

    @GetMapping("/activos")
    @PreAuthorize("hasRole('PARROCO')")
    public Flux<SacramentRecord> listarActivos() {
        return useCase.listarActivos();
    }

    @GetMapping("/inactivos")
    @PreAuthorize("hasRole('PARROCO')")
    public Flux<SacramentRecord> listarInactivos() {
        return useCase.listarInactivos();
    }

    @GetMapping("/tenant/{tenantId}")
    @PreAuthorize("hasRole('PARROCO')")
    public Flux<SacramentRecord> listarPorTenant(@PathVariable Integer tenantId) {
        return useCase.listarPorTenant(tenantId);
    }

    @GetMapping("/persona/{parishionerName}")
    @PreAuthorize("hasRole('PARROCO')")
    public Flux<SacramentRecord> listarPorPersona(@PathVariable String parishionerName) {
        return useCase.listarPorPersona(parishionerName);
    }

    @GetMapping("/sacramento/{sacramentId}")
    @PreAuthorize("hasRole('PARROCO')")
    public Flux<SacramentRecord> listarPorSacramento(@PathVariable UUID sacramentId) {
        return useCase.listarPorSacramento(sacramentId);
    }

    @GetMapping("/sacerdote/{priestId}")
    @PreAuthorize("hasRole('PARROCO')")
    public Flux<SacramentRecord> listarPorSacerdote(@PathVariable Integer priestId) {
        return useCase.listarPorSacerdote(priestId);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('PARROCO')")
    public Mono<ResponseEntity<SacramentRecord>> buscarPorId(@PathVariable UUID id) {
        return useCase.buscarPorId(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('PARROCO')")
    public Mono<SacramentRecord> guardar(@RequestBody SacramentRecord record) {
        return useCase.guardar(record);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PARROCO')")
    public Mono<ResponseEntity<SacramentRecord>> actualizar(@PathVariable UUID id,
            @RequestBody SacramentRecord record) {
        return useCase.actualizar(id, record)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/eliminar")
    @PreAuthorize("hasRole('PARROCO')")
    public Mono<ResponseEntity<SacramentRecord>> eliminar(@PathVariable UUID id) {
        return useCase.eliminar(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/restaurar")
    @PreAuthorize("hasRole('PARROCO')")
    public Mono<ResponseEntity<SacramentRecord>> restaurar(@PathVariable UUID id) {
        return useCase.restaurar(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
