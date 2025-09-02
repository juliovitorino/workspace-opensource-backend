--liquibase formatted sql

--changeset julio.vitorino:2025-09-02-0948_01-update-exercise-videos
--comment Atualização de URLs de vídeos para exercícios adicionais

UPDATE exercise SET video_url_pt = 'https://www.youtube.com/shorts/U9y14C-iw1U' WHERE id = 16;
UPDATE exercise SET video_url_pt = 'https://www.youtube.com/shorts/C95LTQDqH04' WHERE id = 53;
UPDATE exercise SET video_url_pt = 'https://www.youtube.com/shorts/uXsHFdEiMH8' WHERE id = 59;
UPDATE exercise SET video_url_pt = 'https://www.youtube.com/shorts/lsb18p3cOAk' WHERE id = 82;
UPDATE exercise SET video_url_pt = 'https://www.youtube.com/shorts/DcYYwHSuhzs' WHERE id = 52;
UPDATE exercise SET video_url_pt = 'https://www.youtube.com/shorts/iHmv--ag6Qw' WHERE id = 71;
UPDATE exercise SET video_url_pt = 'https://www.youtube.com/shorts/IXg1PQ_5gmw' WHERE id = 61;
UPDATE exercise SET video_url_pt = 'https://www.youtube.com/shorts/GFeP8VDr1Q0' WHERE id = 72;
UPDATE exercise SET video_url_pt = 'https://www.youtube.com/shorts/ZPsUi8zwCQ8' WHERE id = 116;

--rollback UPDATE exercise SET video_url_pt = NULL WHERE id IN (16, 53, 59, 82, 52, 71, 61, 72, 116);
