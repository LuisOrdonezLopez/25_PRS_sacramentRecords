package vallegrande.edu.pe.sacramentrecords.infrastructure.adapter.out.persistence;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/** Repositorio técnico R2DBC de registros. */
public interface SacramentRecordR2dbcRepo extends ReactiveCrudRepository<SacramentRecordEntity, UUID> {

    Flux<SacramentRecordEntity> findByTenantId(Integer tenantId);

    Flux<SacramentRecordEntity> findByParishionerName(String parishionerName);

    Flux<SacramentRecordEntity> findByStatus(String status);

    Flux<SacramentRecordEntity> findBySacramentId(UUID sacramentId);

    Flux<SacramentRecordEntity> findByPriestId(Integer priestId);

    @Query("UPDATE sacrament_records SET status = :status, updated_at = CURRENT_TIMESTAMP WHERE id = :id RETURNING *")
    Mono<SacramentRecordEntity> updateStatus(UUID id, String status);
}
