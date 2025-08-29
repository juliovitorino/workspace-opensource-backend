--liquibase formatted sql

--changeset julio.vitorino:2025-08-29-1816_01-insert-work-groups
--comment Insert default work groups
INSERT INTO work_group (id, name_pt, name_en, name_es, status)
VALUES
(1, 'Peitoral', 'Chest', 'Pectorales', 'A'),
(2, 'Costas', 'Back', 'Espalda', 'A'),
(3, 'Ombros', 'Shoulders', 'Hombros', 'A'),
(5, 'Bíceps', 'Biceps', 'Bíceps', 'A'),
(6, 'Tríceps', 'Triceps', 'Tríceps', 'A'),
(7, 'Abdômen', 'Abdomen', 'Abdomen', 'A'),
(8, 'Glúteos', 'Glutes', 'Glúteos', 'A'),
(9, 'Quadríceps', 'Quadriceps', 'Cuádriceps', 'A'),
(10, 'Posterior de Coxa', 'Hamstrings', 'Isquiotibiales', 'A'),
(11, 'Panturrilhas', 'Calves', 'Pantorrillas', 'A'),
(12, 'Antebraço', 'Forearm', 'Antebrazo', 'A'),
(13, 'Trapézio', 'Trapezius', 'Trapecio', 'A'),
(14, 'Adutores', 'Adductors', 'Aductores', 'A'),
(15, 'Abdutores', 'Abductors', 'Abductores', 'A'),
(16, 'Corpo inteiro', 'Full Body', 'Cuerpo entero', 'A'),
(17, 'Lombar', 'Lower Back', 'Zona Lumbar', 'A'),
(18, 'Pernas', 'Legs', 'Pernas', 'A'),
(19, 'Mente', 'Mindfullness', 'Mente', 'A'),
(20, 'Braço', 'Arms', 'Braços', 'A'),
(21, 'Aquecimento', 'Warm Up', 'Aquecimiento', 'A'),
(22, 'Cardio', 'Cardio', 'Cardio', 'A'),
(23, 'Mobilidade', 'Mobility', 'Mobilidad', 'A'),
(24, 'Core', 'Core', 'Core', 'A'),
(25, 'Pernas & Gluteos', 'Legs & Glutes', 'Pernas & Gluteos', 'A') ON CONFLICT DO NOTHING;
--rollback DELETE FROM work_group WHERE id IN (1,2,3,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25);
