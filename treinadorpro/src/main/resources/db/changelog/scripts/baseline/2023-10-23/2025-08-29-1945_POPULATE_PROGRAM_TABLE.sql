--liquibase formatted sql

--changeset julio.vitorino:2025-08-29_01-insert-programs
--comment Insert default programs
INSERT INTO program (id, name_pt, name_en, name_es, status) VALUES
(57, 'Treino ABC', 'Workout ABC', 'Entrenamiento ABC', 'A'),
(58, 'Treino ABCD', 'Workout ABCD', 'Entrenamiento ABCD', 'A'),
(59, 'Treino ABCDE', 'Workout ABCDE', 'Entrenamiento ABCDE', 'A') ON CONFLICT DO NOTHING;
--rollback DELETE FROM program WHERE id IN (57,58,59);
