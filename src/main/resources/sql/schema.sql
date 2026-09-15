-- RESET DEL MICROSERVICIO TRANSACCIONAL DE REGISTROS SACRAMENTALES

DROP TABLE IF EXISTS sacrament_acts CASCADE;
DROP TABLE IF EXISTS sacrament_records CASCADE;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";


-- 1. SACRAMENT_RECORDS
-- Registros sacramentales. sacrament_id referencia al MS maestro vg-ms-sacramentservice.

CREATE TABLE IF NOT EXISTS sacrament_records(

    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),

    tenant_id INTEGER NOT NULL,

    parishioner_name VARCHAR(150) NOT NULL,

    father_name VARCHAR(150),

    mother_name VARCHAR(150),

    sacrament_id UUID NOT NULL,

    godfather_name VARCHAR(150),

    godmother_name VARCHAR(150),

    priest_id INTEGER NOT NULL,

    celebration_date DATE,

    place VARCHAR(150),

    notes TEXT,

    status CHAR(1)
    DEFAULT 'P'
    CHECK(status IN ('P','A','I')),

    created_at TIMESTAMP
    DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP
    DEFAULT CURRENT_TIMESTAMP

);


-- 2. SACRAMENT_ACTS
-- Enlace al acta generada desde un registro sacramental.

CREATE TABLE IF NOT EXISTS sacrament_acts(

    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),

    tenant_id INTEGER NOT NULL,

    record_id UUID UNIQUE
    REFERENCES sacrament_records(id)
    ON DELETE CASCADE,

    file_url TEXT NOT NULL,

    created_at TIMESTAMP
    DEFAULT CURRENT_TIMESTAMP

);