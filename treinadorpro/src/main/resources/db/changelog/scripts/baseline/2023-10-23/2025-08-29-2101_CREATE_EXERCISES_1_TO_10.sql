--liquibase formatted sql

--changeset julio.vitorino:2025-08-29-2101_10-insert-exercises-musculacao
--comment Insert default exercises for Musculação (id=1)
INSERT INTO exercise (id, image_uuid, name_pt, name_en, name_es) VALUES
(1, 'c776059f-23a7-440f-b9c9-c2772f915ff0','Supino reto', 'Flat Bench Press', 'Press de banca plano'),
(2, '4db7b7d2-c3c0-40bd-8f0e-b68df4c97eb6','Agachamento livre', 'Barbell Squat', 'Sentadilla con barra'),
(3, '210eb1fe-ab3b-4fac-a53a-105dad3dd367','Levantamento terra', 'Deadlift', 'Peso muerto'),
(4, 'd6c16c65-3058-4598-b7cf-7eb33091295c','Desenvolvimento com barra', 'Overhead Press', 'Press militar'),
(5, '4d7cfb0a-f8dd-4f91-ab66-bc060bb50b77','Rosca direta', 'Barbell Curl', 'Curl con barra'),
(6, 'b3c22ba3-e285-461b-bb60-8c5e048a2fad','Tríceps pulley', 'Triceps Pushdown', 'Extensiones en polea'),
(7, 'f6e9cd75-7d3a-4c1b-8a56-235aaf46d3ba','Remada curvada', 'Bent-over Row', 'Remo inclinado'),
(8, '748519e1-17e7-470e-ba67-825989e809f1','Cadeira extensora', 'Leg Extension', 'Extensión de piernas'),
(9, '1f37fdc2-4f4a-40c4-828f-a259a4439a6f','Cadeira flexora', 'Leg Curl', 'Curl femoral'),
(10,'f4366a89-d7e6-4695-be21-608119a7796d','Crucifixo', 'Dumbbell Fly', 'Aperturas con mancuernas')
ON CONFLICT DO NOTHING;

--rollback DELETE FROM exercise WHERE id BETWEEN 1 AND 10;
