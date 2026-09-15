package vallegrande.edu.pe.sacramentrecords.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vallegrande.edu.pe.sacramentrecords.domain.model.SacramentAct;
import vallegrande.edu.pe.sacramentrecords.domain.port.in.SacramentActUseCase;
import vallegrande.edu.pe.sacramentrecords.domain.port.out.SacramentActRepositoryPort;
import vallegrande.edu.pe.sacramentrecords.domain.port.out.SacramentRecordRepositoryPort;

import java.util.UUID;

/** Implementa los casos de uso de actas y coordina la activación del registro asociado. */
@Service
@RequiredArgsConstructor
public class SacramentActService implements SacramentActUseCase {

    private final SacramentActRepositoryPort repositoryPort;
    private final SacramentRecordRepositoryPort recordRepositoryPort;

    @Override
    public Flux<SacramentAct> listarTodos() {
        return repositoryPort.findAll();
    }

    @Override
    public Flux<SacramentAct> listarPorTenant(Integer tenantId) {
        return repositoryPort.findByTenantId(tenantId);
    }

    @Override
    public Mono<SacramentAct> buscarPorRegistro(UUID recordId) {
        return repositoryPort.findByRecordId(recordId);
    }

    @Override
    public Mono<SacramentAct> buscarPorId(UUID id) {
        return repositoryPort.findById(id);
    }

    @Override
    public Mono<SacramentAct> guardar(SacramentAct act) {
        return repositoryPort.save(act).flatMap(this::activarRegistro);
    }

    @Override
    public Mono<SacramentAct> actualizar(UUID id, SacramentAct act) {
        return repositoryPort.findById(id)
                .flatMap(existing -> {
                    existing.setTenantId(act.getTenantId());
                    existing.setRecordId(act.getRecordId());
                    existing.setFileUrl(act.getFileUrl());
                    return repositoryPort.save(existing);
                })
                .flatMap(this::activarRegistro);
    }

    private Mono<SacramentAct> activarRegistro(SacramentAct act) {
        if (act.getRecordId() == null) {
            return Mono.just(act);
        }
        return recordRepositoryPort.updateStatus(act.getRecordId(), "A")
                .thenReturn(act);
    }
}
