CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Inserção de Exercícios para Musculação focus trapezius (id=1)
INSERT INTO exercise (id, image_uuid, name_pt, name_en, name_es) VALUES
(206, gen_random_uuid(), 'Encolhimento de Ombros com Barra', 'Barbell Shrug', 'Encogimiento de Hombros con Barra'),
(207, gen_random_uuid(), 'Encolhimento de Ombros com Halteres', 'Dumbbell Shrug', 'Encogimiento de Hombros con Mancuernas'),
(208, gen_random_uuid(), 'Encolhimento de Ombros na Máquina Smith', 'Smith Machine Shrug', 'Encogimiento de Hombros en Máquina Smith'),
(209, gen_random_uuid(), 'Encolhimento de Ombros na Barra Atrás do Corpo', 'Behind-the-Back Barbell Shrug', 'Encogimiento de Hombros Detrás de la Espalda'),
(210, gen_random_uuid(), 'Remada Alta com Barra', 'Barbell Upright Row', 'Remo Vertical con Barra'),
(211, gen_random_uuid(), 'Remada Alta com Halteres', 'Dumbbell Upright Row', 'Remo Vertical con Mancuernas'),
(212, gen_random_uuid(), 'Remada Alta na Polia', 'Cable Upright Row', 'Remo Vertical en Polea'),
(213, gen_random_uuid(), 'Encolhimento de Ombros Unilateral com Halteres', 'Single-Arm Dumbbell Shrug', 'Encogimiento de Hombros Unilateral con Mancuerna'),
(214, gen_random_uuid(), 'Encolhimento de Ombros com Barra Trap Bar', 'Trap Bar Shrug', 'Encogimiento de Hombros con Barra Trap'),
(215, gen_random_uuid(), 'Prancha com Retração de Escápulas', 'Scapular Retraction Plank', 'Plancha con Retracción de Escápulas');

-- insert relationship between work_group x exercise
-- work_group = 1 (chest)
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES
(1,   1, 'A'),
(1,  10, 'A'),
(1,  91, 'A'),
(1,  92, 'A'),
(1,  93, 'A'),
(1,  94, 'A'),
(1,  95, 'A'),
(1,  96, 'A'),
(1,  97, 'A'),
(1,  98, 'A'),
(1,  99, 'A'),
(1, 100, 'A'),
(1, 101, 'A'),
(1, 102, 'A'),
(1, 103, 'A'),
(1, 104, 'A'),
(1, 105, 'A');

--work_group = 2(back)
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (2, 3, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (2, 7, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (2, 106, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (2, 107, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (2, 108, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (2, 109, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (2, 110, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (2, 111, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (2, 112, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (2, 113, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (2, 114, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (2, 115, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (2, 117, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (2, 120, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (2, 205, 'A');

-- work_group = 3 (shoulders)
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (3, 4, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (3, 121, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (3, 122, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (3, 123, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (3, 124, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (3, 125, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (3, 126, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (3, 127, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (3, 128, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (3, 129, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (3, 130, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (3, 131, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (3, 132, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (3, 133, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (3, 134, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (3, 135, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (3, 206, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (3, 207, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (3, 208, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (3, 209, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (3, 210, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (3, 211, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (3, 212, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (3, 213, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (3, 214, 'A');

-- work_group =5	Bíceps
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (5, 5, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (5, 136, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (5, 137, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (5, 138, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (5, 139, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (5, 140, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (5, 141, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (5, 142, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (5, 143, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (5, 144, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (5, 145, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (5, 146, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (5, 147, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (5, 148, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (5, 149, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (5, 150, 'A');

-- work_group =6	Tríceps
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (6, 6, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (6, 151, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (6, 152, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (6, 153, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (6, 154, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (6, 155, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (6, 156, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (6, 157, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (6, 158, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (6, 159, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (6, 160, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (6, 161, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (6, 162, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (6, 163, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (6, 164, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (6, 165, 'A');

-- work_group =7	Abdômen
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (7, 14, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (7, 20, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (7, 166, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (7, 167, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (7, 168, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (7, 169, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (7, 170, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (7, 171, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (7, 172, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (7, 173, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (7, 174, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (7, 175, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (7, 176, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (7, 177, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (7, 178, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (7, 179, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (7, 180, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (7, 215, 'A');

-- work_group =8	Glúteos
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (8, 181, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (8, 182, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (8, 183, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (8, 184, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (8, 185, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (8, 186, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (8, 187, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (8, 188, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (8, 189, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (8, 190, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (8, 191, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (8, 192, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (8, 193, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (8, 194, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (8, 195, 'A');

-- work_group =9	Quadríceps
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (9, 2, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (9, 8, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (9, 51, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (9, 52, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (9, 53, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (9, 54, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (9, 55, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (9, 56, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (9, 57, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (9, 58, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (9, 59, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (9, 60, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (9, 193, 'A');

-- work_group =10	Posterior de Coxa
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (10, 9, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (10, 61, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (10, 62, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (10, 63, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (10, 64, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (10, 65, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (10, 66, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (10, 67, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (10, 68, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (10, 69, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (10, 70, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (10, 116, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (10, 194, 'A');

-- work_group =11	Panturrilhas
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (11, 71, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (11, 72, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (11, 73, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (11, 74, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (11, 75, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (11, 76, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (11, 77, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (11, 78, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (11, 79, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (11, 80, 'A');

-- work_group =12	Antebraço
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (12, 196, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (12, 197, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (12, 198, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (12, 199, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (12, 200, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (12, 201, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (12, 203, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (12, 204, 'A');

-- work_group =13	Trapézio
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (13, 118, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (13, 119, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (13, 206, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (13, 207, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (13, 208, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (13, 209, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (13, 210, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (13, 211, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (13, 212, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (13, 213, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (13, 214, 'A');

-- work_group =14	Adutores
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (14, 82, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (14, 84, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (14, 86, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (14, 88, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (14, 90, 'A');

-- work_group =15	Abdutores
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (15, 81, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (15, 83, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (15, 85, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (15, 87, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (15, 89, 'A');

-- work_group =16	Corpo inteiro
-- work_group =17	Lombar
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (17, 67, 'A');
INSERT INTO work_group_exercise (work_group_id, exercise_id, status) VALUES (17, 120, 'A');

-- Program Template
-- ABC Training version 1
INSERT INTO program_template (version, modality_id,goal_id, program_id,  work_group_id, exercise_id,  qty_series, qty_reps,   execution, execution_time, rest_time, weight, weight_unit, comments, status, created_at, updated_at, external_id) VALUES
(1, 1, 1, 57,  1,   1, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(1, 1, 1, 57,  1,  10, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(1, 1, 1, 57,  1,  94, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(1, 1, 1, 57,  3, 122, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(1, 1, 1, 57,  3, 131, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(1, 1, 1, 57,  3, 127, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(1, 1, 1, 57,  6,   6, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(1, 1, 1, 57,  6, 151, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(1, 1, 1, 57,  6, 155, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(1, 1, 1, 58,  2, 106, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(1, 1, 1, 58,  2, 107, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(1, 1, 1, 58,  5,   5, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(1, 1, 1, 58,  5, 136, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(1, 1, 1, 58,  5, 137, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(1, 1, 1, 58,  5, 140, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(1, 1, 1, 58, 13, 206, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(1, 1, 1, 58, 13, 207, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(1, 1, 1, 58, 13, 208, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(1, 1, 1, 58, 13, 210, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(1, 1, 1, 59,  9,   8, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(1, 1, 1, 59,  9,   2, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(1, 1, 1, 59,  9,  43, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(1, 1, 1, 59,  9,  54, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(1, 1, 1, 59, 10,   9, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(1, 1, 1, 59, 10,  61, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(1, 1, 1, 59, 10,  63, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(1, 1, 1, 59, 11,  71, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(1, 1, 1, 59, 11,  73, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(1, 1, 1, 59,  8, 181, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(1, 1, 1, 59,  8, 183, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(1, 1, 1, 59,  7,  14, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(1, 1, 1, 59,  7,  20, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(1, 1, 1, 59,  7, 166, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(1, 1, 1, 59,  7, 168, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid())
;

-- ABC Training version 2
INSERT INTO program_template (version, modality_id,goal_id, program_id,  work_group_id, exercise_id, qty_series, qty_reps,  execution, execution_time, rest_time, weight, weight_unit, comments, status, created_at, updated_at, external_id) VALUES
(2, 1, 1, 57,  1,   1, 3,10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(2, 1, 1, 57,  1,  10, 3,10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(2, 1, 1, 57,  1,  94, 3,10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(2, 1, 1, 57,  3, 122, 3,10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(2, 1, 1, 57,  3, 131, 3,10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(2, 1, 1, 57,  3, 127, 3,10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(2, 1, 1, 57,  6,   6, 3,10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(2, 1, 1, 57,  6, 151, 3,10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(2, 1, 1, 57,  6, 155, 3,10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(2, 1, 1, 58,  2, 106, 3,10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(2, 1, 1, 58,  2, 107, 3,10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(2, 1, 1, 58,  5,   5, 3,10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(2, 1, 1, 58,  5, 136, 3,10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(2, 1, 1, 58,  5, 137, 3,10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(2, 1, 1, 58,  5, 140, 3,10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(2, 1, 1, 58, 13, 206, 3,10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(2, 1, 1, 58, 13, 207, 3,10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(2, 1, 1, 58, 13, 208, 3,10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(2, 1, 1, 58, 13, 210, 3,10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(2, 1, 1, 59,  9,   8, 3,10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(2, 1, 1, 59,  9,   2, 3,10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(2, 1, 1, 59,  9,  43, 3,10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(2, 1, 1, 59,  9,  54, 3,10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(2, 1, 1, 59, 10,   9, 3,10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(2, 1, 1, 59, 10,  61, 3,10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(2, 1, 1, 59, 10,  63, 3,10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(2, 1, 1, 59, 11,  71, 3,10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(2, 1, 1, 59, 11,  73, 3,10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(2, 1, 1, 59,  8, 181, 3,10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(2, 1, 1, 59,  8, 183, 3,10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(2, 1, 1, 59,  7,  14, 3,10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(2, 1, 1, 59,  7,  20, 3,10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(2, 1, 1, 59,  7, 166, 3,10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(2, 1, 1, 59,  7, 168, 3,10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid())
;

-- ABC Training version 3
INSERT INTO program_template (version, modality_id,goal_id, program_id,  work_group_id, exercise_id,  qty_series, qty_reps,   execution, execution_time, rest_time, weight, weight_unit, comments, status, created_at, updated_at, external_id) VALUES
(3, 1, 1, 57,  1,   1, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(3, 1, 1, 57,  1,  10, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(3, 1, 1, 57,  1,  94, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(3, 1, 1, 57,  3, 122, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(3, 1, 1, 57,  3, 131, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(3, 1, 1, 57,  3, 127, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(3, 1, 1, 57,  6,   6, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(3, 1, 1, 57,  6, 151, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(3, 1, 1, 57,  6, 155, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(3, 1, 1, 58,  2, 106, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(3, 1, 1, 58,  2, 107, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(3, 1, 1, 58,  5,   5, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(3, 1, 1, 58,  5, 136, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(3, 1, 1, 58,  5, 137, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(3, 1, 1, 58,  5, 140, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(3, 1, 1, 58, 13, 206, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(3, 1, 1, 58, 13, 207, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(3, 1, 1, 58, 13, 208, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(3, 1, 1, 58, 13, 210, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(3, 1, 1, 59,  9,   8, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(3, 1, 1, 59,  9,   2, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(3, 1, 1, 59,  9,  43, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(3, 1, 1, 59,  9,  54, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(3, 1, 1, 59, 10,   9, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(3, 1, 1, 59, 10,  61, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(3, 1, 1, 59, 10,  63, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(3, 1, 1, 59, 11,  71, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(3, 1, 1, 59, 11,  73, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(3, 1, 1, 59,  8, 181, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(3, 1, 1, 59,  8, 183, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(3, 1, 1, 59,  7,  14, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(3, 1, 1, 59,  7,  20, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(3, 1, 1, 59,  7, 166, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid()),
(3, 1, 1, 59,  7, 168, 3, 10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW(), gen_random_uuid())
;

-- adjusting execution method for modality  (1-Bodybuilding) and goal (1-Hypertrophy)
update program_template set execution_method = 'TRADITIONAL_SET' where modality_id = 1 and goal_id = 1;

--videos for exercises
update exercise set video_url_pt = 'https://www.youtube.com/shorts/3QU7jjEgWVI' where id=7;
update exercise set video_url_pt = 'https://www.youtube.com/shorts/g3T7LsEeDWQ' where id=100;
update exercise set video_url_pt = 'https://youtu.be/EZKnjiDXPlY' where id=91;
update exercise set video_url_pt = 'https://youtu.be/EZKnjiDXPlY' where id=1;
update exercise set video_url_pt = 'https://youtu.be/7UoGsmqallc' where id=104;
update exercise set video_url_pt = 'https://www.youtube.com/shorts/Ft6nkjCozf0' where id=99;
update exercise set video_url_pt = 'https://www.youtube.com/shorts/Ft6nkjCozf0' where id=105;
update exercise set video_url_pt = 'https://www.youtube.com/shorts/B9gGcbEdYBQ' where id=94;


