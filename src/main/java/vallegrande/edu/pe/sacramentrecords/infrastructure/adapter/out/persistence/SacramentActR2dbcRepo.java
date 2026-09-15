package vallegrande.edu.pe.sacramentrecords.infrastructure.adapter.out.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/** Repositorio técnico R2DBC de actas. */
public interface SacramentActR2dbcRepo extends ReactiveCrudRepository<SacramentActEntity, UUID> {

    Flux<SacramentActEntity> findByTenantId(Integer tenantId);

    Mono<SacramentActEntity> findByRecordId(UUID recordId);
}
