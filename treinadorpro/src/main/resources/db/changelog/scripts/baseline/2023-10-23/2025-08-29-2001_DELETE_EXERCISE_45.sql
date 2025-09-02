--liquibase formatted sql

--changeset julio.vitorino:2025-08-29_07-delete-exercise-45
--comment Delete exercise Box Jump
DELETE FROM exercise WHERE id = 45;

--rollback INSERT INTO exercise (
--rollback   id, name_pt, name_en, name_es, video_url_pt, video_url_en, video_url_es,
--rollback   image_uuid, status, created_at, updated_at, external_id
--rollback ) VALUES (
--rollback   45, 'Box Jump', 'Box Jump', 'Salto al cajón',
--rollback   NULL, NULL, NULL,
--rollback   'ad4522e0-81cf-43ae-856f-8e49386f7678', 'A',
--rollback   '2025-05-05 14:48:35.296', '2025-05-05 14:48:35.296',
--rollback   'b197dc30-cc56-4464-b717-ea868d745005'
--rollback );
