package vallegrande.edu.pe.sacramentrecords.infrastructure.adapter.out.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

/** Entidad R2DBC de actas; separada del modelo de dominio. */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("sacrament_acts")
public class SacramentActEntity {

    @Id
    private UUID id;

    @Column("tenant_id")
    private Integer tenantId;

    @Column("record_id")
    private UUID recordId;

    @Column("file_url")
    private String fileUrl;

    @Column("created_at")
    private LocalDateTime createdAt;
}
