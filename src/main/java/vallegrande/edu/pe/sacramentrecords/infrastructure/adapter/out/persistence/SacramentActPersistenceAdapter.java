package vallegrande.edu.pe.sacramentrecords.infrastructure.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vallegrande.edu.pe.sacramentrecords.domain.model.SacramentAct;
import vallegrande.edu.pe.sacramentrecords.domain.port.out.SacramentActRepositoryPort;

import java.util.UUID;

/** Adaptador de salida de actas: traduce entre R2DBC y el dominio. */
@Component
@RequiredArgsConstructor
public class SacramentActPersistenceAdapter implements SacramentActRepositoryPort {

    private final SacramentActR2dbcRepo repository;

    private SacramentAct toDomain(SacramentActEntity entity) {
        return new SacramentAct(entity.getId(), entity.getTenantId(), entity.getRecordId(),
                entity.getFileUrl(), entity.getCreatedAt());
    }

    private SacramentActEntity toEntity(SacramentAct act) {
        return new SacramentActEntity(act.getId(), act.getTenantId(), act.getRecordId(),
                act.getFileUrl(), act.getCreatedAt());
    }

    @Override
    public Flux<SacramentAct> findAll() {
        return repository.findAll().map(this::toDomain);
    }

    @Override
    public Flux<SacramentAct> findByTenantId(Integer tenantId) {
        return repository.findByTenantId(tenantId).map(this::toDomain);
    }

    @Override
    public Mono<SacramentAct> findByRecordId(UUID recordId) {
        return repository.findByRecordId(recordId).map(this::toDomain);
    }

    @Override
    public Mono<SacramentAct> findById(UUID id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public Mono<SacramentAct> save(SacramentAct act) {
        return repository.save(toEntity(act)).map(this::toDomain);
    }
}
