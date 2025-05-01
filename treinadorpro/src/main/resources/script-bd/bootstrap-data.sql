CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- insert parameters
INSERT INTO parameters (keytag, valuetag, status) VALUES
    ('ACTIVE_ENVIRONMENT', 'DEVELOPMENT', 'A'),
    ('MAX_LOGIN_ATTEMPTS', '5', 'A'),
    ('DEFAULT_LANGUAGE', 'pt-BR', 'A'),
    ('MAX_SESSIONS', '3', 'A'),
    ('APP_NAME', 'TreinadorPro', 'A'),
    ('PASSWORD_EXPIRY_DAYS', '90', 'A'),
    ('ENABLE_NOTIFICATIONS', 'true', 'A'),
    ('SUPPORT_EMAIL', 'support@treinadorpro.com', 'A'),
    ('TIMEZONE_DEFAULT', 'UTC', 'A'),
    ('DEFAULT_CURRENCY', 'USD', 'A'),
    ('MAINTENANCE_MODE', 'false', 'A');

INSERT INTO goal (id,name_pt, name_en, name_es, status)
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
(14, 'Aumento de Energia', 'Energy Boost', 'Aumento de Energía', 'A');

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
(17, 'Lombar', 'Lower Back', 'Zona Lumbar', 'A');

INSERT INTO program (id, name_pt, name_en, name_es, status)
VALUES
-- Hipertrofia
(1, 'Programa de Hipertrofia 18-30 anos', 'Hypertrophy Program 18-30 years', 'Programa de Hipertrofia 18-30 años', 'A'),
(2, 'Programa de Hipertrofia 31-45 anos', 'Hypertrophy Program 31-45 years', 'Programa de Hipertrofia 31-45 años', 'A'),
(3, 'Programa de Hipertrofia 46-55 anos', 'Hypertrophy Program 46-55 years', 'Programa de Hipertrofia 46-55 años', 'A'),
(4, 'Programa de Hipertrofia 55+ anos', 'Hypertrophy Program 55+ years', 'Programa de Hipertrofia 55+ años', 'A'),

-- Perda de Peso
(5, 'Programa de Perda de Peso 18-30 anos', 'Weight Loss Program 18-30 years', 'Programa de Pérdida de Peso 18-30 años', 'A'),
(6, 'Programa de Perda de Peso 31-45 anos', 'Weight Loss Program 31-45 years', 'Programa de Pérdida de Peso 31-45 años', 'A'),
(7, 'Programa de Perda de Peso 46-55 anos', 'Weight Loss Program 46-55 years', 'Programa de Pérdida de Peso 46-55 años', 'A'),
(8, 'Programa de Perda de Peso 55+ anos', 'Weight Loss Program 55+ years', 'Programa de Pérdida de Peso 55+ años', 'A'),

-- Emagrecimento
(9, 'Programa de Emagrecimento 18-30 anos', 'Slimming Program 18-30 years', 'Programa de Adelgazamiento 18-30 años', 'A'),
(10, 'Programa de Emagrecimento 31-45 anos', 'Slimming Program 31-45 years', 'Programa de Adelgazamiento 31-45 años', 'A'),
(11, 'Programa de Emagrecimento 46-55 anos', 'Slimming Program 46-55 years', 'Programa de Adelgazamiento 46-55 años', 'A'),
(12, 'Programa de Emagrecimento 55+ anos', 'Slimming Program 55+ years', 'Programa de Adelgazamiento 55+ años', 'A'),

-- Definição Muscular
(13, 'Programa de Definição Muscular 18-30 anos', 'Muscle Definition Program 18-30 years', 'Programa de Definición Muscular 18-30 años', 'A'),
(14, 'Programa de Definição Muscular 31-45 anos', 'Muscle Definition Program 31-45 years', 'Programa de Definición Muscular 31-45 años', 'A'),
(15, 'Programa de Definição Muscular 46-55 anos', 'Muscle Definition Program 46-55 years', 'Programa de Definición Muscular 46-55 años', 'A'),
(16, 'Programa de Definição Muscular 55+ anos', 'Muscle Definition Program 55+ years', 'Programa de Definición Muscular 55+ años', 'A'),

-- Condicionamento Físico
(17, 'Programa de Condicionamento Físico 18-30 anos', 'Physical Conditioning Program 18-30 years', 'Programa de Acondicionamiento Físico 18-30 años', 'A'),
(18, 'Programa de Condicionamento Físico 31-45 anos', 'Physical Conditioning Program 31-45 years', 'Programa de Acondicionamiento Físico 31-45 años', 'A'),
(19, 'Programa de Condicionamento Físico 46-55 anos', 'Physical Conditioning Program 46-55 years', 'Programa de Acondicionamiento Físico 46-55 años', 'A'),
(20, 'Programa de Condicionamento Físico 55+ anos', 'Physical Conditioning Program 55+ years', 'Programa de Acondicionamiento Físico 55+ años', 'A'),

-- Ganho de Força
(21, 'Programa de Ganho de Força 18-30 anos', 'Strength Gain Program 18-30 years', 'Programa de Ganancia de Fuerza 18-30 años', 'A'),
(22, 'Programa de Ganho de Força 31-45 anos', 'Strength Gain Program 31-45 years', 'Programa de Ganancia de Fuerza 31-45 años', 'A'),
(23, 'Programa de Ganho de Força 46-55 anos', 'Strength Gain Program 46-55 years', 'Programa de Ganancia de Fuerza 46-55 años', 'A'),
(24, 'Programa de Ganho de Força 55+ anos', 'Strength Gain Program 55+ years', 'Programa de Ganancia de Fuerza 55+ años', 'A'),

-- Resistência Cardiorrespiratória
(25, 'Programa de Resistência Cardiorrespiratória 18-30 anos', 'Cardiorespiratory Endurance Program 18-30 years', 'Programa de Resistencia Cardiorrespiratoria 18-30 años', 'A'),
(26, 'Programa de Resistência Cardiorrespiratória 31-45 anos', 'Cardiorespiratory Endurance Program 31-45 years', 'Programa de Resistencia Cardiorrespiratoria 31-45 años', 'A'),
(27, 'Programa de Resistência Cardiorrespiratória 46-55 anos', 'Cardiorespiratory Endurance Program 46-55 years', 'Programa de Resistencia Cardiorrespiratoria 46-55 años', 'A'),
(28, 'Programa de Resistência Cardiorrespiratória 55+ anos', 'Cardiorespiratory Endurance Program 55+ years', 'Programa de Resistencia Cardiorrespiratoria 55+ años', 'A'),

-- Flexibilidade
(29, 'Programa de Flexibilidade 18-30 anos', 'Flexibility Program 18-30 years', 'Programa de Flexibilidad 18-30 años', 'A'),
(30, 'Programa de Flexibilidade 31-45 anos', 'Flexibility Program 31-45 years', 'Programa de Flexibilidad 31-45 años', 'A'),
(31, 'Programa de Flexibilidade 46-55 anos', 'Flexibility Program 46-55 years', 'Programa de Flexibilidad 46-55 años', 'A'),
(32, 'Programa de Flexibilidade 55+ anos', 'Flexibility Program 55+ years', 'Programa de Flexibilidad 55+ años', 'A'),

-- Melhora da Mobilidade
(33, 'Programa de Melhora da Mobilidade 18-30 anos', 'Mobility Improvement Program 18-30 years', 'Programa de Mejora de la Movilidad 18-30 años', 'A'),
(34, 'Programa de Melhora da Mobilidade 31-45 anos', 'Mobility Improvement Program 31-45 years', 'Programa de Mejora de la Movilidad 31-45 años', 'A'),
(35, 'Programa de Melhora da Mobilidade 46-55 anos', 'Mobility Improvement Program 46-55 years', 'Programa de Mejora de la Movilidad 46-55 años', 'A'),
(36, 'Programa de Melhora da Mobilidade 55+ anos', 'Mobility Improvement Program 55+ years', 'Programa de Mejora de la Movilidad 55+ años', 'A'),

-- Reabilitação Física
(37, 'Programa de Reabilitação Física 18-30 anos', 'Physical Rehabilitation Program 18-30 years', 'Programa de Rehabilitación Física 18-30 años', 'A'),
(38, 'Programa de Reabilitação Física 31-45 anos', 'Physical Rehabilitation Program 31-45 years', 'Programa de Rehabilitación Física 31-45 años', 'A'),
(39, 'Programa de Reabilitação Física 46-55 anos', 'Physical Rehabilitation Program 46-55 years', 'Programa de Rehabilitación Física 46-55 años', 'A'),
(40, 'Programa de Reabilitação Física 55+ anos', 'Physical Rehabilitation Program 55+ years', 'Programa de Rehabilitación Física 55+ años', 'A'),

-- Prevenção de Lesões
(41, 'Programa de Prevenção de Lesões 18-30 anos', 'Injury Prevention Program 18-30 years', 'Programa de Prevención de Lesiones 18-30 años', 'A'),
(42, 'Programa de Prevenção de Lesões 31-45 anos', 'Injury Prevention Program 31-45 years', 'Programa de Prevención de Lesiones 31-45 años', 'A'),
(43, 'Programa de Prevenção de Lesões 46-55 anos', 'Injury Prevention Program 46-55 years', 'Programa de Prevención de Lesiones 46-55 años', 'A'),
(44, 'Programa de Prevenção de Lesões 55+ anos', 'Injury Prevention Program 55+ years', 'Programa de Prevención de Lesiones 55+ años', 'A'),

-- Manutenção da Saúde
(45, 'Programa de Manutenção da Saúde 18-30 anos', 'Health Maintenance Program 18-30 years', 'Programa de Mantenimiento de la Salud 18-30 años', 'A'),
(46, 'Programa de Manutenção da Saúde 31-45 anos', 'Health Maintenance Program 31-45 years', 'Programa de Mantenimiento de la Salud 31-45 años', 'A'),
(47, 'Programa de Manutenção da Saúde 46-55 anos', 'Health Maintenance Program 46-55 years', 'Programa de Mantenimiento de la Salud 46-55 años', 'A'),
(48, 'Programa de Manutenção da Saúde 55+ anos', 'Health Maintenance Program 55+ years', 'Programa de Mantenimiento de la Salud 55+ años', 'A'),

-- Preparação para Corridas
(49, 'Programa de Preparação para Corridas 18-30 anos', 'Running Preparation Program 18-30 years', 'Programa de Preparación para Carreras 18-30 años', 'A'),
(50, 'Programa de Preparação para Corridas 31-45 anos', 'Running Preparation Program 31-45 years', 'Programa de Preparación para Carreras 31-45 años', 'A'),
(51, 'Programa de Preparação para Corridas 46-55 anos', 'Running Preparation Program 46-55 years', 'Programa de Preparación para Carreras 46-55 años', 'A'),
(52, 'Programa de Preparação para Corridas 55+ anos', 'Running Preparation Program 55+ years', 'Programa de Preparación para Carreras 55+ años', 'A'),

-- Aumento de Energia
(53, 'Programa de Aumento de Energia 18-30 anos', 'Energy Boost Program 18-30 years', 'Programa de Aumento de Energía 18-30 años', 'A'),
(54, 'Programa de Aumento de Energia 31-45 anos', 'Energy Boost Program 31-45 years', 'Programa de Aumento de Energía 31-45 años', 'A'),
(55, 'Programa de Aumento de Energia 46-55 anos', 'Energy Boost Program 46-55 years', 'Programa de Aumento de Energía 46-55 años', 'A'),
(56, 'Programa de Aumento de Energia 55+ anos', 'Energy Boost Program 55+ years', 'Programa de Aumento de Energía 55+ años', 'A');

INSERT INTO program (id, name_pt, name_en, name_es, status) VALUES
(57, 'Treino ABC - A (Peitoral, Ombros, Tríceps)', 'Workout ABC - A (Chest, Shoulders, Triceps)', 'Entrenamiento A (Pectorales, Hombros, Tríceps)', 'A'),
(58, 'Treino ABC - B (Costas, Bíceps, Trapézio)', 'Workout ABC - B (Back, Biceps, Trapezius)', 'Entrenamiento B (Espalda, Bíceps, Trapecio)', 'A'),
(59, 'Treino ABC - C (Pernas, Glúteos, Abdômen)', 'Workout ABC - C (Legs, Glutes, Abs)', 'Entrenamiento C (Piernas, Glúteos, Abdomen)', 'A');

insert into plan_template (
description, price, status, payment_frequency) values
    ('FREEMIUM', 67, 'A','MONTHLY'),
    ('BASIC', 804, 'A','ANNUALLY'),
    ('BASIC', 97, 'A','MONTHLY'),
    ('PROFESSIONAL', 1164, 'A','ANNUALLY'),
    ('PROFESSIONAL', 130, 'A','MONTHLY'),
    ('PREMIUM', 1560, 'A','ANNUALLY'),
    ('PREMIUM', 197, 'A','MONTHLY')
;

insert into parameters (keytag,valuetag)
select 'FREEMIUM_PLAN_ID', id
from plan_template where description = 'FREEMIUM' LIMIT 1;

-- Insert modalities
INSERT INTO modality (id, name_pt, name_en, name_es) VALUES
(1, 'Musculação', 'Bodybuilding', 'Musculación'),
(2, 'Treinamento Funcional', 'Functional Training', 'Entrenamiento Funcional'),
(3, 'Yoga', 'Yoga', 'Yoga'),
(4, 'Pilates', 'Pilates', 'Pilates'),
(5, 'CrossFit', 'CrossFit', 'CrossFit'),
(6, 'Calistenia', 'Calisthenics', 'Calistenia'),
(7, 'Spinning', 'Spinning', 'Spinning'),
(8, 'HIIT', 'HIIT', 'HIIT'),
(9, 'Corrida', 'Running', 'Correr'),
(10, 'Caminhada', 'Walking', 'Caminata'),
(11, 'Alongamento', 'Stretching', 'Estiramiento'),
(12, 'Mobilidade Articular', 'Joint Mobility', 'Movilidad Articular'),
(13, 'Zumba', 'Zumba', 'Zumba'),
(14, 'Step', 'Step', 'Step'),
(15, 'Jump', 'Jump', 'Jump'),
(16, 'Boxe', 'Boxing', 'Boxeo'),
(17, 'Muay Thai', 'Muay Thai', 'Muay Thai'),
(18, 'Jiu-Jitsu', 'Jiu-Jitsu', 'Jiu-Jitsu'),
(19, 'Judô', 'Judo', 'Judo'),
(20, 'Karatê', 'Karate', 'Karate'),
(21, 'Taekwondo', 'Taekwondo', 'Taekwondo'),
(22, 'Capoeira', 'Capoeira', 'Capoeira'),
(23, 'Natação', 'Swimming', 'Natación'),
(24, 'Hidroginástica', 'Water Aerobics', 'Aquagym'),
(25, 'Remo Indoor', 'Indoor Rowing', 'Remo Indoor'),
(26, 'Escalada Indoor', 'Indoor Climbing', 'Escalada Indoor'),
(27, 'TRX', 'Suspension Training', 'Entrenamiento en Suspensión'),
(28, 'Slackline', 'Slackline', 'Slackline'),
(29, 'Parkour', 'Parkour', 'Parkour'),
(30, 'Escalada', 'Climbing', 'Escalada'),
(31, 'Patinação', 'Skating', 'Patinaje'),
(32, 'Ciclismo', 'Cycling', 'Ciclismo'),
(33, 'Basquete', 'Basketball', 'Baloncesto'),
(34, 'Futebol', 'Soccer', 'Fútbol'),
(35, 'Futsal', 'Indoor Soccer', 'Fútbol Sala'),
(36, 'Handebol', 'Handball', 'Balonmano'),
(37, 'Vôlei', 'Volleyball', 'Voleibol'),
(38, 'Beach Tennis', 'Beach Tennis', 'Tenis Playa'),
(39, 'Tênis', 'Tennis', 'Tenis'),
(40, 'Paddle', 'Paddle', 'Pádel'),
(41, 'Esgrima', 'Fencing', 'Esgrima'),
(42, 'Ginástica Rítmica', 'Rhythmic Gymnastics', 'Gimnasia Rítmica'),
(43, 'Ginástica Artística', 'Artistic Gymnastics', 'Gimnasia Artística'),
(44, 'Ginástica Acrobática', 'Acrobatic Gymnastics', 'Gimnasia Acrobática'),
(45, 'Powerlifting', 'Powerlifting', 'Powerlifting'),
(46, 'Strongman', 'Strongman', 'Strongman'),
(47, 'Levantamento Olímpico', 'Olympic Weightlifting', 'Halterofilia Olímpica'),
(48, 'Corrida de Obstáculos', 'Obstacle Course Racing', 'Carreras de Obstáculos'),
(49, 'Dança Contemporânea', 'Contemporary Dance', 'Danza Contemporánea'),
(50, 'Balé', 'Ballet', 'Ballet'),
(51, 'Jazz Dance', 'Jazz Dance', 'Danza Jazz'),
(52, 'Street Dance', 'Street Dance', 'Baile Urbano'),
(53, 'Pole Dance', 'Pole Dance', 'Pole Dance'),
(54, 'Ginástica Localizada', 'Localized Gymnastics', 'Gimnasia Localizada'),
(55, 'Treinamento Militar', 'Military Training', 'Entrenamiento Militar'),
(56, 'Corrida de Rua', 'Street Running', 'Carrera Callejera'),
(57, 'Mountain Bike', 'Mountain Biking', 'Ciclismo de Montaña'),
(58, 'Skate', 'Skateboarding', 'Skateboarding'),
(59, 'Surfe', 'Surfing', 'Surf'),
(60, 'Bodyboard', 'Bodyboarding', 'Bodyboard'),
(61, 'Windsurf', 'Windsurfing', 'Windsurf'),
(62, 'Kitesurf', 'Kitesurfing', 'Kitesurf'),
(63, 'Canoagem', 'Canoeing', 'Piragüismo'),
(64, 'Escalada em Rocha', 'Rock Climbing', 'Escalada en Roca'),
(65, 'Tiro com Arco', 'Archery', 'Tiro con Arco'),
(66, 'Corrida em Trilha', 'Trail Running', 'Carrera de Montaña'),
(67, 'Corrida de Orientação', 'Orienteering', 'Orientación'),
(68, 'Equitação', 'Horse Riding', 'Equitación'),
(69, 'Atletismo', 'Athletics', 'Atletismo'),
(70, 'Marcha Atlética', 'Race Walking', 'Marcha Atlética'),
(71, 'Salto com Vara', 'Pole Vault', 'Salto con Pértiga'),
(72, 'Salto em Altura', 'High Jump', 'Salto de Altura'),
(73, 'Arremesso de Peso', 'Shot Put', 'Lanzamiento de Bala'),
(74, 'Lançamento de Dardo', 'Javelin Throw', 'Lanzamiento de Jabalina'),
(75, 'Lançamento de Disco', 'Discus Throw', 'Lanzamiento de Disco'),
(76, 'Corrida de Revezamento', 'Relay Race', 'Carrera de Relevo'),
(77, 'Treinamento Pós-Parto', 'Postpartum Training', 'Entrenamiento Postparto'),
(78, 'Treinamento Pré-Natal', 'Prenatal Training', 'Entrenamiento Prenatal'),
(79, 'Treinamento para Terceira Idade', 'Senior Training', 'Entrenamiento para Mayores'),
(80, 'Treinamento Infantil', 'Kids Training', 'Entrenamiento Infantil'),
(81, 'Treinamento Corporativo', 'Corporate Training', 'Entrenamiento Corporativo'),
(82, 'Reabilitação Física', 'Physical Rehabilitation', 'Rehabilitación Física'),
(83, 'Prevenção de Lesões', 'Injury Prevention', 'Prevención de Lesiones'),
(84, 'Condicionamento Físico', 'Physical Conditioning', 'Acondicionamiento Físico'),
(85, 'Core Training', 'Core Training', 'Entrenamiento de Core'),
(86, 'Resistência Cardiorrespiratória', 'Cardiorespiratory Endurance', 'Resistencia Cardiorrespiratoria'),
(87, 'Força Muscular', 'Muscular Strength', 'Fuerza Muscular'),
(88, 'Flexibilidade', 'Flexibility', 'Flexibilidad'),
(89, 'Velocidade', 'Speed', 'Velocidad'),
(90, 'Agilidade', 'Agility', 'Agilidad'),
(91, 'Coordenação Motora', 'Motor Coordination', 'Coordinación Motora'),
(92, 'Equilíbrio', 'Balance', 'Equilibrio'),
(93, 'Propriocepção', 'Proprioception', 'Propiocepción'),
(94, 'Biomecânica do Movimento', 'Movement Biomechanics', 'Biomecánica del Movimiento'),
(95, 'Análise Postural', 'Postural Analysis', 'Análisis Postural'),
(96, 'Ergonomia', 'Ergonomics', 'Ergonomía'),
(97, 'Psicomotricidade', 'Psychomotricity', 'Psicomotricidad'),
(98, 'Educação Física Escolar', 'School Physical Education', 'Educación Física Escolar'),
(99, 'Atividades Recreativas', 'Recreational Activities', 'Actividades Recreativas'),
(100, 'Jogos Cooperativos', 'Cooperative Games', 'Juegos Cooperativos'),
(101, 'Exergames', 'Exergames', 'Exergames'),
(102, 'Atividades ao Ar Livre', 'Outdoor Activities', 'Actividades al Aire Libre');

-- Inserção de Exercícios para Musculação (id=1)
INSERT INTO exercise (id, image_uuid, name_pt, name_en, name_es) VALUES
(1, gen_random_uuid(),'Supino reto', 'Flat Bench Press', 'Press de banca plano'),
(2, gen_random_uuid(),'Agachamento livre', 'Barbell Squat', 'Sentadilla con barra'),
(3, gen_random_uuid(),'Levantamento terra', 'Deadlift', 'Peso muerto'),
(4, gen_random_uuid(),'Desenvolvimento com barra', 'Overhead Press', 'Press militar'),
(5, gen_random_uuid(),'Rosca direta', 'Barbell Curl', 'Curl con barra'),
(6, gen_random_uuid(),'Tríceps pulley', 'Triceps Pushdown', 'Extensiones en polea'),
(7, gen_random_uuid(),'Remada curvada', 'Bent-over Row', 'Remo inclinado'),
(8, gen_random_uuid(),'Cadeira extensora', 'Leg Extension', 'Extensión de piernas'),
(9, gen_random_uuid(),'Cadeira flexora', 'Leg Curl', 'Curl femoral'),
(10,gen_random_uuid(), 'Crucifixo', 'Dumbbell Fly', 'Aperturas con mancuernas');
-- Adicione mais exercícios conforme necessário

-- Inserção de Exercícios para Treinamento Funcional (id=2)
INSERT INTO exercise (id, image_uuid, name_pt, name_en, name_es) VALUES
(11, gen_random_uuid(), 'Burpee', 'Burpee', 'Burpee'),
(12, gen_random_uuid(), 'Agachamento com kettlebell', 'Goblet Squat', 'Sentadilla con kettlebell'),
(13, gen_random_uuid(), 'Swing com kettlebell', 'Kettlebell Swing', 'Balanceo con kettlebell'),
(14, gen_random_uuid(), 'Prancha frontal', 'Plank', 'Plancha'),
(15, gen_random_uuid(), 'Saltos no caixote', 'Box Jump', 'Saltos al cajón'),
(16, gen_random_uuid(), 'Avanço com halteres', 'Dumbbell Lunge', 'Zancadas con mancuernas'),
(17, gen_random_uuid(), 'Battle rope', 'Battle Rope', 'Cuerdas de combate'),
(18, gen_random_uuid(), 'Escada de agilidade', 'Agility Ladder', 'Escalera de agilidad'),
(19, gen_random_uuid(), 'Corrida em zigue-zague', 'Zigzag Run', 'Carrera en zigzag'),
(20, gen_random_uuid(), 'Prancha lateral', 'Side Plank', 'Plancha lateral');
-- Adicione mais exercícios conforme necessário

-- Inserção de Exercícios para Yoga (id=3)
INSERT INTO exercise (id, image_uuid, name_pt, name_en, name_es) VALUES
(21, gen_random_uuid(), 'Postura da criança', 'Child''s Pose', 'Postura del niño'),
(22, gen_random_uuid(), 'Cão olhando para baixo', 'Downward-Facing Dog', 'Perro boca abajo'),
(23, gen_random_uuid(), 'Postura da cobra', 'Cobra Pose', 'Postura de la cobra'),
(24, gen_random_uuid(), 'Postura da montanha', 'Mountain Pose', 'Postura de la montaña'),
(25, gen_random_uuid(), 'Postura do guerreiro I', 'Warrior I Pose', 'Guerrero I'),
(26, gen_random_uuid(), 'Postura do guerreiro II', 'Warrior II Pose', 'Guerrero II'),
(27, gen_random_uuid(), 'Postura do triângulo', 'Triangle Pose', 'Postura del triángulo'),
(28, gen_random_uuid(), 'Postura da árvore', 'Tree Pose', 'Postura del árbol'),
(29, gen_random_uuid(), 'Postura do cadáver', 'Corpse Pose', 'Postura del cadáver'),
(30, gen_random_uuid(), 'Torção sentada', 'Seated Twist', 'Torsión sentada');
-- Adicione mais exercícios conforme necessário

-- Inserção de Exercícios para Pilates (id=4)
INSERT INTO exercise (id, image_uuid, name_pt, name_en, name_es) VALUES
(31, gen_random_uuid(), 'The Hundred', 'The Hundred', 'El Cien'),
(32, gen_random_uuid(), 'Roll Up', 'Roll Up', 'Rodar hacia arriba'),
(33, gen_random_uuid(), 'Single Leg Stretch', 'Single Leg Stretch', 'Estiramiento de una pierna'),
(34, gen_random_uuid(), 'Double Leg Stretch', 'Double Leg Stretch', 'Estiramiento de dos piernas'),
(35, gen_random_uuid(), 'Leg Circles', 'Leg Circles', 'Círculos de piernas'),
(36, gen_random_uuid(), 'Spine Stretch', 'Spine Stretch', 'Estiramiento de columna'),
(37, gen_random_uuid(), 'Teaser', 'Teaser', 'Teaser'),
(38, gen_random_uuid(), 'Saw', 'Saw', 'Sierra'),
(39, gen_random_uuid(), 'Swimming', 'Swimming', 'Natación'),
(40, gen_random_uuid(), 'Shoulder Bridge', 'Shoulder Bridge', 'Puente de hombros');
-- Adicione mais exercícios conforme necessário

-- Inserção de Exercícios para CrossFit (id=5)
INSERT INTO exercise (id, image_uuid, name_pt, name_en, name_es) VALUES
(41, gen_random_uuid(), 'Snatch', 'Snatch', 'Arranque'),
(42, gen_random_uuid(), 'Clean and Jerk', 'Clean and Jerk', 'Dos tiempos'),
(43, gen_random_uuid(), 'Thruster', 'Thruster', 'Thruster'),
(44, gen_random_uuid(), 'Wall Ball', 'Wall Ball', 'Lanzamiento a la pared'),
(45, gen_random_uuid(), 'Box Jump', 'Box Jump', 'Salto al cajón'),
(46, gen_random_uuid(), 'Double Under', 'Double Under', 'Doble salto de cuerda'),
(47, gen_random_uuid(), 'Toes to Bar', 'Toes to Bar', 'Pies a la barra'),
(48, gen_random_uuid(), 'Kipping Pull-up', 'Kipping Pull-up', 'Dominadas con impulso'),
(49, gen_random_uuid(), 'Handstand Push-up', 'Handstand Push-up', 'Flexión de parada de manos'),
(50, gen_random_uuid(), 'Overhead Squat', 'Overhead Squat', 'Sentadilla sobre la cabeza');
-- Adicione mais exercícios conforme necessário

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
INSERT INTO program_template (version, modality_id,goal_id, program_id,  work_group_id, exercise_id,   execution, execution_time, rest_time, weight, weight_unit, comments, status, created_at, updated_at) VALUES
(1, 1, 1, 57,  1,   1, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(1, 1, 1, 57,  1,  10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(1, 1, 1, 57,  1,  94, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(1, 1, 1, 57,  3, 122, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(1, 1, 1, 57,  3, 131, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(1, 1, 1, 57,  3, 127, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(1, 1, 1, 57,  6,   6, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(1, 1, 1, 57,  6, 151, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(1, 1, 1, 57,  6, 155, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(1, 1, 1, 58,  2, 106, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(1, 1, 1, 58,  2, 107, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(1, 1, 1, 58,  5,   5, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(1, 1, 1, 58,  5, 136, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(1, 1, 1, 58,  5, 137, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(1, 1, 1, 58,  5, 140, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(1, 1, 1, 58, 13, 206, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(1, 1, 1, 58, 13, 207, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(1, 1, 1, 58, 13, 208, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(1, 1, 1, 58, 13, 210, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(1, 1, 1, 59,  9,   8, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(1, 1, 1, 59,  9,   2, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(1, 1, 1, 59,  9,  43, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(1, 1, 1, 59,  9,  54, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(1, 1, 1, 59, 10,   9, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(1, 1, 1, 59, 10,  61, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(1, 1, 1, 59, 10,  63, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(1, 1, 1, 59, 11,  71, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(1, 1, 1, 59, 11,  73, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(1, 1, 1, 59,  8, 181, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(1, 1, 1, 59,  8, 183, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(1, 1, 1, 59,  7,  14, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(1, 1, 1, 59,  7,  20, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(1, 1, 1, 59,  7, 166, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(1, 1, 1, 59,  7, 168, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW())
;

-- ABC Training version 2
INSERT INTO program_template (version, modality_id,goal_id, program_id,  work_group_id, exercise_id,   execution, execution_time, rest_time, weight, weight_unit, comments, status, created_at, updated_at) VALUES
(2, 1, 1, 57,  1,   1, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(2, 1, 1, 57,  1,  10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(2, 1, 1, 57,  1,  94, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(2, 1, 1, 57,  3, 122, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(2, 1, 1, 57,  3, 131, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(2, 1, 1, 57,  3, 127, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(2, 1, 1, 57,  6,   6, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(2, 1, 1, 57,  6, 151, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(2, 1, 1, 57,  6, 155, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(2, 1, 1, 58,  2, 106, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(2, 1, 1, 58,  2, 107, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(2, 1, 1, 58,  5,   5, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(2, 1, 1, 58,  5, 136, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(2, 1, 1, 58,  5, 137, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(2, 1, 1, 58,  5, 140, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(2, 1, 1, 58, 13, 206, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(2, 1, 1, 58, 13, 207, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(2, 1, 1, 58, 13, 208, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(2, 1, 1, 58, 13, 210, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(2, 1, 1, 59,  9,   8, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(2, 1, 1, 59,  9,   2, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(2, 1, 1, 59,  9,  43, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(2, 1, 1, 59,  9,  54, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(2, 1, 1, 59, 10,   9, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(2, 1, 1, 59, 10,  61, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(2, 1, 1, 59, 10,  63, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(2, 1, 1, 59, 11,  71, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(2, 1, 1, 59, 11,  73, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(2, 1, 1, 59,  8, 181, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(2, 1, 1, 59,  8, 183, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(2, 1, 1, 59,  7,  14, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(2, 1, 1, 59,  7,  20, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(2, 1, 1, 59,  7, 166, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(2, 1, 1, 59,  7, 168, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW())
;

-- ABC Training version 3
INSERT INTO program_template (version, modality_id,goal_id, program_id,  work_group_id, exercise_id,   execution, execution_time, rest_time, weight, weight_unit, comments, status, created_at, updated_at) VALUES
(3, 1, 1, 57,  1,   1, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(3, 1, 1, 57,  1,  10, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(3, 1, 1, 57,  1,  94, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(3, 1, 1, 57,  3, 122, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(3, 1, 1, 57,  3, 131, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(3, 1, 1, 57,  3, 127, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(3, 1, 1, 57,  6,   6, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(3, 1, 1, 57,  6, 151, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(3, 1, 1, 57,  6, 155, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(3, 1, 1, 58,  2, 106, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(3, 1, 1, 58,  2, 107, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(3, 1, 1, 58,  5,   5, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(3, 1, 1, 58,  5, 136, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(3, 1, 1, 58,  5, 137, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(3, 1, 1, 58,  5, 140, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(3, 1, 1, 58, 13, 206, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(3, 1, 1, 58, 13, 207, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(3, 1, 1, 58, 13, 208, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(3, 1, 1, 58, 13, 210, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(3, 1, 1, 59,  9,   8, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(3, 1, 1, 59,  9,   2, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(3, 1, 1, 59,  9,  43, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(3, 1, 1, 59,  9,  54, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(3, 1, 1, 59, 10,   9, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(3, 1, 1, 59, 10,  61, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(3, 1, 1, 59, 10,  63, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(3, 1, 1, 59, 11,  71, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(3, 1, 1, 59, 11,  73, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(3, 1, 1, 59,  8, 181, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(3, 1, 1, 59,  8, 183, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(3, 1, 1, 59,  7,  14, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(3, 1, 1, 59,  7,  20, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(3, 1, 1, 59,  7, 166, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW()),
(3, 1, 1, 59,  7, 168, '3s x 10rep', null, '60s', 20, 'kg', null, 'A', NOW(), NOW())
;

-- adjusting execution method for modality  (1-Bodybuilding) and goal (1-Hypertrophy)
update program_template set execution_method = 'TRADITIONAL_SET' where modality_id = 1 and goal_id = 1;


