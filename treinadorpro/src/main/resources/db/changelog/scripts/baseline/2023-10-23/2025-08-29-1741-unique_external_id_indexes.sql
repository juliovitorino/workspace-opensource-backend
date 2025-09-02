--liquibase formatted sql

--changeset julio.vitorino:2025-08-29_01-add-unique-index-account_statement_external_id
--comment Create unique index on account_statement.external_id (idempotent)
CREATE UNIQUE INDEX IF NOT EXISTS ux_account_statement_external_id ON account_statement(external_id);
--rollback DROP INDEX IF EXISTS ux_account_statement_external_id;

--changeset julio.vitorino:2025-08-29_02-add-unique-index-version_external_id
--comment Create unique index on version.external_id (idempotent)
CREATE UNIQUE INDEX IF NOT EXISTS ux_version_external_id ON version(external_id);
--rollback DROP INDEX IF EXISTS ux_version_external_id;