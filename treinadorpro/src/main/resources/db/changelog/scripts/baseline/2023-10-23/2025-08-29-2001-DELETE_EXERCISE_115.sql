--liquibase formatted sql

--changeset julio.vitorino:2025-08-29-200101_01-delete-exercise-115
--comment Delete exercise Levantamento Terra (Deadlift)
DELETE FROM exercise WHERE id = 115;
--rollback INSERT INTO exercise (id, name_pt, name_en, name_es, video_url_pt, video_url_en, video_url_es, image_uuid, status, created_at, updated_at, external_id)
--rollback VALUES (115, 'Levantamento Terra', 'Deadlift', 'Peso Muerto', NULL, NULL, NULL, '4a75d81a-efbf-4fb7-a314-5ded197f5795', 'A', '2025-05-05 14:48:35.296', '2025-05-05 14:48:35.296', 'd12c09fe-d941-4670-b1b9-517472a37be3');
