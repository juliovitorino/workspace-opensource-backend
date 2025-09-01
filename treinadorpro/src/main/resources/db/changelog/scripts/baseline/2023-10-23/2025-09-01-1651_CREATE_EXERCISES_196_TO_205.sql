--liquibase formatted sql

--changeset julio.vitorino:2025-09-01-1651_01-insert-exercises-forearms
--comment Insert default exercises for Musculação (Antebraço / Forearms)
INSERT INTO exercise (id, external_id, image_uuid, name_pt, name_en, name_es) VALUES
(196, gen_random_uuid(), 'f6bc720f-6ebd-4370-a62c-07e502fe5a1e', 'Rosca de Punho com Barra', 'Barbell Wrist Curl', 'Curl de Muñeca con Barra'),
(197, gen_random_uuid(), '6b36ec15-53af-44db-9c93-1eab6d0a94f1', 'Rosca de Punho Invertida com Barra', 'Reverse Barbell Wrist Curl', 'Curl Inverso de Muñeca con Barra'),
(198, gen_random_uuid(), 'd97a6f64-cc7a-4a51-9327-6f62c9e29ee3', 'Rosca de Punho com Halteres', 'Dumbbell Wrist Curl', 'Curl de Muñeca con Mancuernas'),
(199, gen_random_uuid(), '59f5f8b6-44b1-4f71-9ed8-1584c7190633', 'Rosca de Punho Invertida com Halteres', 'Reverse Dumbbell Wrist Curl', 'Curl Inverso de Muñeca con Mancuernas'),
(200, gen_random_uuid(), '6b44d926-7c4d-4e45-bc2b-8011080b64ab', 'Pronação de Punho com Halteres', 'Dumbbell Wrist Pronation', 'Pronación de Muñeca con Mancuernas'),
(201, gen_random_uuid(), 'ffce41d2-4364-495f-b01f-5a190739ea0d', 'Supinação de Punho com Halteres', 'Dumbbell Wrist Supination', 'Supinación de Muñeca con Mancuernas'),
(202, gen_random_uuid(), 'df42c503-9db9-4151-9dbd-56d474cfb54d', 'Rosca Martelo com Halteres', 'Hammer Curl', 'Curl Martillo con Mancuernas'),
(203, gen_random_uuid(), '42c194b3-d19d-4d09-bc20-5b4c8f81e95a', 'Rosca Inversa na Barra W', 'EZ Bar Reverse Curl', 'Curl Inverso con Barra EZ'),
(204, gen_random_uuid(), 'e0a6bb9d-4233-4c6f-bb18-10b1a7391c44', 'Farmer’s Walk', 'Farmer’s Walk', 'Caminata del Granjero'),
(205, gen_random_uuid(), 'dd1caa37-5d69-4a7c-a82b-0eeb0c2f4684', 'Dead Hang na Barra Fixa', 'Dead Hang', 'Colgado Muerto en Barra Fija')
ON CONFLICT DO NOTHING;

--rollback DELETE FROM exercise WHERE id BETWEEN 196 AND 205;
