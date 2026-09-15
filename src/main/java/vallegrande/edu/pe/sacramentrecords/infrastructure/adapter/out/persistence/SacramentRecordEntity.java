package vallegrande.edu.pe.sacramentrecords.infrastructure.adapter.out.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/** Entidad R2DBC de registros; separada del modelo de dominio. */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("sacrament_records")
public class SacramentRecordEntity {

    @Id
    private UUID id;

    @Column("tenant_id")
    private Integer tenantId;

    @Column("parishioner_name")
    private String parishionerName;

    @Column("father_name")
    private String fatherName;

    @Column("mother_name")
    private String motherName;

    @Column("sacrament_id")
    private UUID sacramentId;

    @Column("godfather_name")
    private String godfatherName;

    @Column("godmother_name")
    private String godmotherName;

    @Column("priest_id")
    private Integer priestId;

    @Column("celebration_date")
    private LocalDate celebrationDate;

    private String place;
    private String notes;
    private String status;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}
