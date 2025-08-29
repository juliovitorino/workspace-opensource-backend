--liquibase formatted sql

--changeset julio.vitorino:2025-08-29_06-delete-exercise-54
--comment Delete exercise Extensão de Pernas (Leg Extension)
DELETE FROM exercise WHERE id = 54;

--rollback INSERT INTO exercise (
--rollback   id, name_pt, name_en, name_es, video_url_pt, video_url_en, video_url_es,
--rollback   image_uuid, status, created_at, updated_at, external_id
--rollback ) VALUES (
--rollback   54, 'Extensão de Pernas', 'Leg Extension', 'Extensión de Piernas',
--rollback   NULL, NULL, NULL,
--rollback   '3a5770e9-46fb-42c7-be0d-1688e7ac1572', 'A',
--rollback   '2025-05-05 14:48:35.296', '2025-05-05 14:48:35.296',
--rollback   'f5e82c2b-5047-470d-9e0a-7fa349354e9b'
--rollback );
