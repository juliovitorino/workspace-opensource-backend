--liquibase formatted sql

--changeset julio.vitorino:2025-08-29_05-update-exercise-65
--comment Equalize translations for Stiff-Leg Deadlift with Dumbbells
UPDATE exercise
SET name_pt = 'Levantamento Terra Stiff com Halteres',
    name_en = 'Dumbbell Stiff-Leg Deadlift',
    name_es = 'Peso Muerto con Piernas Rígidas con Mancuernas'
WHERE id = 65;

--rollback UPDATE exercise
--rollback SET name_pt = 'Stiff com Halteres',
--rollback     name_en = 'Dumbbell Stiff-Leg Deadlift',
--rollback     name_es = 'Peso Muerto Rumano con Mancuernas'
--rollback WHERE id = 65;
