--liquibase formatted sql

--changeset julio.vitorino:2025-08-29-1956_01-create-immutable-unaccent
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

--changeset julio.vitorino:2025-08-29-1956_02-add-ux-modality-name-pt-unaccent
--comment Unique index ignoring accents on modality.name_pt
CREATE UNIQUE INDEX IF NOT EXISTS ux_modality_name_pt_unaccent
    ON modality (immutable_unaccent(lower(name_pt)));
--rollback DROP INDEX IF EXISTS ux_modality_name_pt_unaccent;

--changeset julio.vitorino:2025-08-29-1956_03-add-ux-modality-name-es-unaccent
--comment Unique index ignoring accents on modality.name_es
CREATE UNIQUE INDEX IF NOT EXISTS ux_modality_name_es_unaccent
    ON modality (immutable_unaccent(lower(name_es)));
--rollback DROP INDEX IF EXISTS ux_modality_name_es_unaccent;

--changeset julio.vitorino:2025-08-29-1956_04-add-ux-modality-name-en-unaccent
--comment Unique index ignoring accents on modality.name_en
CREATE UNIQUE INDEX IF NOT EXISTS ux_modality_name_en_unaccent
    ON modality (immutable_unaccent(lower(name_en)));
--rollback DROP INDEX IF EXISTS ux_modality_name_en_unaccent;

