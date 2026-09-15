package vallegrande.edu.pe.sacramentrecords.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/** Modelo de dominio puro, sin anotaciones de Spring Data. */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SacramentAct {

    private UUID id;
    private Integer tenantId;
    private UUID recordId;
    private String fileUrl;
    private LocalDateTime createdAt;
}
