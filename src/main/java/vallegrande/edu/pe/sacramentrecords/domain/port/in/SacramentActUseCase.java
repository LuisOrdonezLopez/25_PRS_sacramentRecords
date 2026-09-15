package vallegrande.edu.pe.sacramentrecords.domain.port.in;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vallegrande.edu.pe.sacramentrecords.domain.model.SacramentAct;

import java.util.UUID;

/** Puerto de entrada para los casos de uso de actas sacramentales. */
public interface SacramentActUseCase {

    Flux<SacramentAct> listarTodos();

    Flux<SacramentAct> listarPorTenant(Integer tenantId);

    Mono<SacramentAct> buscarPorRegistro(UUID recordId);

    Mono<SacramentAct> buscarPorId(UUID id);

    Mono<SacramentAct> guardar(SacramentAct act);

    Mono<SacramentAct> actualizar(UUID id, SacramentAct act);
}
