--liquibase formatted sql

--changeset julio.vitorino:2025-09-01-1636_01-insert-exercises-back
--comment Insert default exercises for Musculação (Back)
INSERT INTO exercise (id, external_id, image_uuid, name_pt, name_en, name_es) VALUES
(106, gen_random_uuid(), 'fd7e07c6-68bb-4b40-bf1f-b8cfd21f93c2', 'Puxada Frontal na Barra Fixa', 'Pull-up', 'Dominadas'),
(107, gen_random_uuid(), 'a1af5f4d-f517-4a94-94d9-4e12c52a9ef8', 'Puxada Frontal na Polia', 'Lat Pulldown', 'Jalón al Pecho'),
(108, gen_random_uuid(), '2a8d248c-4b6a-414c-8e83-8ec39c940cf2', 'Puxada Neutra na Polia', 'Neutral-Grip Lat Pulldown', 'Jalón al Pecho con Agarre Neutro'),
(109, gen_random_uuid(), '7d25cf70-2d25-4b19-9f7a-6a20f729fc86', 'Puxada por Trás da Nuca', 'Behind the Neck Pulldown', 'Jalón Detrás del Cuello'),
(110, gen_random_uuid(), '0f55bb1a-1155-42d7-a883-54c6d7e5a582', 'Remada Curvada com Barra', 'Barbell Bent-Over Row', 'Remo con Barra Inclinado'),
(111, gen_random_uuid(), 'd5b6a82a-7f0e-40b0-8c25-83a5de829e9e', 'Remada Curvada com Halteres', 'Dumbbell Bent-Over Row', 'Remo con Mancuernas Inclinado'),
(112, gen_random_uuid(), 'd382b4c8-9d2b-44d2-8966-158c39dc2c56', 'Remada Unilateral com Halter', 'One-Arm Dumbbell Row', 'Remo Unilateral con Mancuerna'),
(113, gen_random_uuid(), '6e8e6c45-67a8-4b2c-b6a7-64889f8c8d27', 'Remada Baixa na Polia', 'Seated Cable Row', 'Remo en Máquina Sentado'),
(114, gen_random_uuid(), '6cc028a7-2f73-498d-8a83-48216b94b1ec', 'Remada Cavalinho (T-Bar Row)', 'T-Bar Row', 'Remo T-Bar'),
(115, gen_random_uuid(), 'd14b8f9c-7fa0-4aef-8b5d-f9b497c5cb48', 'Levantamento Terra', 'Deadlift', 'Peso Muerto'),
(116, gen_random_uuid(), 'f1f146d6-3a4d-47db-8e1c-0c8e6c4f7cc3', 'Levantamento Terra Romeno', 'Romanian Deadlift', 'Peso Muerto Rumano'),
(117, gen_random_uuid(), '7e2c0a57-1626-4378-9e3c-0c6cfb71cf05', 'Remada na Máquina Hammer', 'Hammer Strength Row', 'Remo en Máquina Hammer'),
(118, gen_random_uuid(), 'cf7bb34b-3d9b-48cd-9b77-fdc9c622ac23', 'Shrug com Barra', 'Barbell Shrug', 'Encogimientos de Trapecio con Barra'),
(119, gen_random_uuid(), '46f31b7a-517c-4785-bd5a-6c632d5f4dbd', 'Shrug com Halteres', 'Dumbbell Shrug', 'Encogimientos de Trapecio con Mancuernas'),
(120, gen_random_uuid(), '99417d53-9c37-46c4-9a8e-d8a41b22f940', 'Extensão Lombar no Banco', 'Back Extension', 'Extensiones de Espalda')
ON CONFLICT DO NOTHING;

--rollback DELETE FROM exercise WHERE id BETWEEN 106 AND 120;
