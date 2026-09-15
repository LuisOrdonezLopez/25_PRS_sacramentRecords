-- INSERTS DEL MICROSERVICIO TRANSACCIONAL DE REGISTROS SACRAMENTALES

-- tenants:
-- 1 = San Vicente
-- 2 = Parroquia 2
-- 9 = Imperial

-- sacrament_id referencia a los IDs sembrados en vg-ms-sacramentservice:
-- 10000000-0000-0000-0000-000000000001 = Bautizo
-- 10000000-0000-0000-0000-000000000002 = Eucaristía
-- 10000000-0000-0000-0000-000000000003 = Confirmación
-- 10000000-0000-0000-0000-000000000004 = Matrimonio


-- SACRAMENT RECORDS

INSERT INTO sacrament_records(

id,
tenant_id,
parishioner_name,
father_name,
mother_name,
sacrament_id,
godfather_name,
godmother_name,
priest_id,
celebration_date,
place,
notes,
status

)

VALUES

-- BAUTIZO

(
'20000000-0000-0000-0000-000000000001',
9,
'Sebastián Torres Mendoza',
'Ricardo Torres Gómez',
'Patricia Mendoza Ruiz',
'10000000-0000-0000-0000-000000000001',
'Luis Salazar Paredes',
'Carmen Herrera Flores',
101,
'2026-06-14',
'Parroquia Imperial',
'Solicitud aprobada',
'A'
),

-- EUCARISTÍA

(
'20000000-0000-0000-0000-000000000002',
9,
'Valeria Gutiérrez Rojas',
'Fernando Gutiérrez Pérez',
'Ana Rojas Silva',
'10000000-0000-0000-0000-000000000002',
NULL,
'Julia Medina Castro',
102,
'2026-07-18',
'Parroquia Imperial',
'Catequesis culminada',
'A'
),

-- CONFIRMACIÓN

(
'20000000-0000-0000-0000-000000000003',
9,
'Diego Sánchez Vargas',
'Marco Sánchez León',
'Maritza Vargas Flores',
'10000000-0000-0000-0000-000000000003',
'Pedro Castillo Ruiz',
NULL,
103,
'2026-08-24',
'Parroquia Imperial',
'Confirmación grupal',
'A'
),

-- MATRIMONIO

(
'20000000-0000-0000-0000-000000000004',
9,
'Camila Navarro Salas',
'Rubén Navarro Ríos',
'Julia Salas Herrera',
'10000000-0000-0000-0000-000000000004',
'Miguel Paredes Díaz',
'Elena Gómez Torres',
104,
'2026-09-30',
'Parroquia Imperial',
'Matrimonio comunitario',
'A'
);


-- SACRAMENT ACTS

INSERT INTO sacrament_acts
(tenant_id,record_id,file_url)

VALUES

(
9,
'20000000-0000-0000-0000-000000000001',
'https://localhost/actas/bautizo002.pdf'
),

(
9,
'20000000-0000-0000-0000-000000000002',
'https://localhost/actas/eucaristia002.pdf'
),

(
9,
'20000000-0000-0000-0000-000000000003',
'https://localhost/actas/confirmacion002.pdf'
),

(
9,
'20000000-0000-0000-0000-000000000004',
'https://localhost/actas/matrimonio002.pdf'
);