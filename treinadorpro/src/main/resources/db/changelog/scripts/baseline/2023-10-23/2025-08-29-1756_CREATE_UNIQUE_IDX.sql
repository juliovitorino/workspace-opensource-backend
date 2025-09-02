--liquibase formatted sql

--changeset julio.vitorino:2025-08-29_01-create-immutable-unaccent
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

--changeset julio.vitorino:2025-08-29_02-add-ux-parameters-keytag-unaccent
--comment Unique index ignoring accents on goal.name_pt
CREATE UNIQUE INDEX IF NOT EXISTS ux_goal_name_pt_unaccent
    ON goal (immutable_unaccent(lower(name_pt)));
--rollback DROP INDEX IF EXISTS ux_goal_name_pt_unaccent;

--changeset julio.vitorino:2025-08-29_023-add-ux-parameters-keytag-unaccent
--comment Unique index ignoring accents on goal.name_es
CREATE UNIQUE INDEX IF NOT EXISTS ux_goal_name_es_unaccent
    ON goal (immutable_unaccent(lower(name_es)));
--rollback DROP INDEX IF EXISTS ux_goal_name_es_unaccent;

--changeset julio.vitorino:2025-08-29_04-add-ux-parameters-keytag-unaccent
--comment Unique index ignoring accents on goal.name_en
CREATE UNIQUE INDEX IF NOT EXISTS ux_goal_name_en_unaccent
    ON goal (immutable_unaccent(lower(name_en)));
--rollback DROP INDEX IF EXISTS ux_goal_name_en_unaccent;

