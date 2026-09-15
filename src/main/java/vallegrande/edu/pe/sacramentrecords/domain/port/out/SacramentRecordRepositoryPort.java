package vallegrande.edu.pe.sacramentrecords.domain.port.out;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vallegrande.edu.pe.sacramentrecords.domain.model.SacramentRecord;

import java.util.UUID;

/** Puerto de salida para la persistencia de registros. */
public interface SacramentRecordRepositoryPort {

    Flux<SacramentRecord> findAll();

    Flux<SacramentRecord> findByTenantId(Integer tenantId);

    Flux<SacramentRecord> findByParishionerName(String parishionerName);

    Flux<SacramentRecord> findByStatus(String status);

    Flux<SacramentRecord> findBySacramentId(UUID sacramentId);

    Flux<SacramentRecord> findByPriestId(Integer priestId);

    Mono<SacramentRecord> findById(UUID id);

    Mono<SacramentRecord> save(SacramentRecord record);

    Mono<SacramentRecord> updateStatus(UUID id, String status);
}
