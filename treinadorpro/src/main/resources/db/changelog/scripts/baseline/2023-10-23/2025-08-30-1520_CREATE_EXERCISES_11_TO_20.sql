--liquibase formatted sql

--changeset julio.vitorino:2025-08-30_01-insert-exercises-funcional-11-20
--comment Insert default exercises for Treinamento Funcional (id=2)
INSERT INTO exercise (id, external_id, image_uuid, name_pt, name_en, name_es) VALUES
(11, gen_random_uuid(), '6a8a7fd7-3ef9-41d4-b6f4-79c64e77d81c', 'Burpee', 'Burpee', 'Burpee'),
(12, gen_random_uuid(), 'df33a170-2d3b-4e77-9c3a-d42bc2db6a09', 'Agachamento com kettlebell', 'Goblet Squat', 'Sentadilla con kettlebell'),
(13, gen_random_uuid(), 'f1a28355-4061-4a77-964c-f6dc838c948a', 'Swing com kettlebell', 'Kettlebell Swing', 'Balanceo con kettlebell'),
(14, gen_random_uuid(), '0e03204c-6e85-49d7-bf4d-f0ff2bb14207', 'Prancha frontal', 'Plank', 'Plancha'),
(15, gen_random_uuid(), 'b9fbe5c6-4088-4f7d-9d14-15b410bf92f5', 'Saltos no caixote', 'Box Jump', 'Saltos al cajón'),
(16, gen_random_uuid(), '09de63a0-6b59-4486-9bb4-6e497cb3d0ec', 'Avanço com halteres', 'Dumbbell Lunge', 'Zancadas con mancuernas'),
(17, gen_random_uuid(), 'b815109d-63db-4a1f-aba4-4632f78db62f', 'Battle rope', 'Battle Rope', 'Cuerdas de combate'),
(18, gen_random_uuid(), 'c53cf52f-9af9-4621-bc36-3b1df92e391c', 'Escada de agilidade', 'Agility Ladder', 'Escalera de agilidad'),
(19, gen_random_uuid(), 'e585b41c-5c61-4d80-a50c-2ab64e5f69f4', 'Corrida em zigue-zague', 'Zigzag Run', 'Carrera en zigzag'),
(20, gen_random_uuid(), '27e9c34e-9c3a-49cb-9b42-17c02d680dd2', 'Prancha lateral', 'Side Plank', 'Plancha lateral')
ON CONFLICT DO NOTHING;

--rollback DELETE FROM exercise WHERE id BETWEEN 11 AND 20;
