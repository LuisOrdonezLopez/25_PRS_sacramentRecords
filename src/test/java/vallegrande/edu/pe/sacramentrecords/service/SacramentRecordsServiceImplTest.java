package vallegrande.edu.pe.sacramentrecords.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import vallegrande.edu.pe.sacramentrecords.application.service.SacramentRecordService;
import vallegrande.edu.pe.sacramentrecords.domain.model.SacramentRecord;
import vallegrande.edu.pe.sacramentrecords.domain.port.out.SacramentRecordRepositoryPort;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Pruebas unitarias para SacramentRecordServiceImpl.
 *
 * El nombre del archivo conserva el solicitado por la actividad, pero se
 * prueba la implementacion real SacramentRecordServiceImpl.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("SacramentRecordServiceImpl - Pruebas unitarias")
class SacramentRecordsServiceImplTest {

    @Mock
    private SacramentRecordRepositoryPort sacramentRecordRepository;

    @InjectMocks
    private SacramentRecordService sacramentRecordService;

    private SacramentRecord sampleRecord;
    private UUID sampleRecordId;
    private UUID sampleSacramentId;

    // Configuracion inicial de datos reutilizados por los escenarios.
    @BeforeEach
    void setUp() {
        sampleRecordId = UUID.randomUUID();
        sampleSacramentId = UUID.randomUUID();

        sampleRecord = new SacramentRecord();
        sampleRecord.setId(sampleRecordId);
        sampleRecord.setTenantId(1);
        sampleRecord.setParishionerName("Maria Lopez");
        sampleRecord.setFatherName("Jose Lopez");
        sampleRecord.setMotherName("Ana Perez");
        sampleRecord.setSacramentId(sampleSacramentId);
        sampleRecord.setGodfatherName("Carlos Diaz");
        sampleRecord.setGodmotherName("Lucia Diaz");
        sampleRecord.setPriestId(10);
        sampleRecord.setCelebrationDate(LocalDate.of(2026, 5, 10));
        sampleRecord.setPlace("Parroquia San Jose");
        sampleRecord.setNotes("Registro de prueba");
        sampleRecord.setStatus("A");
    }

    // ESCENARIO 1: REGISTRO EXITOSO
    @Test
    @DisplayName("Escenario 1: Registra correctamente un historial sacramental valido")
    void guardar_conHistorialValido_debeAsignarEstadoPendienteYGuardar() {
        // Arrange
        when(sacramentRecordRepository.save(any(SacramentRecord.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        // Act & Assert
        StepVerifier.create(sacramentRecordService.guardar(sampleRecord))
                .assertNext(savedRecord -> {
                    assertNotNull(savedRecord);
                    assertEquals(sampleRecordId, savedRecord.getId());
                    assertEquals("Maria Lopez", savedRecord.getParishionerName());
                    assertEquals("P", savedRecord.getStatus());
                })
                .verifyComplete();

        verify(sacramentRecordRepository).save(sampleRecord);
    }

    // ESCENARIO 2: DATOS INVALIDOS / RECHAZO DE PERSISTENCIA
    @Test
    @DisplayName("Escenario 2: Propaga la excepcion cuando se rechaza un historial invalido")
    void guardar_conHistorialInvalido_debePropagarExcepcionDelRepositorio() {
        // Arrange
        SacramentRecord invalidRecord = new SacramentRecord();
        invalidRecord.setTenantId(1);
        invalidRecord.setStatus("P");
        when(sacramentRecordRepository.save(invalidRecord))
                .thenReturn(Mono.error(new IllegalArgumentException("Historial invalido")));

        // Act & Assert
        StepVerifier.create(sacramentRecordService.guardar(invalidRecord))
                .expectErrorMatches(error -> error instanceof IllegalArgumentException
                        && error.getMessage().contains("invalido"))
                .verify();

        verify(sacramentRecordRepository).save(invalidRecord);
    }

    // ESCENARIO 3: CONSULTA POR FELIGRES
    @Test
    @DisplayName("Escenario 3: Consulta registros asociados a un feligres existente")
    void listarPorPersona_conFeligresExistente_debeRetornarSusRegistros() {
        // Arrange
        SacramentRecord secondRecord = new SacramentRecord();
        secondRecord.setId(UUID.randomUUID());
        secondRecord.setParishionerName("Maria Lopez");
        secondRecord.setStatus("A");
        when(sacramentRecordRepository.findByParishionerName("Maria Lopez"))
                .thenReturn(Flux.just(sampleRecord, secondRecord));

        // Act & Assert
        StepVerifier.create(sacramentRecordService.listarPorPersona("Maria Lopez"))
                .assertNext(firstRecord -> {
                    assertEquals(sampleRecordId, firstRecord.getId());
                    assertEquals("Maria Lopez", firstRecord.getParishionerName());
                })
                .assertNext(secondFoundRecord -> {
                    assertEquals("Maria Lopez", secondFoundRecord.getParishionerName());
                    assertEquals("A", secondFoundRecord.getStatus());
                })
                .verifyComplete();

        verify(sacramentRecordRepository).findByParishionerName("Maria Lopez");
    }

    // ESCENARIO 4: CONSULTA SIN RESULTADOS
    @Test
    @DisplayName("Escenario 4: Devuelve un flujo vacio si no existen registros del feligres")
    void listarPorPersona_conFeligresSinRegistros_debeCompletarSinResultados() {
        // Arrange
        when(sacramentRecordRepository.findByParishionerName("Feligres inexistente"))
                .thenReturn(Flux.empty());

        // Act & Assert
        StepVerifier.create(sacramentRecordService.listarPorPersona("Feligres inexistente"))
                .verifyComplete();

        verify(sacramentRecordRepository).findByParishionerName("Feligres inexistente");
        verify(sacramentRecordRepository, never()).save(any(SacramentRecord.class));
    }

    // CASO ADICIONAL: ERROR DE CONSULTA
    @Test
    @DisplayName("Caso adicional: Propaga la excepcion cuando falla la consulta")
    void listarPorPersona_conFalloDelRepositorio_debePropagarExcepcion() {
        // Arrange
        when(sacramentRecordRepository.findByParishionerName("Error de consulta"))
                .thenReturn(Flux.error(new IllegalStateException("Error de base de datos")));

        // Act & Assert
        StepVerifier.create(sacramentRecordService.listarPorPersona("Error de consulta"))
                .expectErrorMatches(error -> error instanceof IllegalStateException
                        && error.getMessage().contains("base de datos"))
                .verify();

        verify(sacramentRecordRepository).findByParishionerName("Error de consulta");
    }

    @ParameterizedTest(name = "{0}")
    @CsvSource({
            "Registro recibido como activo, A",
            "Registro recibido como inactivo, I",
            "Registro recibido como pendiente, P"
    })
    @DisplayName("Prueba parametrizada: todo registro nuevo inicia como pendiente")
    void guardar_parametrizado_debeForzarEstadoPendiente(String escenario, String estadoInicial) {
        sampleRecord.setStatus(estadoInicial);
        when(sacramentRecordRepository.save(any(SacramentRecord.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        StepVerifier.create(sacramentRecordService.guardar(sampleRecord))
                .assertNext(savedRecord -> assertEquals("P", savedRecord.getStatus()))
                .verifyComplete();

        verify(sacramentRecordRepository).save(sampleRecord);
    }
}
