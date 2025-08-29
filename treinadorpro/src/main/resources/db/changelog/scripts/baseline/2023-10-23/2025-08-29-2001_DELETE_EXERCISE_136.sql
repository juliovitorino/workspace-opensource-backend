--liquibase formatted sql

--changeset julio.vitorino:2025-08-29_09-delete-exercise-136
--comment Delete exercise Rosca Direta com Barra (Barbell Curl)
DELETE FROM exercise WHERE id = 136;

--rollback INSERT INTO exercise (
--rollback   id, name_pt, name_en, name_es, video_url_pt, video_url_en, video_url_es,
--rollback   image_uuid, status, created_at, updated_at, external_id
--rollback ) VALUES (
--rollback   136, 'Rosca Direta com Barra', 'Barbell Curl', 'Curl de Bíceps con Barra',
--rollback   NULL, NULL, NULL,
--rollback   '8750a2e1-8f5f-49c8-be90-c5a9fdb1e7f7', 'A',
--rollback   '2025-05-05 14:48:35.296', '2025-05-05 14:48:35.296',
--rollback   'd3c8f66f-bc08-46b6-9e47-e07eb8cf0734'
--rollback );
