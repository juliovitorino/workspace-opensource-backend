--liquibase formatted sql

--changeset julio.vitorino:2025-08-29_19-insert-exercises-chest
--comment Insert default exercises for Musculação (Chest)
INSERT INTO exercise (id, external_id, image_uuid, name_pt, name_en, name_es) VALUES
(91,  gen_random_uuid(), 'b0f6a1f7-27b4-41e4-a5b6-5f173c5721c0', 'Supino Reto com Barra', 'Barbell Bench Press', 'Press de Banca con Barra'),
(92,  gen_random_uuid(), '5ed08f55-90a3-4c3b-9a6d-c1b580f23920', 'Supino Reto com Halteres', 'Dumbbell Bench Press', 'Press de Banca con Mancuernas'),
(93,  gen_random_uuid(), 'c6c1d2c1-b6e6-4b7e-8f2e-52c4653c13e4', 'Supino Inclinado com Barra', 'Barbell Incline Bench Press', 'Press de Banca Inclinado con Barra'),
(94,  gen_random_uuid(), 'a9ad6fd0-1e03-4825-95aa-f13f83e09364', 'Supino Inclinado com Halteres', 'Dumbbell Incline Bench Press', 'Press de Banca Inclinado con Mancuernas'),
(95,  gen_random_uuid(), 'f1a84a36-3e47-4c36-85e5-94f08d4dc4cc', 'Supino Declinado com Barra', 'Barbell Decline Bench Press', 'Press de Banca Declinado con Barra'),
(96,  gen_random_uuid(), '91802232-1fa1-4a52-9bfa-39dfd306f273', 'Supino Declinado com Halteres', 'Dumbbell Decline Bench Press', 'Press de Banca Declinado con Mancuernas'),
(97,  gen_random_uuid(), 'd5cf4f5a-2f40-471f-b0ae-7d88920862a5', 'Crucifixo Reto com Halteres', 'Dumbbell Flat Fly', 'Aperturas Planas con Mancuernas'),
(98,  gen_random_uuid(), 'b7dfd7a0-9c12-44e2-8100-c1dbdcf5a4e1', 'Crucifixo Inclinado com Halteres', 'Dumbbell Incline Fly', 'Aperturas Inclinadas con Mancuernas'),
(99,  gen_random_uuid(), 'e1444829-b9f4-422c-9b1a-276efb49363b', 'Crossover no Cross Over', 'Cable Crossover', 'Cruce de Poleas'),
(100, gen_random_uuid(), 'ae7924c4-7c1c-4197-87a8-79adbe3e26c6', 'Peck Deck', 'Pec Deck Machine', 'Máquina Pec Deck'),
(101, gen_random_uuid(), '28cda5e4-2bb0-4b89-8f9b-6d9930ab82ce', 'Flexão de Braços no Solo', 'Push-up', 'Flexiones de Pecho'),
(102, gen_random_uuid(), 'ea79d02c-6938-4087-84d6-1d891f33df92', 'Flexão de Braços com Pés Elevados', 'Decline Push-up', 'Flexiones Declive'),
(103, gen_random_uuid(), '1d327b6e-8791-44bb-a8e6-9c124c425a26', 'Flexão de Braços com Mãos Elevadas', 'Incline Push-up', 'Flexiones Inclinadas'),
(104, gen_random_uuid(), '39f64c7c-4fa3-420f-90e4-205e4e6d1c3e', 'Crossover Baixo no Cross Over', 'Low to High Cable Crossover', 'Cruce de Poleas de Abajo a Arriba'),
(105, gen_random_uuid(), 'e89dbbe4-92ff-463f-b2b6-4e0ddf52cb87', 'Crossover Alto no Cross Over', 'High to Low Cable Crossover', 'Cruce de Poleas de Arriba a Abajo')
ON CONFLICT DO NOTHING;

--rollback DELETE FROM exercise WHERE id BETWEEN 91 AND 105;
