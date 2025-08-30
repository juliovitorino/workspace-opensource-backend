CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Inserção de Exercícios para Musculação focus quadriceps (id=1)
INSERT INTO exercise (id, image_uuid, name_pt, name_en, name_es) VALUES
(51, gen_random_uuid(), 'Agachamento Livre', 'Barbell Squat', 'Sentadilla Libre'),
(52, gen_random_uuid(), 'Agachamento no Smith', 'Smith Machine Squat', 'Sentadilla en Máquina Smith'),
(53, gen_random_uuid(), 'Leg Press 45°', '45 Degree Leg Press', 'Prensa de Piernas 45°'),
(54, gen_random_uuid(), 'Extensão de Pernas', 'Leg Extension', 'Extensión de Piernas'),
(55, gen_random_uuid(), 'Agachamento Frontal', 'Front Squat', 'Sentadilla Frontal'),
(56, gen_random_uuid(), 'Agachamento Búlgaro', 'Bulgarian Split Squat', 'Sentadilla Búlgara'),
(57, gen_random_uuid(), 'Passada com Halteres', 'Dumbbell Walking Lunge', 'Zancada Caminando con Mancuernas'),
(58, gen_random_uuid(), 'Step-up no Banco', 'Step-up onto Bench', 'Subida al Banco'),
(59, gen_random_uuid(), 'Agachamento Sumô', 'Sumo Squat', 'Sentadilla Sumo'),
(60, gen_random_uuid(), 'Hack Machine', 'Hack Squat Machine', 'Máquina Hack de Sentadilla');

-- Inserção de Exercícios para Musculação focus harmstrings (id=1)
INSERT INTO exercise (id, image_uuid, name_pt, name_en, name_es) VALUES
(61, gen_random_uuid(), 'Mesa Flexora', 'Lying Leg Curl', 'Curl de Piernas Acostado'),
(62, gen_random_uuid(), 'Flexão de Joelhos em Pé', 'Standing Leg Curl', 'Curl de Piernas de Pie'),
(63, gen_random_uuid(), 'Flexão de Joelhos Sentado', 'Seated Leg Curl', 'Curl de Piernas Sentado'),
(64, gen_random_uuid(), 'Stiff com Barra', 'Barbell Stiff-Leg Deadlift', 'Peso Muerto Rumano con Barra'),
(65, gen_random_uuid(), 'Stiff com Halteres', 'Dumbbell Stiff-Leg Deadlift', 'Peso Muerto Rumano con Mancuernas'),
(66, gen_random_uuid(), 'Glute Ham Raise', 'Glute Ham Raise', 'Elevación de Glúteos e Isquiotibiales'),
(67, gen_random_uuid(), 'Good Morning', 'Good Morning Exercise', 'Buenos Días'),
(68, gen_random_uuid(), 'Deadlift Romeno com Halteres', 'Dumbbell Romanian Deadlift', 'Peso Muerto Rumano con Mancuernas'),
(69, gen_random_uuid(), 'Cadeira Flexora Unilateral', 'Unilateral Seated Leg Curl', 'Curl de Piernas Unilateral Sentado'),
(70, gen_random_uuid(), 'Flexão Nórdica', 'Nordic Hamstring Curl', 'Curl Nórdico de Isquiotibiales');

-- Inserção de Exercícios para Musculação focus calves (id=1)
INSERT INTO exercise (id, image_uuid, name_pt, name_en, name_es) VALUES
(71, gen_random_uuid(), 'Elevação de Gêmeos em Pé', 'Standing Calf Raise', 'Elevación de Gemelos de Pie'),
(72, gen_random_uuid(), 'Elevação de Gêmeos Sentado', 'Seated Calf Raise', 'Elevación de Gemelos Sentado'),
(73, gen_random_uuid(), 'Elevação de Gêmeos na Leg Press', 'Calf Press on Leg Press', 'Elevación de Gemelos en Prensa de Piernas'),
(74, gen_random_uuid(), 'Elevação de Gêmeos Unilateral', 'Single-Leg Standing Calf Raise', 'Elevación de Gemelos a Una Pierna'),
(75, gen_random_uuid(), 'Elevação de Gêmeos no Smith', 'Smith Machine Calf Raise', 'Elevación de Gemelos en Máquina Smith'),
(76, gen_random_uuid(), 'Saltos na Corda', 'Jump Rope', 'Saltar la Cuerda'),
(77, gen_random_uuid(), 'Elevação de Gêmeos na Plataforma', 'Calf Raise on Step Platform', 'Elevación de Gemelos en Plataforma'),
(78, gen_random_uuid(), 'Donkey Calf Raise', 'Donkey Calf Raise', 'Elevación de Gemelos Tipo Burro'),
(79, gen_random_uuid(), 'Elevação de Gêmeos com Halteres', 'Dumbbell Standing Calf Raise', 'Elevación de Gemelos con Mancuernas'),
(80, gen_random_uuid(), 'Elevação de Gêmeos no Hack Machine', 'Hack Machine Calf Raise', 'Elevación de Gemelos en Máquina Hack');

-- Inserção de Exercícios para Musculação focus adutores e abdutores (id=1)
INSERT INTO exercise (id, image_uuid, name_pt, name_en, name_es) VALUES
(81, gen_random_uuid(), 'Cadeira Abdutora', 'Seated Hip Abduction', 'Abducción de Cadera Sentado'),
(82, gen_random_uuid(), 'Cadeira Adutora', 'Seated Hip Adduction', 'Aducción de Cadera Sentado'),
(83, gen_random_uuid(), 'Abdução de Quadril em Pé', 'Standing Hip Abduction', 'Abducción de Cadera de Pie'),
(84, gen_random_uuid(), 'Addução de Quadril em Pé', 'Standing Hip Adduction', 'Aducción de Cadera de Pie'),
(85, gen_random_uuid(), 'Abdução de Quadril com Faixa Elástica', 'Resistance Band Hip Abduction', 'Abducción de Cadera con Banda de Resistencia'),
(86, gen_random_uuid(), 'Addução de Quadril com Faixa Elástica', 'Resistance Band Hip Adduction', 'Aducción de Cadera con Banda de Resistencia'),
(87, gen_random_uuid(), 'Abdução de Quadril no Cross Over', 'Cable Hip Abduction', 'Abducción de Cadera en Polea'),
(88, gen_random_uuid(), 'Addução de Quadril no Cross Over', 'Cable Hip Adduction', 'Aducción de Cadera en Polea'),
(89, gen_random_uuid(), 'Abdução de Quadril Deitado', 'Side-Lying Hip Abduction', 'Abducción de Cadera Acostado de Lado'),
(90, gen_random_uuid(), 'Addução de Quadril Deitado', 'Side-Lying Hip Adduction', 'Aducción de Cadera Acostado de Lado');

-- Inserção de Exercícios para Musculação focus chest (id=1)
INSERT INTO exercise (id, image_uuid, name_pt, name_en, name_es) VALUES
(91, gen_random_uuid(), 'Supino Reto com Barra', 'Barbell Bench Press', 'Press de Banca con Barra'),
(92, gen_random_uuid(), 'Supino Reto com Halteres', 'Dumbbell Bench Press', 'Press de Banca con Mancuernas'),
(93, gen_random_uuid(), 'Supino Inclinado com Barra', 'Barbell Incline Bench Press', 'Press de Banca Inclinado con Barra'),
(94, gen_random_uuid(), 'Supino Inclinado com Halteres', 'Dumbbell Incline Bench Press', 'Press de Banca Inclinado con Mancuernas'),
(95, gen_random_uuid(), 'Supino Declinado com Barra', 'Barbell Decline Bench Press', 'Press de Banca Declinado con Barra'),
(96, gen_random_uuid(), 'Supino Declinado com Halteres', 'Dumbbell Decline Bench Press', 'Press de Banca Declinado con Mancuernas'),
(97, gen_random_uuid(), 'Crucifixo Reto com Halteres', 'Dumbbell Flat Fly', 'Aperturas Planas con Mancuernas'),
(98, gen_random_uuid(), 'Crucifixo Inclinado com Halteres', 'Dumbbell Incline Fly', 'Aperturas Inclinadas con Mancuernas'),
(99, gen_random_uuid(), 'Crossover no Cross Over', 'Cable Crossover', 'Cruce de Poleas'),
(100,gen_random_uuid(),  'Peck Deck', 'Pec Deck Machine', 'Máquina Pec Deck'),
(101,gen_random_uuid(),  'Flexão de Braços no Solo', 'Push-up', 'Flexiones de Pecho'),
(102,gen_random_uuid(),  'Flexão de Braços com Pés Elevados', 'Decline Push-up', 'Flexiones Declive'),
(103,gen_random_uuid(),  'Flexão de Braços com Mãos Elevadas', 'Incline Push-up', 'Flexiones Inclinadas'),
(104,gen_random_uuid(),  'Crossover Baixo no Cross Over', 'Low to High Cable Crossover', 'Cruce de Poleas de Abajo a Arriba'),
(105,gen_random_uuid(),  'Crossover Alto no Cross Over', 'High to Low Cable Crossover', 'Cruce de Poleas de Arriba a Abajo');

-- Inserção de Exercícios para Musculação focus back (id=1)
INSERT INTO exercise (id, image_uuid, name_pt, name_en, name_es) VALUES
(106, gen_random_uuid(), 'Puxada Frontal na Barra Fixa', 'Pull-up', 'Dominadas'),
(107, gen_random_uuid(), 'Puxada Frontal na Polia', 'Lat Pulldown', 'Jalón al Pecho'),
(108, gen_random_uuid(), 'Puxada Neutra na Polia', 'Neutral-Grip Lat Pulldown', 'Jalón al Pecho con Agarre Neutro'),
(109, gen_random_uuid(), 'Puxada por Trás da Nuca', 'Behind the Neck Pulldown', 'Jalón Detrás del Cuello'),
(110, gen_random_uuid(), 'Remada Curvada com Barra', 'Barbell Bent-Over Row', 'Remo con Barra Inclinado'),
(111, gen_random_uuid(), 'Remada Curvada com Halteres', 'Dumbbell Bent-Over Row', 'Remo con Mancuernas Inclinado'),
(112, gen_random_uuid(), 'Remada Unilateral com Halter', 'One-Arm Dumbbell Row', 'Remo Unilateral con Mancuerna'),
(113, gen_random_uuid(), 'Remada Baixa na Polia', 'Seated Cable Row', 'Remo en Máquina Sentado'),
(114, gen_random_uuid(), 'Remada Cavalinho (T-Bar Row)', 'T-Bar Row', 'Remo T-Bar'),
(115, gen_random_uuid(), 'Levantamento Terra', 'Deadlift', 'Peso Muerto'),
(116, gen_random_uuid(), 'Levantamento Terra Romeno', 'Romanian Deadlift', 'Peso Muerto Rumano'),
(117, gen_random_uuid(), 'Remada na Máquina Hammer', 'Hammer Strength Row', 'Remo en Máquina Hammer'),
(118, gen_random_uuid(), 'Shrug com Barra', 'Barbell Shrug', 'Encogimientos de Trapecio con Barra'),
(119, gen_random_uuid(), 'Shrug com Halteres', 'Dumbbell Shrug', 'Encogimientos de Trapecio con Mancuernas'),
(120, gen_random_uuid(), 'Extensão Lombar no Banco', 'Back Extension', 'Extensiones de Espalda');

-- Inserção de Exercícios para Musculação focus shoulders (id=1)
INSERT INTO exercise (id, image_uuid, name_pt, name_en, name_es) VALUES
(121, gen_random_uuid(), 'Desenvolvimento com Barra', 'Barbell Overhead Press', 'Press Militar con Barra'),
(122, gen_random_uuid(), 'Desenvolvimento com Halteres', 'Dumbbell Shoulder Press', 'Press Militar con Mancuernas'),
(123, gen_random_uuid(), 'Desenvolvimento na Máquina', 'Machine Shoulder Press', 'Press Militar en Máquina'),
(124, gen_random_uuid(), 'Elevação Lateral com Halteres', 'Dumbbell Lateral Raise', 'Elevaciones Laterales con Mancuernas'),
(125, gen_random_uuid(), 'Elevação Lateral na Polia', 'Cable Lateral Raise', 'Elevaciones Laterales en Polea'),
(126, gen_random_uuid(), 'Elevação Frontal com Halteres', 'Dumbbell Front Raise', 'Elevaciones Frontales con Mancuernas'),
(127, gen_random_uuid(), 'Elevação Frontal na Polia', 'Cable Front Raise', 'Elevaciones Frontales en Polea'),
(128, gen_random_uuid(), 'Crucifixo Inverso com Halteres', 'Dumbbell Reverse Fly', 'Aperturas Invertidas con Mancuernas'),
(129, gen_random_uuid(), 'Crucifixo Inverso na Máquina', 'Reverse Pec Deck', 'Pec Deck Invertido'),
(130, gen_random_uuid(), 'Remada Alta com Barra', 'Barbell Upright Row', 'Remo Vertical con Barra'),
(131, gen_random_uuid(), 'Remada Alta com Halteres', 'Dumbbell Upright Row', 'Remo Vertical con Mancuernas'),
(132, gen_random_uuid(), 'Arnold Press', 'Arnold Press', 'Press Arnold'),
(133, gen_random_uuid(), 'Face Pull na Polia', 'Face Pull', 'Face Pull en Polea'),
(134, gen_random_uuid(), 'Desenvolvimento com Halteres Sentado', 'Seated Dumbbell Shoulder Press', 'Press Militar Sentado con Mancuernas'),
(135, gen_random_uuid(), 'Elevação Lateral Unilateral na Polia', 'Single-Arm Cable Lateral Raise', 'Elevación Lateral Unilateral en Polea');

-- Inserção de Exercícios para Musculação focus biceps (id=1)
INSERT INTO exercise (id, image_uuid, name_pt, name_en, name_es) VALUES
(136, gen_random_uuid(), 'Rosca Direta com Barra', 'Barbell Curl', 'Curl de Bíceps con Barra'),
(137, gen_random_uuid(), 'Rosca Direta com Halteres', 'Dumbbell Curl', 'Curl de Bíceps con Mancuernas'),
(138, gen_random_uuid(), 'Rosca Alternada com Halteres', 'Alternating Dumbbell Curl', 'Curl Alternado con Mancuernas'),
(139, gen_random_uuid(), 'Rosca Scott na Barra', 'Barbell Preacher Curl', 'Curl Predicador con Barra'),
(140, gen_random_uuid(), 'Rosca Scott com Halteres', 'Dumbbell Preacher Curl', 'Curl Predicador con Mancuernas'),
(141, gen_random_uuid(), 'Rosca Martelo com Halteres', 'Hammer Curl', 'Curl Martillo con Mancuernas'),
(142, gen_random_uuid(), 'Rosca Concentrada', 'Concentration Curl', 'Curl Concentrado'),
(143, gen_random_uuid(), 'Rosca Inversa com Barra', 'Reverse Barbell Curl', 'Curl Inverso con Barra'),
(144, gen_random_uuid(), 'Rosca 21', '21s Curl', 'Curl 21s'),
(145, gen_random_uuid(), 'Rosca de Bíceps na Polia', 'Cable Biceps Curl', 'Curl de Bíceps en Polea'),
(146, gen_random_uuid(), 'Rosca Martelo na Polia', 'Cable Hammer Curl', 'Curl Martillo en Polea'),
(147, gen_random_uuid(), 'Rosca de Bíceps no Cross Over', 'Cross Cable Curl', 'Curl de Bíceps en Cruce de Poleas'),
(148, gen_random_uuid(), 'Rosca de Bíceps com Barra W', 'EZ Bar Curl', 'Curl con Barra EZ'),
(149, gen_random_uuid(), 'Rosca Spider com Halteres', 'Spider Dumbbell Curl', 'Curl Spider con Mancuernas'),
(150, gen_random_uuid(), 'Rosca Spider com Barra', 'Spider Barbell Curl', 'Curl Spider con Barra');

-- Inserção de Exercícios para Musculação focus triceps (id=1)
INSERT INTO exercise (id, image_uuid, name_pt, name_en, name_es) VALUES
(151, gen_random_uuid(), 'Tríceps Testa com Barra', 'Barbell Skullcrusher', 'Extensión de Tríceps con Barra Acostado'),
(152, gen_random_uuid(), 'Tríceps Testa com Halteres', 'Dumbbell Skullcrusher', 'Extensión de Tríceps con Mancuernas Acostado'),
(153, gen_random_uuid(), 'Tríceps na Polia com Barra Reta', 'Cable Pushdown with Straight Bar', 'Extensión de Tríceps en Polea con Barra Recta'),
(154, gen_random_uuid(), 'Tríceps na Polia com Corda', 'Cable Rope Pushdown', 'Extensión de Tríceps en Polea con Cuerda'),
(155, gen_random_uuid(), 'Tríceps na Polia com Barra V', 'Cable Pushdown with V Bar', 'Extensión de Tríceps en Polea con Barra V'),
(156, gen_random_uuid(), 'Tríceps Coice com Halteres', 'Dumbbell Kickback', 'Patada de Tríceps con Mancuernas'),
(157, gen_random_uuid(), 'Mergulho entre Bancos', 'Bench Dips', 'Fondos entre Bancos'),
(158, gen_random_uuid(), 'Mergulho nas Paralelas', 'Parallel Bar Dips', 'Fondos en Paralelas'),
(159, gen_random_uuid(), 'Tríceps Francês com Halteres', 'Dumbbell Overhead Triceps Extension', 'Extensión de Tríceps por Encima de la Cabeza con Mancuernas'),
(160, gen_random_uuid(), 'Tríceps Francês com Barra', 'Barbell Overhead Triceps Extension', 'Extensión de Tríceps por Encima de la Cabeza con Barra'),
(161, gen_random_uuid(), 'Tríceps Unilateral na Polia', 'Single-Arm Cable Pushdown', 'Extensión de Tríceps a Una Mano en Polea'),
(162, gen_random_uuid(), 'Tríceps Invertido na Polia', 'Reverse Grip Cable Pushdown', 'Extensión de Tríceps en Polea con Agarre Invertido'),
(163, gen_random_uuid(), 'Tríceps Kickback na Polia', 'Cable Kickback', 'Patada de Tríceps en Polea'),
(164, gen_random_uuid(), 'Tríceps Crossbody com Corda', 'Cable Crossbody Triceps Extension', 'Extensión de Tríceps Cruzado en Polea'),
(165, gen_random_uuid(), 'Tríceps Pullover com Halteres', 'Dumbbell Pullover Triceps', 'Pullover de Tríceps con Mancuernas');

-- Inserção de Exercícios para Musculação focus abs (id=1)
INSERT INTO exercise (id, image_uuid, name_pt, name_en, name_es) VALUES
(166, gen_random_uuid(), 'Abdominal Supra no Solo', 'Floor Crunch', 'Abdominal Superior en el Suelo'),
(167, gen_random_uuid(), 'Abdominal Infra no Solo', 'Reverse Crunch', 'Abdominal Inferior en el Suelo'),
(168, gen_random_uuid(), 'Abdominal Oblíquo no Solo', 'Oblique Crunch', 'Abdominal Oblicuo en el Suelo'),
(169, gen_random_uuid(), 'Prancha Frontal', 'Front Plank', 'Plancha Frontal'),
(170, gen_random_uuid(), 'Prancha Lateral', 'Side Plank', 'Plancha Lateral'),
(171, gen_random_uuid(), 'Abdominal Bicicleta', 'Bicycle Crunch', 'Abdominal Bicicleta'),
(172, gen_random_uuid(), 'Elevação de Pernas no Solo', 'Lying Leg Raise', 'Elevación de Piernas Acostado'),
(173, gen_random_uuid(), 'Elevação de Pernas na Barra Fixa', 'Hanging Leg Raise', 'Elevación de Piernas Colgado'),
(174, gen_random_uuid(), 'Abdominal Canivete', 'V-Up', 'Abdominal en V'),
(175, gen_random_uuid(), 'Abdominal com Corda na Polia', 'Cable Rope Crunch', 'Crunch de Abdominales en Polea con Cuerda'),
(176, gen_random_uuid(), 'Abdominal na Máquina', 'Machine Crunch', 'Crunch de Abdominales en Máquina'),
(177, gen_random_uuid(), 'Abdominal na Bola Suíça', 'Swiss Ball Crunch', 'Crunch de Abdominales en Balón Suizo'),
(178, gen_random_uuid(), 'Toques nos Calcanhares', 'Heel Taps', 'Toques de Talón'),
(179, gen_random_uuid(), 'Abdominal Reverso com Bola', 'Reverse Crunch with Ball', 'Abdominal Inverso con Balón'),
(180, gen_random_uuid(), 'Mountain Climbers', 'Mountain Climbers', 'Escaladores');

-- Inserção de Exercícios para Musculação focus abs (id=1)
INSERT INTO exercise (id, image_uuid, name_pt, name_en, name_es) VALUES
(181, gen_random_uuid(), 'Elevação de Quadril com Barra (Hip Thrust)', 'Barbell Hip Thrust', 'Elevación de Cadera con Barra (Hip Thrust)'),
(182, gen_random_uuid(), 'Ponte de Glúteo no Solo', 'Glute Bridge', 'Puente de Glúteos en el Suelo'),
(183, gen_random_uuid(), 'Agachamento Sumô', 'Sumo Squat', 'Sentadilla Sumo'),
(184, gen_random_uuid(), 'Agachamento Búlgaro', 'Bulgarian Split Squat', 'Sentadilla Búlgara'),
(185, gen_random_uuid(), 'Deadlift Romeno com Halteres', 'Dumbbell Romanian Deadlift', 'Peso Muerto Rumano con Mancuernas'),
(186, gen_random_uuid(), 'Levantamento Terra com Barra', 'Barbell Deadlift', 'Peso Muerto con Barra'),
(187, gen_random_uuid(), 'Kickback na Polia', 'Cable Glute Kickback', 'Patada de Glúteo en Polea'),
(188, gen_random_uuid(), 'Kickback com Faixa Elástica', 'Resistance Band Glute Kickback', 'Patada de Glúteo con Banda de Resistencia'),
(189, gen_random_uuid(), 'Abdução de Quadril em Pé', 'Standing Hip Abduction', 'Abducción de Cadera de Pie'),
(190, gen_random_uuid(), 'Abdução de Quadril Deitado', 'Side-Lying Hip Abduction', 'Abducción de Cadera Acostado'),
(191, gen_random_uuid(), 'Cadeira Abdutora', 'Seated Hip Abduction Machine', 'Máquina de Abducción de Cadera'),
(192, gen_random_uuid(), 'Step-up no Banco', 'Step-up onto Bench', 'Subida al Banco'),
(193, gen_random_uuid(), 'Agachamento no Smith', 'Smith Machine Squat', 'Sentadilla en Máquina Smith'),
(194, gen_random_uuid(), 'Glute Ham Raise', 'Glute Ham Raise', 'Elevación de Glúteos e Isquiotibiales'),
(195, gen_random_uuid(), 'Frog Pump', 'Frog Pump', 'Frog Pump para Glúteos');

-- Inserção de Exercícios para Musculação focus abs (id=1)
INSERT INTO exercise (id, image_uuid, name_pt, name_en, name_es) VALUES
(196, gen_random_uuid(), 'Rosca de Punho com Barra', 'Barbell Wrist Curl', 'Curl de Muñeca con Barra'),
(197, gen_random_uuid(), 'Rosca de Punho Invertida com Barra', 'Reverse Barbell Wrist Curl', 'Curl Inverso de Muñeca con Barra'),
(198, gen_random_uuid(), 'Rosca de Punho com Halteres', 'Dumbbell Wrist Curl', 'Curl de Muñeca con Mancuernas'),
(199, gen_random_uuid(), 'Rosca de Punho Invertida com Halteres', 'Reverse Dumbbell Wrist Curl', 'Curl Inverso de Muñeca con Mancuernas'),
(200, gen_random_uuid(), 'Pronação de Punho com Halteres', 'Dumbbell Wrist Pronation', 'Pronación de Muñeca con Mancuernas'),
(201, gen_random_uuid(), 'Supinação de Punho com Halteres', 'Dumbbell Wrist Supination', 'Supinación de Muñeca con Mancuernas'),
(202, gen_random_uuid(), 'Rosca Martelo com Halteres', 'Hammer Curl', 'Curl Martillo con Mancuernas'),
(203, gen_random_uuid(), 'Rosca Inversa na Barra W', 'EZ Bar Reverse Curl', 'Curl Inverso con Barra EZ'),
(204, gen_random_uuid(), 'Farmer’s Walk', 'Farmer’s Walk', 'Caminata del Granjero'),
(205, gen_random_uuid(), 'Dead Hang na Barra Fixa', 'Dead Hang', 'Colgado Muerto en Barra Fija');

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


