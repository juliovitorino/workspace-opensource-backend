--liquibase formatted sql

--changeset julio.vitorino:2025-08-29-2001_02-delete-duplicate-exercises-name-en
--comment Delete duplicate exercises keeping the lowest id by name_en
DELETE FROM exercise e
USING (
    SELECT MAX(id) AS id_to_delete, name_en
    FROM exercise
    GROUP BY name_en
    HAVING COUNT(*) > 1
) d
WHERE e.id = d.id_to_delete;

--rollback -- Rollback not deterministic, must be handled manually by re-inserting deleted rows
