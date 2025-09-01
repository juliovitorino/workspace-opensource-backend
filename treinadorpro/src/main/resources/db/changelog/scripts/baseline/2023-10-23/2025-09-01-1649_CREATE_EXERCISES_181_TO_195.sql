--liquibase formatted sql

--changeset julio.vitorino:2025-09-01-1649_01-insert-exercises-gluteos
--comment Insert default exercises for Musculação (Glúteos)
INSERT INTO exercise (id, external_id, image_uuid, name_pt, name_en, name_es) VALUES
(181, gen_random_uuid(), '0aeebf87-1f2c-4d3a-8c41-74e83999a8e0', 'Elevação de Quadril com Barra (Hip Thrust)', 'Barbell Hip Thrust', 'Elevación de Cadera con Barra (Hip Thrust)'),
(182, gen_random_uuid(), 'f5a55b5c-54f1-44f0-92e3-b54fdb5733d4', 'Ponte de Glúteo no Solo', 'Glute Bridge', 'Puente de Glúteos en el Suelo'),
(183, gen_random_uuid(), 'f76c73c4-9d3c-4d79-a319-2c62a71b46e5', 'Agachamento Sumô', 'Sumo Squat', 'Sentadilla Sumo'),
(184, gen_random_uuid(), '4e11a62a-65e0-44bb-b0b1-f19e1245876e', 'Agachamento Búlgaro', 'Bulgarian Split Squat', 'Sentadilla Búlgara'),
(185, gen_random_uuid(), 'b0836ac5-7b70-41ed-b3cc-3cb69cb9c4c6', 'Deadlift Romeno com Halteres', 'Dumbbell Romanian Deadlift', 'Peso Muerto Rumano con Mancuernas'),
(186, gen_random_uuid(), '9c302a72-2a67-4e20-a7f4-47a95ab34e19', 'Levantamento Terra com Barra', 'Barbell Deadlift', 'Peso Muerto con Barra'),
(187, gen_random_uuid(), 'a71a9d65-54a0-4c02-9318-590e30dcf647', 'Kickback na Polia', 'Cable Glute Kickback', 'Patada de Glúteo en Polea'),
(188, gen_random_uuid(), 'c18b167b-7e82-45a2-a6ff-0ed7d2ad8d85', 'Kickback com Faixa Elástica', 'Resistance Band Glute Kickback', 'Patada de Glúteo con Banda de Resistencia'),
(189, gen_random_uuid(), '7c7ad8d5-8b2e-48f4-8075-f8a1fce29f3b', 'Abdução de Quadril em Pé', 'Standing Hip Abduction', 'Abducción de Cadera de Pie'),
(190, gen_random_uuid(), 'e90d81c5-5a4a-47b4-9c87-04783e4b11d1', 'Abdução de Quadril Deitado', 'Side-Lying Hip Abduction', 'Abducción de Cadera Acostado'),
(191, gen_random_uuid(), 'dc252ace-5670-4e26-8e19-cc567c1b7933', 'Cadeira Abdutora', 'Seated Hip Abduction Machine', 'Máquina de Abducción de Cadera'),
(192, gen_random_uuid(), 'ab2056a2-60e5-46a1-83d8-87cfeb87c2e2', 'Step-up no Banco', 'Step-up onto Bench', 'Subida al Banco'),
(193, gen_random_uuid(), 'dd7b1671-79f9-4a69-b62b-2c761f93912c', 'Agachamento no Smith', 'Smith Machine Squat', 'Sentadilla en Máquina Smith'),
(194, gen_random_uuid(), 'c6ac3a86-3e12-4d95-b67d-25b79883681a', 'Glute Ham Raise', 'Glute Ham Raise', 'Elevación de Glúteos e Isquiotibiales'),
(195, gen_random_uuid(), '8c22b246-528c-456d-bf21-2d4a31fdc7f1', 'Frog Pump', 'Frog Pump', 'Frog Pump para Glúteos')
ON CONFLICT DO NOTHING;

--rollback DELETE FROM exercise WHERE id BETWEEN 181 AND 195;
