package vallegrande.edu.pe.sacramentrecords.infrastructure.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vallegrande.edu.pe.sacramentrecords.domain.model.SacramentRecord;
import vallegrande.edu.pe.sacramentrecords.domain.port.out.SacramentRecordRepositoryPort;

import java.util.UUID;

/** Adaptador de salida de registros: traduce entre R2DBC y el dominio. */
@Component
@RequiredArgsConstructor
public class SacramentRecordPersistenceAdapter implements SacramentRecordRepositoryPort {

    private final SacramentRecordR2dbcRepo repository;

    private SacramentRecord toDomain(SacramentRecordEntity entity) {
        return new SacramentRecord(entity.getId(), entity.getTenantId(), entity.getParishionerName(),
                entity.getFatherName(), entity.getMotherName(), entity.getSacramentId(),
                entity.getGodfatherName(), entity.getGodmotherName(), entity.getPriestId(),
                entity.getCelebrationDate(), entity.getPlace(), entity.getNotes(), entity.getStatus(),
                entity.getCreatedAt(), entity.getUpdatedAt());
    }

    private SacramentRecordEntity toEntity(SacramentRecord record) {
        return new SacramentRecordEntity(record.getId(), record.getTenantId(), record.getParishionerName(),
                record.getFatherName(), record.getMotherName(), record.getSacramentId(),
                record.getGodfatherName(), record.getGodmotherName(), record.getPriestId(),
                record.getCelebrationDate(), record.getPlace(), record.getNotes(), record.getStatus(),
                record.getCreatedAt(), record.getUpdatedAt());
    }

    @Override
    public Flux<SacramentRecord> findAll() {
        return repository.findAll().map(this::toDomain);
    }

    @Override
    public Flux<SacramentRecord> findByTenantId(Integer tenantId) {
        return repository.findByTenantId(tenantId).map(this::toDomain);
    }

    @Override
    public Flux<SacramentRecord> findByParishionerName(String parishionerName) {
        return repository.findByParishionerName(parishionerName).map(this::toDomain);
    }

    @Override
    public Flux<SacramentRecord> findByStatus(String status) {
        return repository.findByStatus(status).map(this::toDomain);
    }

    @Override
    public Flux<SacramentRecord> findBySacramentId(UUID sacramentId) {
        return repository.findBySacramentId(sacramentId).map(this::toDomain);
    }

    @Override
    public Flux<SacramentRecord> findByPriestId(Integer priestId) {
        return repository.findByPriestId(priestId).map(this::toDomain);
    }

    @Override
    public Mono<SacramentRecord> findById(UUID id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public Mono<SacramentRecord> save(SacramentRecord record) {
        return repository.save(toEntity(record)).map(this::toDomain);
    }

    @Override
    public Mono<SacramentRecord> updateStatus(UUID id, String status) {
        return repository.updateStatus(id, status).map(this::toDomain);
    }
}
