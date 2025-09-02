--liquibase formatted sql

--changeset julio.vitorino:2025-09-01-1647_01-insert-exercises-abs
--comment Insert default exercises for Musculação (Abdômen / Core)
INSERT INTO exercise (id,external_id, image_uuid, name_pt, name_en, name_es) VALUES
(166, gen_random_uuid(), '5e2dbf88-74ad-4b12-9edc-f27a4e3a1a69', 'Abdominal Supra no Solo', 'Floor Crunch', 'Abdominal Superior en el Suelo'),
(167, gen_random_uuid(), '6e5a9c0f-b3b2-44ed-a9a0-0e06de2dfb6f', 'Abdominal Infra no Solo', 'Reverse Crunch', 'Abdominal Inferior en el Suelo'),
(168, gen_random_uuid(), '1a8fbd3f-9e92-4c70-85d2-b4ef2e7c53c0', 'Abdominal Oblíquo no Solo', 'Oblique Crunch', 'Abdominal Oblicuo en el Suelo'),
(169, gen_random_uuid(), '4df2f412-0f21-42f6-ae61-6b3a8c5de14f', 'Prancha Frontal', 'Front Plank', 'Plancha Frontal'),
(170, gen_random_uuid(), 'c19b7c1c-2823-4623-a9e2-9f6a208dca90', 'Prancha Lateral', 'Side Plank', 'Plancha Lateral'),
(171, gen_random_uuid(), 'f3c12fc6-25d9-44c4-8c0f-d8fdf238de56', 'Abdominal Bicicleta', 'Bicycle Crunch', 'Abdominal Bicicleta'),
(172, gen_random_uuid(), '7f66db6f-d267-4b04-b9b6-879bb54aeb35', 'Elevação de Pernas no Solo', 'Lying Leg Raise', 'Elevación de Piernas Acostado'),
(173, gen_random_uuid(), 'e427b6e8-3c68-41b6-9df7-7267e88f41c6', 'Elevação de Pernas na Barra Fixa', 'Hanging Leg Raise', 'Elevación de Piernas Colgado'),
(174, gen_random_uuid(), 'cd36d36b-80b6-41ee-bc61-0a60a3f62f77', 'Abdominal Canivete', 'V-Up', 'Abdominal en V'),
(175, gen_random_uuid(), 'c1582dc5-8e9a-4d0d-9e94-60a0d329a8a1', 'Abdominal com Corda na Polia', 'Cable Rope Crunch', 'Crunch de Abdominales en Polea con Cuerda'),
(176, gen_random_uuid(), 'd9d0e4a2-c0e5-46c0-8249-5f51b9e2c1a0', 'Abdominal na Máquina', 'Machine Crunch', 'Crunch de Abdominales en Máquina'),
(177, gen_random_uuid(), 'd759a31d-3d8f-43f3-b2fc-7ab6d62e73f7', 'Abdominal na Bola Suíça', 'Swiss Ball Crunch', 'Crunch de Abdominales en Balón Suizo'),
(178, gen_random_uuid(), '05f2587f-c85d-41fc-80cd-d7e2c4b09a76', 'Toques nos Calcanhares', 'Heel Taps', 'Toques de Talón'),
(179, gen_random_uuid(), 'a7465a9d-b99a-4e83-9f59-c5cce1c8fc12', 'Abdominal Reverso com Bola', 'Reverse Crunch with Ball', 'Abdominal Inverso con Balón'),
(180, gen_random_uuid(), 'd78a7d59-80fb-4e7e-a6d9-27d41f2e8125', 'Mountain Climbers', 'Mountain Climbers', 'Escaladores')
ON CONFLICT DO NOTHING;

--rollback DELETE FROM exercise WHERE id BETWEEN 166 AND 180;
