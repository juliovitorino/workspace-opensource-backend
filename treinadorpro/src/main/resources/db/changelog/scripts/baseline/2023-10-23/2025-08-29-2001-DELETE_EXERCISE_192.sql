--liquibase formatted sql

--changeset julio.vitorino:2025-08-29-2001_01-delete-exercise-192
--comment Delete exercise Step-up no Banco (Step-up onto Bench)
DELETE FROM exercise WHERE id = 192;
--rollback INSERT INTO exercise (
--rollback   id, name_pt, name_en, name_es, video_url_pt, video_url_en, video_url_es,
--rollback   image_uuid, status, created_at, updated_at, external_id
--rollback ) VALUES (
--rollback   192, 'Step-up no Banco', 'Step-up onto Bench', 'Subida al Banco',
--rollback   NULL, NULL, NULL,
--rollback   '4264144b-e2cc-4a04-8c7f-f266e2829061', 'A',
--rollback   '2025-05-05 14:48:35.296', '2025-05-05 14:48:35.296',
--rollback   'a9a81f34-b7ba-430c-a973-98593cf0d759'
--rollback );
