package vallegrande.edu.pe.sacramentrecords.domain.port.in;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vallegrande.edu.pe.sacramentrecords.domain.model.SacramentRecord;

import java.util.UUID;

/** Puerto de entrada para los casos de uso de registros sacramentales. */
public interface SacramentRecordUseCase {

    Flux<SacramentRecord> listarTodos();

    Flux<SacramentRecord> listarPendientes();

    Flux<SacramentRecord> listarActivos();

    Flux<SacramentRecord> listarInactivos();

    Flux<SacramentRecord> listarPorTenant(Integer tenantId);

    Flux<SacramentRecord> listarPorPersona(String parishionerName);

    Flux<SacramentRecord> listarPorSacramento(UUID sacramentId);

    Flux<SacramentRecord> listarPorSacerdote(Integer priestId);

    Mono<SacramentRecord> buscarPorId(UUID id);

    Mono<SacramentRecord> guardar(SacramentRecord record);

    Mono<SacramentRecord> actualizar(UUID id, SacramentRecord record);

    Mono<SacramentRecord> eliminar(UUID id);

    Mono<SacramentRecord> restaurar(UUID id);
}
