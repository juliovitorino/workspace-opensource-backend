--liquibase formatted sql

--changeset julio.vitorino:2025-08-29_01-create-immutable-unaccent_workgroup
--comment Define immutable wrapper for unaccent
CREATE EXTENSION IF NOT EXISTS unaccent;

CREATE OR REPLACE FUNCTION immutable_unaccent(text)
RETURNS text
LANGUAGE sql
IMMUTABLE
AS '
  SELECT unaccent(''public.unaccent'', lower($1));
';
--rollback DROP FUNCTION IF EXISTS immutable_unaccent(text);

--changeset julio.vitorino:2025-08-29_02-add-ux-workgroup-name_pt-unaccent
--comment Unique index ignoring accents on goal.name_pt
CREATE UNIQUE INDEX IF NOT EXISTS ux_work_group_name_pt_unaccent
    ON work_group (immutable_unaccent(lower(name_pt)));
--rollback DROP INDEX IF EXISTS ux_work_group_name_pt_unaccent;

--changeset julio.vitorino:2025-08-29_023-add-ux-workgroup-name_pt-unaccent
--comment Unique index ignoring accents on goal.name_es
CREATE UNIQUE INDEX IF NOT EXISTS ux_work_group_name_es_unaccent
    ON work_group (immutable_unaccent(lower(name_es)));
--rollback DROP INDEX IF EXISTS ux_work_group_name_es_unaccent;

--changeset julio.vitorino:2025-08-29_04-add-ux-workgroup-name_pt-unaccent
--comment Unique index ignoring accents on goal.name_en
CREATE UNIQUE INDEX IF NOT EXISTS ux_work_group_name_en_unaccent
    ON work_group (immutable_unaccent(lower(name_en)));
--rollback DROP INDEX IF EXISTS ux_work_group_name_en_unaccent;

