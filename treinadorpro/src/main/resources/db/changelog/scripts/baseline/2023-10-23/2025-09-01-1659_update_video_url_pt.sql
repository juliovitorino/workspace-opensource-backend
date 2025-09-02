--liquibase formatted sql

--changeset julio.vitorino:2025-09-01-1659_01-update-exercise-videos
--comment Atualização de URLs de vídeos para alguns exercícios

-- update exercícios com vídeo em português
UPDATE exercise SET video_url_pt = 'https://www.youtube.com/shorts/3QU7jjEgWVI' WHERE id = 7;
UPDATE exercise SET video_url_pt = 'https://www.youtube.com/shorts/g3T7LsEeDWQ' WHERE id = 100;
UPDATE exercise SET video_url_pt = 'https://youtu.be/EZKnjiDXPlY' WHERE id = 91;
UPDATE exercise SET video_url_pt = 'https://youtu.be/EZKnjiDXPlY' WHERE id = 1;
UPDATE exercise SET video_url_pt = 'https://youtu.be/7UoGsmqallc' WHERE id = 104;
UPDATE exercise SET video_url_pt = 'https://www.youtube.com/shorts/Ft6nkjCozf0' WHERE id = 99;
UPDATE exercise SET video_url_pt = 'https://www.youtube.com/shorts/Ft6nkjCozf0' WHERE id = 105;
UPDATE exercise SET video_url_pt = 'https://www.youtube.com/shorts/B9gGcbEdYBQ' WHERE id = 94;

--rollback UPDATE exercise SET video_url_pt = NULL WHERE id IN (7, 100, 91, 1, 104, 99, 105, 94);
