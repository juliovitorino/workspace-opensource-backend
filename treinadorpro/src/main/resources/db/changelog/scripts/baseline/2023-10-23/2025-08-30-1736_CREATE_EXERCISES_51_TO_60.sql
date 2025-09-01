--liquibase formatted sql

--changeset julio.vitorino:2025-08-30-1736_01-insert-exercises-quadriceps
--comment Insert default exercises for Musculação (Quadríceps)
INSERT INTO exercise (id, external_id, image_uuid, name_pt, name_en, name_es) VALUES
(51, gen_random_uuid(), 'e6ab1f22-70d4-4bbd-a54c-7645e26b152f', 'Agachamento Livre', 'Barbell Squat', 'Sentadilla Libre'),
(52, gen_random_uuid(), '49ad923d-5c72-4e8a-9125-4b4a94e35452', 'Agachamento no Smith', 'Smith Machine Squat', 'Sentadilla en Máquina Smith'),
(53, gen_random_uuid(), 'e03d1f13-b0c0-41fb-bd77-8cc90f3b8b7e', 'Leg Press 45°', '45 Degree Leg Press', 'Prensa de Piernas 45°'),
(54, gen_random_uuid(), '2fb9cb0d-9c31-46f0-8498-65c1ed3a8249', 'Extensão de Pernas', 'Leg Extension', 'Extensión de Piernas'),
(55, gen_random_uuid(), '86b7f4f1-0986-43b3-9e55-40665b28a222', 'Agachamento Frontal', 'Front Squat', 'Sentadilla Frontal'),
(56, gen_random_uuid(), '0c0cf9ff-3a47-44e4-91de-95a5e8a1397f', 'Agachamento Búlgaro', 'Bulgarian Split Squat', 'Sentadilla Búlgara'),
(57, gen_random_uuid(), 'a44ecf63-54f4-4640-a18e-64a0c9c45885', 'Passada com Halteres', 'Dumbbell Walking Lunge', 'Zancada Caminando con Mancuernas'),
(58, gen_random_uuid(), '7a8f1b41-b5f6-4a0e-b250-1d48a4e819e0', 'Step-up no Banco', 'Step-up onto Bench', 'Subida al Banco'),
(59, gen_random_uuid(), 'f4d73f54-f83a-42b2-87af-d6d138fa1e35', 'Agachamento Sumô', 'Sumo Squat', 'Sentadilla Sumo'),
(60, gen_random_uuid(), 'c1dd2f9e-f307-4c63-b523-8c9f189a2391', 'Hack Machine', 'Hack Squat Machine', 'Máquina Hack de Sentadilla')
ON CONFLICT DO NOTHING;


--rollback DELETE FROM exercise WHERE id BETWEEN 51 AND 60;
