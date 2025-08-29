--liquibase formatted sql

--changeset julio.vitorino:2025-08-29_01-insert-goals
--comment Insert default goals
INSERT INTO goal (id, name_pt, name_en, name_es, status)
VALUES
(1, 'Hipertrofia', 'Hypertrophy', 'Hipertrofia', 'A'),
(2, 'Perda de Peso', 'Weight Loss', 'Pérdida de Peso', 'A'),
(3, 'Emagrecimento', 'Slimming', 'Adelgazamiento', 'A'),
(4, 'Definição Muscular', 'Muscle Definition', 'Definición Muscular', 'A'),
(5, 'Condicionamento Físico', 'Physical Conditioning', 'Condicionamiento Físico', 'A'),
(6, 'Ganho de Força', 'Strength Gain', 'Ganancia de Fuerza', 'A'),
(7, 'Resistência Cardiorrespiratória', 'Cardiorespiratory Endurance', 'Resistencia Cardiorrespiratoria', 'A'),
(8, 'Flexibilidade', 'Flexibility', 'Flexibilidad', 'A'),
(9, 'Melhora da Mobilidade', 'Mobility Improvement', 'Mejora de la Movilidad', 'A'),
(10, 'Reabilitação Física', 'Physical Rehabilitation', 'Rehabilitación Física', 'A'),
(11, 'Prevenção de Lesões', 'Injury Prevention', 'Prevención de Lesiones', 'A'),
(12, 'Manutenção da Saúde', 'Health Maintenance', 'Mantenimiento de la Salud', 'A'),
(13, 'Preparação para Corridas', 'Race Preparation', 'Preparación para Carreras', 'A'),
(14, 'Aumento de Energia', 'Energy Boost', 'Aumento de Energía', 'A') ON CONFLICT DO NOTHING;
--rollback DELETE FROM goal WHERE id IN (1,2,3,4,5,6,7,8,9,10,11,12,13,14);
