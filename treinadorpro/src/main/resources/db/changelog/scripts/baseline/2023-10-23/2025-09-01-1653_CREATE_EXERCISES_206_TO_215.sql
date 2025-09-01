--liquibase formatted sql

--changeset julio.vitorino:2025-09-01-1653_01-insert-exercises-trapezius
--comment Insert default exercises for Musculação (Trapézio / Trapezius)
INSERT INTO exercise (id, external_id, image_uuid, name_pt, name_en, name_es) VALUES
(206, gen_random_uuid(), '7a0eb994-6232-4ff4-a1b6-3d192d9f08c8', 'Encolhimento de Ombros com Barra', 'Barbell Shrug', 'Encogimiento de Hombros con Barra'),
(207, gen_random_uuid(), 'b84e5975-124c-47a3-8361-3a91a2e2b03a', 'Encolhimento de Ombros com Halteres', 'Dumbbell Shrug', 'Encogimiento de Hombros con Mancuernas'),
(208, gen_random_uuid(), 'f72e9f4d-c52c-47c9-879f-5fc95e671d7d', 'Encolhimento de Ombros na Máquina Smith', 'Smith Machine Shrug', 'Encogimiento de Hombros en Máquina Smith'),
(209, gen_random_uuid(), '6cba81ab-bb12-4aa5-9b8a-26760f8b83ad', 'Encolhimento de Ombros na Barra Atrás do Corpo', 'Behind-the-Back Barbell Shrug', 'Encogimiento de Hombros Detrás de la Espalda'),
(210, gen_random_uuid(), '56fc2536-853b-493c-9ae4-016dc0132b6e', 'Remada Alta com Barra', 'Barbell Upright Row', 'Remo Vertical con Barra'),
(211, gen_random_uuid(), 'ea923fb1-7d8d-4806-8a48-25f06aa3f761', 'Remada Alta com Halteres', 'Dumbbell Upright Row', 'Remo Vertical con Mancuernas'),
(212, gen_random_uuid(), '5b50dd3d-2be6-4058-a1ee-349f8db79ebc', 'Remada Alta na Polia', 'Cable Upright Row', 'Remo Vertical en Polea'),
(213, gen_random_uuid(), '87cc65d2-c88c-4b4d-bc4d-3bfa7cb26816', 'Encolhimento de Ombros Unilateral com Halteres', 'Single-Arm Dumbbell Shrug', 'Encogimiento de Hombros Unilateral con Mancuerna'),
(214, gen_random_uuid(), '365c4f91-3548-4688-84af-349285b9f71a', 'Encolhimento de Ombros com Barra Trap Bar', 'Trap Bar Shrug', 'Encogimiento de Hombros con Barra Trap'),
(215, gen_random_uuid(), 'c45a14d0-134b-405b-a3f2-bb9e54b814e8', 'Prancha com Retração de Escápulas', 'Scapular Retraction Plank', 'Plancha con Retracción de Escápulas')
ON CONFLICT DO NOTHING;

--rollback DELETE FROM exercise WHERE id BETWEEN 206 AND 215;
