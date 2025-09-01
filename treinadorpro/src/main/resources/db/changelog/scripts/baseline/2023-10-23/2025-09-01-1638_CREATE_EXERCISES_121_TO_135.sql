--liquibase formatted sql

--changeset julio.vitorino:2025-09-01-1638_01-insert-exercises-shoulders
--comment Insert default exercises for Musculação (Shoulders)
INSERT INTO exercise (id, external_id, image_uuid, name_pt, name_en, name_es) VALUES
(121, gen_random_uuid(), 'a4dc1c2f-34a3-4a68-bc6c-1597cfa19e56', 'Desenvolvimento com Barra', 'Barbell Overhead Press', 'Press Militar con Barra'),
(122, gen_random_uuid(), '8c174a56-70c3-469b-8f66-7f35b16a5f37', 'Desenvolvimento com Halteres', 'Dumbbell Shoulder Press', 'Press Militar con Mancuernas'),
(123, gen_random_uuid(), 'f3d0a748-d93e-474a-b1a2-2f4aaad6d6e0', 'Desenvolvimento na Máquina', 'Machine Shoulder Press', 'Press Militar en Máquina'),
(124, gen_random_uuid(), '9e9de50e-c1a3-4379-a99c-8c1e623e95d8', 'Elevação Lateral com Halteres', 'Dumbbell Lateral Raise', 'Elevaciones Laterales con Mancuernas'),
(125, gen_random_uuid(), 'dbf89997-b5e0-4a91-8b93-80b582e9d518', 'Elevação Lateral na Polia', 'Cable Lateral Raise', 'Elevaciones Laterales en Polea'),
(126, gen_random_uuid(), 'ab7410b6-d351-40d7-8af1-0934b03b5a1e', 'Elevação Frontal com Halteres', 'Dumbbell Front Raise', 'Elevaciones Frontales con Mancuernas'),
(127, gen_random_uuid(), 'f7f378fa-132a-487c-9ef7-09c7f5ad9ee1', 'Elevação Frontal na Polia', 'Cable Front Raise', 'Elevaciones Frontales en Polea'),
(128, gen_random_uuid(), 'e0c1727c-83e5-4014-b728-259d17877959', 'Crucifixo Inverso com Halteres', 'Dumbbell Reverse Fly', 'Aperturas Invertidas con Mancuernas'),
(129, gen_random_uuid(), 'fbb78d85-04c4-4b55-a3d4-9bb1f47bc40d', 'Crucifixo Inverso na Máquina', 'Reverse Pec Deck', 'Pec Deck Invertido'),
(130, gen_random_uuid(), 'a30e0918-2373-4d19-9126-0a9d9e2ad29f', 'Remada Alta com Barra', 'Barbell Upright Row', 'Remo Vertical con Barra'),
(131, gen_random_uuid(), 'f80ae2e2-75f0-4c76-b4d3-f21c1d6b76b5', 'Remada Alta com Halteres', 'Dumbbell Upright Row', 'Remo Vertical con Mancuernas'),
(132, gen_random_uuid(), '56a13d25-d0b2-4989-962d-18b979bc812e', 'Arnold Press', 'Arnold Press', 'Press Arnold'),
(133, gen_random_uuid(), '47930318-1d5b-4b19-90a0-597bde47e4b9', 'Face Pull na Polia', 'Face Pull', 'Face Pull en Polea'),
(134, gen_random_uuid(), 'c03091d2-5687-4c5d-aed9-b2553e7f4b36', 'Desenvolvimento com Halteres Sentado', 'Seated Dumbbell Shoulder Press', 'Press Militar Sentado con Mancuernas'),
(135, gen_random_uuid(), '06ee96d6-80ae-4b1f-89c5-861bc0a38b6b', 'Elevação Lateral Unilateral na Polia', 'Single-Arm Cable Lateral Raise', 'Elevación Lateral Unilateral en Polea')
ON CONFLICT DO NOTHING;

--rollback DELETE FROM exercise WHERE id BETWEEN 121 AND 135;
