
-- Cleanup
truncate table public.parameters CASCADE;
truncate table public.plan_template CASCADE;
truncate table public.student_feature CASCADE;
truncate table public.personal_trainer_payments CASCADE;
truncate table public.personal_feature CASCADE;
truncate table public.active_personal_plan CASCADE;
truncate table public.student_payments CASCADE;
truncate table public.program_template CASCADE;
truncate table public.users CASCADE;
truncate table public.modality CASCADE;
truncate table public.personal_trainer_program CASCADE;
truncate table public.user_workout_calendar CASCADE;
truncate table public.work_group CASCADE;
truncate table public.exercise CASCADE;
truncate table public.goal CASCADE;
truncate table public.program CASCADE;
truncate table public.user_pack_training CASCADE;

CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Users
INSERT INTO users (id, uuid_id, first_name, email, user_profile, status, created_at, updated_at) VALUES
(1, gen_random_uuid(), 'Alice Student', 'alice.student@example.com', 'STUDENT', 'A', NOW(), NOW()),
(2, gen_random_uuid(), 'Bob Student', 'bob.student@example.com', 'STUDENT', 'A', NOW(), NOW()),
(3, gen_random_uuid(), 'Carol Student', 'carol.student@example.com', 'STUDENT', 'A', NOW(), NOW()),
(4, gen_random_uuid(), 'Dave Student', 'dave.student@example.com', 'STUDENT', 'A', NOW(), NOW()),
(5, gen_random_uuid(), 'Eve Student', 'eve.student@example.com', 'STUDENT', 'A', NOW(), NOW()),
(6, gen_random_uuid(), 'Frank Trainer', 'frank.trainer@example.com', 'PERSONAL_TRAINER', 'A', NOW(), NOW()),
(7, gen_random_uuid(), 'Grace Trainer', 'grace.trainer@example.com', 'PERSONAL_TRAINER', 'A', NOW(), NOW()),
(8, gen_random_uuid(), 'Heidi Trainer', 'heidi.trainer@example.com', 'PERSONAL_TRAINER', 'A', NOW(), NOW()),
(9, gen_random_uuid(), 'Ivan Trainer', 'ivan.trainer@example.com', 'PERSONAL_TRAINER', 'A', NOW(), NOW()),
(10,gen_random_uuid(),  'Judy Trainer', 'judy.trainer@example.com', 'PERSONAL_TRAINER', 'A', NOW(), NOW());

-- Parameters
INSERT INTO parameters (keytag, valuetag, status) VALUES
('MAX_LOGIN_ATTEMPTS', '5', 'A'),
('DEFAULT_LANGUAGE', 'en', 'A'),
('MAX_SESSIONS', '3', 'A'),
('APP_NAME', 'TreinadorPro', 'A'),
('PASSWORD_EXPIRY_DAYS', '90', 'A'),
('ENABLE_NOTIFICATIONS', 'true', 'A'),
('SUPPORT_EMAIL', 'support@treinadorpro.com', 'A'),
('TIMEZONE_DEFAULT', 'UTC', 'A'),
('DEFAULT_CURRENCY', 'USD', 'A'),
('MAINTENANCE_MODE', 'false', 'A');

-- (continuação: Programs, Goals, Work Groups, Plan Templates, Active Plans, Payments, Features, Program Templates, Trainer Programs)
-- Para manter o arquivo gerenciável, a continuação será anexada

-- Program
INSERT INTO program (id, name_pt, name_en, name_es, status, created_at, updated_at) VALUES
(1, 'Perda de Peso', 'Weight Loss', 'Pérdida de Peso', 'A', NOW(), NOW()),
(2, 'Hipertrofia', 'Hypertrophy', 'Hipertrofia', 'A', NOW(), NOW()),
(3, 'Condicionamento Físico', 'Physical Conditioning', 'Condicionamiento Físico', 'A', NOW(), NOW()),
(4, 'Resistência', 'Endurance', 'Resistencia', 'A', NOW(), NOW()),
(5, 'Flexibilidade', 'Flexibility', 'Flexibilidad', 'A', NOW(), NOW()),
(6, 'Treino Funcional', 'Functional Training', 'Entrenamiento Funcional', 'A', NOW(), NOW()),
(7, 'Calistenia', 'Calisthenics', 'Calistenia', 'A', NOW(), NOW()),
(8, 'Mobilidade Articular', 'Joint Mobility', 'Movilidad Articular', 'A', NOW(), NOW()),
(9, 'Treino de Força', 'Strength Training', 'Entrenamiento de Fuerza', 'A', NOW(), NOW()),
(10, 'Treino Aeróbico', 'Aerobic Training', 'Entrenamiento Aeróbico', 'A', NOW(), NOW());

-- Goal
INSERT INTO goal (id, name_pt, name_en, name_es, status, created_at, updated_at) VALUES
(1, 'Perda de Peso', 'Weight Loss', 'Pérdida de peso', 'A', NOW(), NOW()),
(2, 'Hipertrofia', 'Hypertrophy', 'Hipertrofia', 'A', NOW(), NOW()),
(3, 'Condicionamento', 'Conditioning', 'Condicionamiento', 'A', NOW(), NOW()),
(4, 'Resistência', 'Endurance', 'Resistencia', 'A', NOW(), NOW()),
(5, 'Flexibilidade', 'Flexibility', 'Flexibilidad', 'A', NOW(), NOW()),
(6, 'Recuperação', 'Recovery', 'Recuperación', 'A', NOW(), NOW()),
(7, 'Performance Esportiva', 'Sports Performance', 'Desempeño deportivo', 'A', NOW(), NOW()),
(8, 'Postura', 'Posture', 'Postura', 'A', NOW(), NOW()),
(9, 'Mobilidade', 'Mobility', 'Movilidad', 'A', NOW(), NOW()),
(10, 'Prevenção de Lesão', 'Injury Prevention', 'Prevención de lesiones', 'A', NOW(), NOW());

-- Work Group

INSERT INTO work_group (id, name_pt, name_en, name_es, status)
VALUES
(1, 'Peitoral', 'Chest', 'Pectorales', 'A'),
(2, 'Costas', 'Back', 'Espalda', 'A'),
(3, 'Ombros', 'Shoulders', 'Hombros', 'A'),
(4, 'Bíceps', 'Biceps', 'Bíceps', 'A'),
(5, 'Tríceps', 'Triceps', 'Tríceps', 'A'),
(6, 'Abdômen', 'Abdomen', 'Abdomen', 'A'),
(7, 'Glúteos', 'Glutes', 'Glúteos', 'A'),
(8, 'Quadríceps', 'Quadriceps', 'Cuádriceps', 'A'),
(9, 'Posterior de Coxa', 'Hamstrings', 'Isquiotibiales', 'A'),
(10, 'Panturrilhas', 'Calves', 'Pantorrillas', 'A'),
(11, 'Antebraço', 'Forearm', 'Antebrazo', 'A'),
(12, 'Trapézio', 'Trapezius', 'Trapecio', 'A'),
(13, 'Adutores', 'Adductors', 'Aductores', 'A'),
(14, 'Abdutores', 'Abductors', 'Abductores', 'A'),
(15, 'Corpo inteiro', 'Full Body', 'Cuerpo entero', 'A'),
(16, 'Lombar', 'Lower Back', 'Zona Lumbar', 'A');

--modality
INSERT INTO modality (id, name_pt, name_en, name_es, status, created_at, updated_at)
VALUES (1,'Treinamento Funcional', 'Functional Training', 'Entrenamiento Funcional', 'A', NOW(), NOW());

INSERT INTO exercise (id,name_pt, name_en, name_es, status, created_at, updated_at) VALUES
(1,'Agachamento Livre', 'Free Squat', 'Sentadilla Libre', 'A', NOW(), NOW()),
(2,'Flexão de Braço', 'Push-up', 'Flexiones', 'A', NOW(), NOW()),
(3,'Abdominal Supra', 'Crunch', 'Abdominales', 'A', NOW(), NOW()),
(4,'Prancha', 'Plank', 'Plancha', 'A', NOW(), NOW()),
(5,'Puxada Frente', 'Front Pulldown', 'Jalón Frontal', 'A', NOW(), NOW()),
(6,'Remada Curvada', 'Bent-over Row', 'Remo Inclinado', 'A', NOW(), NOW()),
(7,'Elevação Lateral', 'Lateral Raise', 'Elevación Lateral', 'A', NOW(), NOW()),
(9,'Desenvolvimento Ombro', 'Shoulder Press', 'Press Militar', 'A', NOW(), NOW()),
(10,'Agachamento com Salto', 'Jump Squat', 'Sentadilla con Salto', 'A', NOW(), NOW()),
(11,'Afundo', 'Lunge', 'Zancada', 'A', NOW(), NOW()),
(12,'Corrida Estacionária', 'Running in Place', 'Correr en el Sitio', 'A', NOW(), NOW()),
(13,'Burpee', 'Burpee', 'Burpee', 'A', NOW(), NOW()),
(14,'Escalador', 'Mountain Climbers', 'Escaladores', 'A', NOW(), NOW()),
(15,'Ponte de Glúteo', 'Glute Bridge', 'Puente de Glúteos', 'A', NOW(), NOW()),
(16,'Bíceps com Halteres', 'Dumbbell Biceps Curl', 'Curl de Bíceps con Mancuernas', 'A', NOW(), NOW()),
(17,'Tríceps Banco', 'Bench Triceps Dip', 'Fondos de Tríceps en Banco', 'A', NOW(), NOW()),
(18,'Elevação de Quadril', 'Hip Thrust', 'Elevación de Cadera', 'A', NOW(), NOW()),
(19,'Corrida em Esteira', 'Treadmill Running', 'Correr en Cinta', 'A', NOW(), NOW()),
(20,'Bike Ergométrica', 'Stationary Bike', 'Bicicleta Estática', 'A', NOW(), NOW()),
(21,'Pular Corda', 'Jump Rope', 'Saltar la Cuerda', 'A', NOW(), NOW());

-- Plan Template
INSERT INTO plan_template (id, description, price, amount_discount, payment_frequency, qty_user_pack_training_allowed, status, created_at, updated_at) VALUES
(1, 'Plano Mensal Básico', 100.00, 0, 'MONTHLY', 5, 'A', NOW(), NOW()),
(2, 'Plano Anual Básico', 1000.00, 200, 'ANNUALLY', 60, 'A', NOW(), NOW()),
(3, 'Plano Mensal Premium', 150.00, 0, 'MONTHLY', 8, 'A', NOW(), NOW()),
(4, 'Plano Anual Premium', 1400.00, 400, 'ANNUALLY', 96, 'A', NOW(), NOW()),
(5, 'Plano Especial', 500.00, 50, 'MONTHLY', 20, 'A', NOW(), NOW());

-- Active Personal Plan
INSERT INTO active_personal_plan (id, plan_template_id, personal_user_id, description, price, amount_discount, plan_expiration_date, payment_frequency, qty_user_pack_training_allowed, status, created_at, updated_at) VALUES
(1, 1, 6, 'Plano Mensal do Frank', 100, 0, NOW() + INTERVAL '30 day', 'MONTHLY', 5, 'A', NOW(), NOW()),
(2, 2, 7, 'Plano Anual da Grace', 1000, 200, NOW() + INTERVAL '365 day', 'ANNUALLY', 60, 'A', NOW(), NOW()),
(3, 3, 8, 'Plano Mensal da Heidi', 150, 0, NOW() + INTERVAL '30 day', 'MONTHLY', 8, 'A', NOW(), NOW()),
(4, 4, 9, 'Plano Anual do Ivan', 1400, 400, NOW() + INTERVAL '365 day', 'ANNUALLY', 96, 'A', NOW(), NOW()),
(5, 5, 10, 'Plano Especial da Judy', 500, 50, NOW() + INTERVAL '30 day', 'MONTHLY', 20, 'A', NOW(), NOW());

-- Personal Trainer Payments
INSERT INTO personal_trainer_payments (active_personal_plan_id, expected_amount, expected_date, amount_discount, amount_paid, paid_date, status, created_at, updated_at) VALUES
(1, 100, NOW() + INTERVAL '5 day', 0, 0, NULL, 'P', NOW(), NOW()),
(2, 1000, NOW() + INTERVAL '5 day', 200, 0, NULL, 'P', NOW(), NOW());

-- Student Feature
INSERT INTO student_feature (student_user_id, password, height, weight, weight_unit, status, created_at, updated_at) VALUES
(1, 'pass123', 170, 65.5, 'kg', 'A', NOW(), NOW()),
(2, 'pass123', 160, 58.0, 'kg', 'A', NOW(), NOW()),
(3, 'pass123', 180, 70.0, 'kg', 'A', NOW(), NOW()),
(4, 'pass123', 165, 60.0, 'kg', 'A', NOW(), NOW()),
(5, 'pass123', 175, 68.0, 'kg', 'A', NOW(), NOW());

-- Personal Feature
INSERT INTO personal_feature (personal_user_id, mon_period, tue_period, wed_period, thu_period, fri_period, sat_period, sun_period, status, created_at, updated_at) VALUES
(6, '08:00-12:00', '08:00-12:00', '08:00-12:00', '08:00-12:00', '08:00-12:00', NULL, NULL, 'A', NOW(), NOW()),
(7, '14:00-18:00', '14:00-18:00', '14:00-18:00', '14:00-18:00', '14:00-18:00', NULL, NULL, 'A', NOW(), NOW());

INSERT INTO user_pack_training (id, personal_user_id, student_user_id, description, price, modality_id, start_time, end_time, days_of_week, status, created_at, updated_at)
VALUES (1, 6, 1, 'Pacote de Treino Funcional', 300.00, 1, '08:00', '09:00', 'Mon,Wed,Fri', 'A', NOW(), NOW());

-- Student Payments
INSERT INTO student_payments (user_pack_training_id, amount, expected_date, payment_date, status, created_at, updated_at) VALUES
(1, 300, NOW() + INTERVAL '10 day', NULL, 'P', NOW(), NOW());

-- Program Template
INSERT INTO program_template (modality_id, work_group_id, goal_id, exercise_id, program_id, execution, execution_time, rest_time, weight, weight_unit, comments, status, created_at, updated_at) VALUES
(1, 1, 1, 1, 1, '3s x 10rep', null, '60s', 20, 'kg', 'Atenção na respiração', 'A', NOW(), NOW());

-- Personal Trainer Program
INSERT INTO personal_trainer_program (personal_user_id, modality_id, work_group_id, goal_id, exercise_id, program_id, custom_exercise, custom_program, execution, execution_time, rest_time, weight, weight_unit, comments, obs, status, created_at, updated_at) VALUES
(6, 1, 1, 1, 1, 1, NULL, NULL, '4x12', '50', '90', 15, 'kg', 'Foco na execução', 'Usar barra fixa', 'A', NOW(), NOW());
