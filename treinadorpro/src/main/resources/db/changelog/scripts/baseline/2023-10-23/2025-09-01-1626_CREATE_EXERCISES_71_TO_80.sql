--liquibase formatted sql

--changeset julio.vitorino:2025-08-29_17-insert-exercises-calves
--comment Insert default exercises for Musculação (Panturrilhas / Calves)
INSERT INTO exercise (id, external_id, image_uuid, name_pt, name_en, name_es) VALUES
(71, gen_random_uuid(), '2c395c53-2e3e-4b9a-8fa3-7c059733c6ef', 'Elevação de Gêmeos em Pé', 'Standing Calf Raise', 'Elevación de Gemelos de Pie'),
(72, gen_random_uuid(), 'b67d1566-bfaa-405d-9a8f-98606726f60d', 'Elevação de Gêmeos Sentado', 'Seated Calf Raise', 'Elevación de Gemelos Sentado'),
(73, gen_random_uuid(), '9a450303-ec86-40db-9c4d-93b7d9d07cc0', 'Elevação de Gêmeos na Leg Press', 'Calf Press on Leg Press', 'Elevación de Gemelos en Prensa de Piernas'),
(74, gen_random_uuid(), '0a388ff2-15b6-47e9-bc06-14e5235a0a2c', 'Elevação de Gêmeos Unilateral', 'Single-Leg Standing Calf Raise', 'Elevación de Gemelos a Una Pierna'),
(75, gen_random_uuid(), 'c55d4e18-1f7b-4ffb-a5de-05829d0030c5', 'Elevação de Gêmeos no Smith', 'Smith Machine Calf Raise', 'Elevación de Gemelos en Máquina Smith'),
(76, gen_random_uuid(), '96b9f487-2cb0-4c11-937b-6a2a517ef43a', 'Saltos na Corda', 'Jump Rope', 'Saltar la Cuerda'),
(77, gen_random_uuid(), '15508a8b-f1eb-4de5-9cfb-973c54721e12', 'Elevação de Gêmeos na Plataforma', 'Calf Raise on Step Platform', 'Elevación de Gemelos en Plataforma'),
(78, gen_random_uuid(), '1c1ed1f9-29ee-42d6-9fb7-0380102cd6a3', 'Donkey Calf Raise', 'Donkey Calf Raise', 'Elevación de Gemelos Tipo Burro'),
(79, gen_random_uuid(), '4b9c21de-25b2-4b66-b871-185432ea9b2f', 'Elevação de Gêmeos com Halteres', 'Dumbbell Standing Calf Raise', 'Elevación de Gemelos con Mancuernas'),
(80, gen_random_uuid(), '3f943e8f-1c4a-40df-93d5-172d441e9406', 'Elevação de Gêmeos no Hack Machine', 'Hack Machine Calf Raise', 'Elevación de Gemelos en Máquina Hack')
ON CONFLICT DO NOTHING;

--rollback DELETE FROM exercise WHERE id BETWEEN 71 AND 80;
