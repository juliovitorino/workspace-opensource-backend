--liquibase formatted sql

--changeset julio.vitorino:2025-09-01-1630_01-insert-exercises-adutores-abdutores
--comment Insert default exercises for Musculação (Adutores e Abdutores)
INSERT INTO exercise (id, external_id, image_uuid, name_pt, name_en, name_es) VALUES
(81, gen_random_uuid(), '5f5d2ec5-86c7-4f2b-8e92-1dc8f61f06f0', 'Cadeira Abdutora', 'Seated Hip Abduction', 'Abducción de Cadera Sentado'),
(82, gen_random_uuid(), 'f1e7c1a3-17c0-478c-a1dd-d2b7b40813a5', 'Cadeira Adutora', 'Seated Hip Adduction', 'Aducción de Cadera Sentado'),
(83, gen_random_uuid(), 'a6b046a0-d20f-4d82-9f1e-6d62441a8596', 'Abdução de Quadril em Pé', 'Standing Hip Abduction', 'Abducción de Cadera de Pie'),
(86, gen_random_uuid(), 'e92c0a42-91cc-40dd-8f65-f6f5b6d1a01b', 'Addução de Quadril com Faixa Elástica', 'Resistance Band Hip Adduction', 'Aducción de Cadera con Banda de Resistencia'),
(88, gen_random_uuid(), 'b46fbc55-f1d1-4c3c-b7db-0af18664e4a1', 'Addução de Quadril no Cross Over', 'Cable Hip Adduction', 'Aducción de Cadera en Polea'),
(90, gen_random_uuid(), '0a4f4a72-29a7-4b44-a5a0-b27d68cfb63f', 'Addução de Quadril Deitado', 'Side-Lying Hip Adduction', 'Aducción de Cadera Acostado de Lado')
ON CONFLICT DO NOTHING;

--rollback DELETE FROM exercise WHERE id BETWEEN 81 AND 90;
