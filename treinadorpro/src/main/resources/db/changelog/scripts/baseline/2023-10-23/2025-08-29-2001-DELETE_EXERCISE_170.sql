--liquibase formatted sql

--changeset julio.vitorino:2025-08-29_03-delete-exercise-170
--comment Delete exercise Prancha Lateral (Side Plank)
DELETE FROM exercise WHERE id = 170;

--rollback INSERT INTO exercise (
--rollback   id, name_pt, name_en, name_es, video_url_pt, video_url_en, video_url_es,
--rollback   image_uuid, status, created_at, updated_at, external_id
--rollback ) VALUES (
--rollback   170, 'Prancha Lateral', 'Side Plank', 'Plancha Lateral',
--rollback   NULL, NULL, NULL,
--rollback   'fbec9a6b-b27b-40cc-8d57-ac00e6fc7eef', 'A',
--rollback   '2025-05-05 14:48:35.296', '2025-05-05 14:48:35.296',
--rollback   '669ee7bf-6248-4a84-8865-07929507e639'
--rollback );
