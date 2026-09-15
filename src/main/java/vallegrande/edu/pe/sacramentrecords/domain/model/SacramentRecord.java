package vallegrande.edu.pe.sacramentrecords.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/** Modelo de dominio puro, sin anotaciones de Spring Data. */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SacramentRecord {

    private UUID id;
    private Integer tenantId;
    private String parishionerName;
    private String fatherName;
    private String motherName;
    private UUID sacramentId;
    private String godfatherName;
    private String godmotherName;
    private Integer priestId;
    private LocalDate celebrationDate;
    private String place;
    private String notes;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
