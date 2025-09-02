--liquibase formatted sql

--changeset julio.vitorino:2025-08-29_08-delete-exercise-207
--comment Delete exercise Encolhimento de Ombros com Halteres (Dumbbell Shrug)
DELETE FROM exercise WHERE id = 207;

--rollback INSERT INTO exercise (
--rollback   id, name_pt, name_en, name_es, video_url_pt, video_url_en, video_url_es,
--rollback   image_uuid, status, created_at, updated_at, external_id
--rollback ) VALUES (
--rollback   207, 'Encolhimento de Ombros com Halteres', 'Dumbbell Shrug', 'Encogimiento de Hombros con Mancuernas',
--rollback   NULL, NULL, NULL,
--rollback   'f232821b-9c6c-4f6e-9ad9-7e7abf519a0e', 'A',
--rollback   '2025-05-05 14:48:35.296', '2025-05-05 14:48:35.296',
--rollback   '6e35d723-9658-4be7-b410-d3081790eb25'
--rollback );
