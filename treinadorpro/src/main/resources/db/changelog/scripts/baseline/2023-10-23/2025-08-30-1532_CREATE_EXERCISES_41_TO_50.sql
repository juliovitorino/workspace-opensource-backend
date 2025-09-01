--liquibase formatted sql

--changeset julio.vitorino:2025-08-30-1532_01-insert-exercises-crossfit
--comment Insert default exercises for CrossFit (id=5)
INSERT INTO exercise (id, external_id, image_uuid, name_pt, name_en, name_es) VALUES
(41, gen_random_uuid(), 'a34f2a6b-5b0a-46df-b196-5d1c83a12ed0', 'Snatch', 'Snatch', 'Arranque'),
(42, gen_random_uuid(), 'ec9d2b90-263f-4408-b56b-845dfb798a0c', 'Clean and Jerk', 'Clean and Jerk', 'Dos tiempos'),
(43, gen_random_uuid(), '9b1c44f5-19a9-4e5c-81ad-12c01b91dc6b', 'Thruster', 'Thruster', 'Thruster'),
(44, gen_random_uuid(), 'f072f1b7-7c6c-4e7c-9f40-2f21c4e8410a', 'Wall Ball', 'Wall Ball', 'Lanzamiento a la pared'),
(45, gen_random_uuid(), 'dd630c9e-48cc-459b-bb2a-5c2b0f3bb4a9', 'Box Jump', 'Box Jump', 'Salto al cajón'),
(46, gen_random_uuid(), '4c853201-0e6f-4206-8999-bd69c8243a6f', 'Double Under', 'Double Under', 'Doble salto de cuerda'),
(47, gen_random_uuid(), 'a11c8b63-243f-4d7e-9e43-83b8cfda5b87', 'Toes to Bar', 'Toes to Bar', 'Pies a la barra'),
(48, gen_random_uuid(), '7d63484f-3a50-4cc8-b568-b7c1e5372afc', 'Kipping Pull-up', 'Kipping Pull-up', 'Dominadas con impulso'),
(49, gen_random_uuid(), 'b81e091c-2f13-4403-82ab-51b42f4f4445', 'Handstand Push-up', 'Handstand Push-up', 'Flexión de parada de manos'),
(50, gen_random_uuid(), 'fcbfb94d-cfb1-42bb-a64b-19e6b08cb5b4', 'Overhead Squat', 'Overhead Squat', 'Sentadilla sobre la cabeza')
ON CONFLICT DO NOTHING;

--rollback DELETE FROM exercise WHERE id BETWEEN 41 AND 50;
