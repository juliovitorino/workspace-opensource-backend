--liquibase formatted sql

--changeset julio.vitorino:2025-09-02-1012_01-update-exercise-videos
--comment Atualização de URLs de vídeos para exercícios adicionais

UPDATE exercise SET video_url_pt = 'https://www.youtube.com/shorts/T46yKiz8laY' WHERE id = 8;

--rollback UPDATE exercise SET video_url_pt = NULL WHERE id IN (8);
