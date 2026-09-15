package vallegrande.edu.pe.sacramentrecords.domain.port.out;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vallegrande.edu.pe.sacramentrecords.domain.model.SacramentAct;

import java.util.UUID;

/** Puerto de salida para la persistencia de actas. */
public interface SacramentActRepositoryPort {

    Flux<SacramentAct> findAll();

    Flux<SacramentAct> findByTenantId(Integer tenantId);

    Mono<SacramentAct> findByRecordId(UUID recordId);

    Mono<SacramentAct> findById(UUID id);

    Mono<SacramentAct> save(SacramentAct act);
}
