--liquibase formatted sql

--changeset julio.vitorino:2025-08-29_01-populate_version_table

INSERT INTO version (external_id, version, platform, status, created_at, updated_at)
VALUES ('550e8400-e29b-41d4-a716-446655440000', '1.0.0', 'APP', 'A', NOW(), NOW()) ON CONFLICT DO NOTHING;


--rollback delete from version where external_id = '550e8400-e29b-41d4-a716-446655440000';
