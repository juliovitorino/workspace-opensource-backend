--liquibase formatted sql

--changeset julio.vitorino:2025-09-01-1644_01-insert-exercises-triceps
--comment Insert default exercises for Musculação (Tríceps)
INSERT INTO exercise (id, external_id, image_uuid, name_pt, name_en, name_es) VALUES
(151, gen_random_uuid(), 'b3d76f24-51d4-4bb2-9db2-42238f278e4c', 'Tríceps Testa com Barra', 'Barbell Skullcrusher', 'Extensión de Tríceps con Barra Acostado'),
(152, gen_random_uuid(), 'f22c0d67-9a13-47e2-8c4c-bac94616505f', 'Tríceps Testa com Halteres', 'Dumbbell Skullcrusher', 'Extensión de Tríceps con Mancuernas Acostado'),
(153, gen_random_uuid(), '6b13a84b-0e64-4998-bba3-c0c17c2e1f76', 'Tríceps na Polia com Barra Reta', 'Cable Pushdown with Straight Bar', 'Extensión de Tríceps en Polea con Barra Recta'),
(154, gen_random_uuid(), '67a32812-22b6-4059-8e05-26731eb2b5e2', 'Tríceps na Polia com Corda', 'Cable Rope Pushdown', 'Extensión de Tríceps en Polea con Cuerda'),
(155, gen_random_uuid(), '739cb10a-16db-47d1-9e65-88cfec12e2f0', 'Tríceps na Polia com Barra V', 'Cable Pushdown with V Bar', 'Extensión de Tríceps en Polea con Barra V'),
(156, gen_random_uuid(), '9a4f040d-d089-4b3f-bf78-8b53f4c26a5f', 'Tríceps Coice com Halteres', 'Dumbbell Kickback', 'Patada de Tríceps con Mancuernas'),
(157, gen_random_uuid(), 'c5d5d2f1-7f44-44f4-8126-ec0b5ab94a7f', 'Mergulho entre Bancos', 'Bench Dips', 'Fondos entre Bancos'),
(158, gen_random_uuid(), '0f88eb90-74f8-497b-9fc1-0a5c1c58e4f9', 'Mergulho nas Paralelas', 'Parallel Bar Dips', 'Fondos en Paralelas'),
(159, gen_random_uuid(), '8365ef7f-c29c-44b0-a196-0db7f2c40412', 'Tríceps Francês com Halteres', 'Dumbbell Overhead Triceps Extension', 'Extensión de Tríceps por Encima de la Cabeza con Mancuernas'),
(160, gen_random_uuid(), 'b89d41e3-5a83-4c29-8a85-9a47c531165e', 'Tríceps Francês com Barra', 'Barbell Overhead Triceps Extension', 'Extensión de Tríceps por Encima de la Cabeza con Barra'),
(161, gen_random_uuid(), '8a0f3a9d-9c9c-47ab-9b44-5c721f197c12', 'Tríceps Unilateral na Polia', 'Single-Arm Cable Pushdown', 'Extensión de Tríceps a Una Mano en Polea'),
(162, gen_random_uuid(), '6a6bdf67-88fc-4d47-bf9e-3af82d0f7e3e', 'Tríceps Invertido na Polia', 'Reverse Grip Cable Pushdown', 'Extensión de Tríceps en Polea con Agarre Invertido'),
(163, gen_random_uuid(), 'd26b04dc-75cc-4c4c-b6e6-7f1f9380b28d', 'Tríceps Kickback na Polia', 'Cable Kickback', 'Patada de Tríceps en Polea'),
(164, gen_random_uuid(), '1c8740bb-5b3f-42bb-96a0-7d0c7d334a7d', 'Tríceps Crossbody com Corda', 'Cable Crossbody Triceps Extension', 'Extensión de Tríceps Cruzado en Polea'),
(165, gen_random_uuid(), '83c66c9c-7587-4dc4-8aaf-d4d4f4168c76', 'Tríceps Pullover com Halteres', 'Dumbbell Pullover Triceps', 'Pullover de Tríceps con Mancuernas')
ON CONFLICT DO NOTHING;

--rollback DELETE FROM exercise WHERE id BETWEEN 151 AND 165;
