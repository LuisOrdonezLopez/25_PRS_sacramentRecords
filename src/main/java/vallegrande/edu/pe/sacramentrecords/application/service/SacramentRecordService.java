package vallegrande.edu.pe.sacramentrecords.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vallegrande.edu.pe.sacramentrecords.domain.model.SacramentRecord;
import vallegrande.edu.pe.sacramentrecords.domain.port.in.SacramentRecordUseCase;
import vallegrande.edu.pe.sacramentrecords.domain.port.out.SacramentRecordRepositoryPort;

import java.time.LocalDateTime;
import java.util.UUID;

/** Implementa los casos de uso de registros contra el puerto de persistencia. */
@Service
@RequiredArgsConstructor
public class SacramentRecordService implements SacramentRecordUseCase {

    private final SacramentRecordRepositoryPort repositoryPort;

    @Override
    public Flux<SacramentRecord> listarTodos() {
        return repositoryPort.findAll();
    }

    @Override
    public Flux<SacramentRecord> listarPendientes() {
        return repositoryPort.findByStatus("P");
    }

    @Override
    public Flux<SacramentRecord> listarActivos() {
        return repositoryPort.findByStatus("A");
    }

    @Override
    public Flux<SacramentRecord> listarInactivos() {
        return repositoryPort.findByStatus("I");
    }

    @Override
    public Flux<SacramentRecord> listarPorTenant(Integer tenantId) {
        return repositoryPort.findByTenantId(tenantId);
    }

    @Override
    public Flux<SacramentRecord> listarPorPersona(String parishionerName) {
        return repositoryPort.findByParishionerName(parishionerName);
    }

    @Override
    public Flux<SacramentRecord> listarPorSacramento(UUID sacramentId) {
        return repositoryPort.findBySacramentId(sacramentId);
    }

    @Override
    public Flux<SacramentRecord> listarPorSacerdote(Integer priestId) {
        return repositoryPort.findByPriestId(priestId);
    }

    @Override
    public Mono<SacramentRecord> buscarPorId(UUID id) {
        return repositoryPort.findById(id);
    }

    @Override
    public Mono<SacramentRecord> guardar(SacramentRecord record) {
        record.setStatus("P");
        return repositoryPort.save(record);
    }

    @Override
    public Mono<SacramentRecord> actualizar(UUID id, SacramentRecord record) {
        return repositoryPort.findById(id)
                .flatMap(existing -> {
                    existing.setTenantId(record.getTenantId());
                    existing.setParishionerName(record.getParishionerName());
                    existing.setFatherName(record.getFatherName());
                    existing.setMotherName(record.getMotherName());
                    existing.setSacramentId(record.getSacramentId());
                    existing.setGodfatherName(record.getGodfatherName());
                    existing.setGodmotherName(record.getGodmotherName());
                    existing.setPriestId(record.getPriestId());
                    existing.setCelebrationDate(record.getCelebrationDate());
                    existing.setPlace(record.getPlace());
                    existing.setNotes(record.getNotes());
                    existing.setStatus(record.getStatus());
                    existing.setUpdatedAt(LocalDateTime.now());
                    return repositoryPort.save(existing);
                });
    }

    @Override
    public Mono<SacramentRecord> eliminar(UUID id) {
        return repositoryPort.updateStatus(id, "I");
    }

    @Override
    public Mono<SacramentRecord> restaurar(UUID id) {
        return repositoryPort.updateStatus(id, "A");
    }
}
