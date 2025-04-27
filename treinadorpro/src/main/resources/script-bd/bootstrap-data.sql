-- insert parameters
INSERT INTO parameters (keytag, valuetag) VALUES
    ('ACTIVE_ENVIRONMENT', 'DEVELOPMENT')
;

INSERT INTO goal (name_pt, name_en, name_es, status)
VALUES
('Hipertrofia', 'Hypertrophy', 'Hipertrofia', 'A'),
('Perda de Peso', 'Weight Loss', 'Pérdida de Peso', 'A'),
('Emagrecimento', 'Slimming', 'Adelgazamiento', 'A'),
('Definição Muscular', 'Muscle Definition', 'Definición Muscular', 'A'),
('Condicionamento Físico', 'Physical Conditioning', 'Condicionamiento Físico', 'A'),
('Ganho de Força', 'Strength Gain', 'Ganancia de Fuerza', 'A'),
('Resistência Cardiorrespiratória', 'Cardiorespiratory Endurance', 'Resistencia Cardiorrespiratoria', 'A'),
('Flexibilidade', 'Flexibility', 'Flexibilidad', 'A'),
('Melhora da Mobilidade', 'Mobility Improvement', 'Mejora de la Movilidad', 'A'),
('Reabilitação Física', 'Physical Rehabilitation', 'Rehabilitación Física', 'A'),
('Prevenção de Lesões', 'Injury Prevention', 'Prevención de Lesiones', 'A'),
('Manutenção da Saúde', 'Health Maintenance', 'Mantenimiento de la Salud', 'A'),
('Preparação para Corridas', 'Race Preparation', 'Preparación para Carreras', 'A'),
('Aumento de Energia', 'Energy Boost', 'Aumento de Energía', 'A');

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

INSERT INTO program (name_pt, name_en, name_es, status)
VALUES
-- Hipertrofia
('Programa de Hipertrofia 18-30 anos', 'Hypertrophy Program 18-30 years', 'Programa de Hipertrofia 18-30 años', 'A'),
('Programa de Hipertrofia 31-45 anos', 'Hypertrophy Program 31-45 years', 'Programa de Hipertrofia 31-45 años', 'A'),
('Programa de Hipertrofia 46-55 anos', 'Hypertrophy Program 46-55 years', 'Programa de Hipertrofia 46-55 años', 'A'),
('Programa de Hipertrofia 55+ anos', 'Hypertrophy Program 55+ years', 'Programa de Hipertrofia 55+ años', 'A'),

-- Perda de Peso
('Programa de Perda de Peso 18-30 anos', 'Weight Loss Program 18-30 years', 'Programa de Pérdida de Peso 18-30 años', 'A'),
('Programa de Perda de Peso 31-45 anos', 'Weight Loss Program 31-45 years', 'Programa de Pérdida de Peso 31-45 años', 'A'),
('Programa de Perda de Peso 46-55 anos', 'Weight Loss Program 46-55 years', 'Programa de Pérdida de Peso 46-55 años', 'A'),
('Programa de Perda de Peso 55+ anos', 'Weight Loss Program 55+ years', 'Programa de Pérdida de Peso 55+ años', 'A'),

-- Emagrecimento
('Programa de Emagrecimento 18-30 anos', 'Slimming Program 18-30 years', 'Programa de Adelgazamiento 18-30 años', 'A'),
('Programa de Emagrecimento 31-45 anos', 'Slimming Program 31-45 years', 'Programa de Adelgazamiento 31-45 años', 'A'),
('Programa de Emagrecimento 46-55 anos', 'Slimming Program 46-55 years', 'Programa de Adelgazamiento 46-55 años', 'A'),
('Programa de Emagrecimento 55+ anos', 'Slimming Program 55+ years', 'Programa de Adelgazamiento 55+ años', 'A'),

-- Definição Muscular
('Programa de Definição Muscular 18-30 anos', 'Muscle Definition Program 18-30 years', 'Programa de Definición Muscular 18-30 años', 'A'),
('Programa de Definição Muscular 31-45 anos', 'Muscle Definition Program 31-45 years', 'Programa de Definición Muscular 31-45 años', 'A'),
('Programa de Definição Muscular 46-55 anos', 'Muscle Definition Program 46-55 years', 'Programa de Definición Muscular 46-55 años', 'A'),
('Programa de Definição Muscular 55+ anos', 'Muscle Definition Program 55+ years', 'Programa de Definición Muscular 55+ años', 'A'),

-- Condicionamento Físico
('Programa de Condicionamento Físico 18-30 anos', 'Physical Conditioning Program 18-30 years', 'Programa de Acondicionamiento Físico 18-30 años', 'A'),
('Programa de Condicionamento Físico 31-45 anos', 'Physical Conditioning Program 31-45 years', 'Programa de Acondicionamiento Físico 31-45 años', 'A'),
('Programa de Condicionamento Físico 46-55 anos', 'Physical Conditioning Program 46-55 years', 'Programa de Acondicionamiento Físico 46-55 años', 'A'),
('Programa de Condicionamento Físico 55+ anos', 'Physical Conditioning Program 55+ years', 'Programa de Acondicionamiento Físico 55+ años', 'A'),

-- Ganho de Força
('Programa de Ganho de Força 18-30 anos', 'Strength Gain Program 18-30 years', 'Programa de Ganancia de Fuerza 18-30 años', 'A'),
('Programa de Ganho de Força 31-45 anos', 'Strength Gain Program 31-45 years', 'Programa de Ganancia de Fuerza 31-45 años', 'A'),
('Programa de Ganho de Força 46-55 anos', 'Strength Gain Program 46-55 years', 'Programa de Ganancia de Fuerza 46-55 años', 'A'),
('Programa de Ganho de Força 55+ anos', 'Strength Gain Program 55+ years', 'Programa de Ganancia de Fuerza 55+ años', 'A'),

-- Resistência Cardiorrespiratória
('Programa de Resistência Cardiorrespiratória 18-30 anos', 'Cardiorespiratory Endurance Program 18-30 years', 'Programa de Resistencia Cardiorrespiratoria 18-30 años', 'A'),
('Programa de Resistência Cardiorrespiratória 31-45 anos', 'Cardiorespiratory Endurance Program 31-45 years', 'Programa de Resistencia Cardiorrespiratoria 31-45 años', 'A'),
('Programa de Resistência Cardiorrespiratória 46-55 anos', 'Cardiorespiratory Endurance Program 46-55 years', 'Programa de Resistencia Cardiorrespiratoria 46-55 años', 'A'),
('Programa de Resistência Cardiorrespiratória 55+ anos', 'Cardiorespiratory Endurance Program 55+ years', 'Programa de Resistencia Cardiorrespiratoria 55+ años', 'A'),

-- Flexibilidade
('Programa de Flexibilidade 18-30 anos', 'Flexibility Program 18-30 years', 'Programa de Flexibilidad 18-30 años', 'A'),
('Programa de Flexibilidade 31-45 anos', 'Flexibility Program 31-45 years', 'Programa de Flexibilidad 31-45 años', 'A'),
('Programa de Flexibilidade 46-55 anos', 'Flexibility Program 46-55 years', 'Programa de Flexibilidad 46-55 años', 'A'),
('Programa de Flexibilidade 55+ anos', 'Flexibility Program 55+ years', 'Programa de Flexibilidad 55+ años', 'A'),

-- Melhora da Mobilidade
('Programa de Melhora da Mobilidade 18-30 anos', 'Mobility Improvement Program 18-30 years', 'Programa de Mejora de la Movilidad 18-30 años', 'A'),
('Programa de Melhora da Mobilidade 31-45 anos', 'Mobility Improvement Program 31-45 years', 'Programa de Mejora de la Movilidad 31-45 años', 'A'),
('Programa de Melhora da Mobilidade 46-55 anos', 'Mobility Improvement Program 46-55 years', 'Programa de Mejora de la Movilidad 46-55 años', 'A'),
('Programa de Melhora da Mobilidade 55+ anos', 'Mobility Improvement Program 55+ years', 'Programa de Mejora de la Movilidad 55+ años', 'A'),

-- Reabilitação Física
('Programa de Reabilitação Física 18-30 anos', 'Physical Rehabilitation Program 18-30 years', 'Programa de Rehabilitación Física 18-30 años', 'A'),
('Programa de Reabilitação Física 31-45 anos', 'Physical Rehabilitation Program 31-45 years', 'Programa de Rehabilitación Física 31-45 años', 'A'),
('Programa de Reabilitação Física 46-55 anos', 'Physical Rehabilitation Program 46-55 years', 'Programa de Rehabilitación Física 46-55 años', 'A'),
('Programa de Reabilitação Física 55+ anos', 'Physical Rehabilitation Program 55+ years', 'Programa de Rehabilitación Física 55+ años', 'A'),

-- Prevenção de Lesões
('Programa de Prevenção de Lesões 18-30 anos', 'Injury Prevention Program 18-30 years', 'Programa de Prevención de Lesiones 18-30 años', 'A'),
('Programa de Prevenção de Lesões 31-45 anos', 'Injury Prevention Program 31-45 years', 'Programa de Prevención de Lesiones 31-45 años', 'A'),
('Programa de Prevenção de Lesões 46-55 anos', 'Injury Prevention Program 46-55 years', 'Programa de Prevención de Lesiones 46-55 años', 'A'),
('Programa de Prevenção de Lesões 55+ anos', 'Injury Prevention Program 55+ years', 'Programa de Prevención de Lesiones 55+ años', 'A'),

-- Manutenção da Saúde
('Programa de Manutenção da Saúde 18-30 anos', 'Health Maintenance Program 18-30 years', 'Programa de Mantenimiento de la Salud 18-30 años', 'A'),
('Programa de Manutenção da Saúde 31-45 anos', 'Health Maintenance Program 31-45 years', 'Programa de Mantenimiento de la Salud 31-45 años', 'A'),
('Programa de Manutenção da Saúde 46-55 anos', 'Health Maintenance Program 46-55 years', 'Programa de Mantenimiento de la Salud 46-55 años', 'A'),
('Programa de Manutenção da Saúde 55+ anos', 'Health Maintenance Program 55+ years', 'Programa de Mantenimiento de la Salud 55+ años', 'A'),

-- Preparação para Corridas
('Programa de Preparação para Corridas 18-30 anos', 'Running Preparation Program 18-30 years', 'Programa de Preparación para Carreras 18-30 años', 'A'),
('Programa de Preparação para Corridas 31-45 anos', 'Running Preparation Program 31-45 years', 'Programa de Preparación para Carreras 31-45 años', 'A'),
('Programa de Preparação para Corridas 46-55 anos', 'Running Preparation Program 46-55 years', 'Programa de Preparación para Carreras 46-55 años', 'A'),
('Programa de Preparação para Corridas 55+ anos', 'Running Preparation Program 55+ years', 'Programa de Preparación para Carreras 55+ años', 'A'),

-- Aumento de Energia
('Programa de Aumento de Energia 18-30 anos', 'Energy Boost Program 18-30 years', 'Programa de Aumento de Energía 18-30 años', 'A'),
('Programa de Aumento de Energia 31-45 anos', 'Energy Boost Program 31-45 years', 'Programa de Aumento de Energía 31-45 años', 'A'),
('Programa de Aumento de Energia 46-55 anos', 'Energy Boost Program 46-55 years', 'Programa de Aumento de Energía 46-55 años', 'A'),
('Programa de Aumento de Energia 55+ anos', 'Energy Boost Program 55+ years', 'Programa de Aumento de Energía 55+ años', 'A');

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
INSERT INTO exercise (id, name_pt, name_en, name_es) VALUES
(1, 'Supino reto', 'Flat Bench Press', 'Press de banca plano'),
(2, 'Agachamento livre', 'Barbell Squat', 'Sentadilla con barra'),
(3, 'Levantamento terra', 'Deadlift', 'Peso muerto'),
(4, 'Desenvolvimento com barra', 'Overhead Press', 'Press militar'),
(5, 'Rosca direta', 'Barbell Curl', 'Curl con barra'),
(6, 'Tríceps pulley', 'Triceps Pushdown', 'Extensiones en polea'),
(7, 'Remada curvada', 'Bent-over Row', 'Remo inclinado'),
(8, 'Cadeira extensora', 'Leg Extension', 'Extensión de piernas'),
(9, 'Cadeira flexora', 'Leg Curl', 'Curl femoral'),
(10, 'Crucifixo', 'Dumbbell Fly', 'Aperturas con mancuernas');
-- Adicione mais exercícios conforme necessário

-- Inserção de Exercícios para Treinamento Funcional (id=2)
INSERT INTO exercise (id, name_pt, name_en, name_es) VALUES
(11, 'Burpee', 'Burpee', 'Burpee'),
(12, 'Agachamento com kettlebell', 'Goblet Squat', 'Sentadilla con kettlebell'),
(13, 'Swing com kettlebell', 'Kettlebell Swing', 'Balanceo con kettlebell'),
(14, 'Prancha frontal', 'Plank', 'Plancha'),
(15, 'Saltos no caixote', 'Box Jump', 'Saltos al cajón'),
(16, 'Avanço com halteres', 'Dumbbell Lunge', 'Zancadas con mancuernas'),
(17, 'Battle rope', 'Battle Rope', 'Cuerdas de combate'),
(18, 'Escada de agilidade', 'Agility Ladder', 'Escalera de agilidad'),
(19, 'Corrida em zigue-zague', 'Zigzag Run', 'Carrera en zigzag'),
(20, 'Prancha lateral', 'Side Plank', 'Plancha lateral');
-- Adicione mais exercícios conforme necessário

-- Inserção de Exercícios para Yoga (id=3)
INSERT INTO exercise (id, name_pt, name_en, name_es) VALUES
(21, 'Postura da criança', 'Child''s Pose', 'Postura del niño'),
(22, 'Cão olhando para baixo', 'Downward-Facing Dog', 'Perro boca abajo'),
(23, 'Postura da cobra', 'Cobra Pose', 'Postura de la cobra'),
(24, 'Postura da montanha', 'Mountain Pose', 'Postura de la montaña'),
(25, 'Postura do guerreiro I', 'Warrior I Pose', 'Guerrero I'),
(26, 'Postura do guerreiro II', 'Warrior II Pose', 'Guerrero II'),
(27, 'Postura do triângulo', 'Triangle Pose', 'Postura del triángulo'),
(28, 'Postura da árvore', 'Tree Pose', 'Postura del árbol'),
(29, 'Postura do cadáver', 'Corpse Pose', 'Postura del cadáver'),
(30, 'Torção sentada', 'Seated Twist', 'Torsión sentada');
-- Adicione mais exercícios conforme necessário

-- Inserção de Exercícios para Pilates (id=4)
INSERT INTO exercise (id, name_pt, name_en, name_es) VALUES
(31, 'The Hundred', 'The Hundred', 'El Cien'),
(32, 'Roll Up', 'Roll Up', 'Rodar hacia arriba'),
(33, 'Single Leg Stretch', 'Single Leg Stretch', 'Estiramiento de una pierna'),
(34, 'Double Leg Stretch', 'Double Leg Stretch', 'Estiramiento de dos piernas'),
(35, 'Leg Circles', 'Leg Circles', 'Círculos de piernas'),
(36, 'Spine Stretch', 'Spine Stretch', 'Estiramiento de columna'),
(37, 'Teaser', 'Teaser', 'Teaser'),
(38, 'Saw', 'Saw', 'Sierra'),
(39, 'Swimming', 'Swimming', 'Natación'),
(40, 'Shoulder Bridge', 'Shoulder Bridge', 'Puente de hombros');
-- Adicione mais exercícios conforme necessário

-- Inserção de Exercícios para CrossFit (id=5)
INSERT INTO exercise (id, name_pt, name_en, name_es) VALUES
(41, 'Snatch', 'Snatch', 'Arranque'),
(42, 'Clean and Jerk', 'Clean and Jerk', 'Dos tiempos'),
(43, 'Thruster', 'Thruster', 'Thruster'),
(44, 'Wall Ball', 'Wall Ball', 'Lanzamiento a la pared'),
(45, 'Box Jump', 'Box Jump', 'Salto al cajón'),
(46, 'Double Under', 'Double Under', 'Doble salto de cuerda'),
(47, 'Toes to Bar', 'Toes to Bar', 'Pies a la barra'),
(48, 'Kipping Pull-up', 'Kipping Pull-up', 'Dominadas con impulso'),
(49, 'Handstand Push-up', 'Handstand Push-up', 'Flexión de parada de manos'),
(50, 'Overhead Squat', 'Overhead Squat', 'Sentadilla sobre la cabeza');
-- Adicione mais exercícios conforme necessário

-- creating relationship between bodybuilding modality and their exercises
insert into modality_exercise (modality_id,exercise_id) select 1, e.id from exercise e where e.id between 1 and 10;

-- creating relationship between 'Functional Training' and their exercises
insert into modality_exercise (modality_id,exercise_id) select 2, e.id from exercise e where e.id between 11 and 20;

-- creating relationship between 'Yoga' and their exercises
insert into modality_exercise (modality_id,exercise_id) select 3, e.id from exercise e where e.id between 21 and 30;

-- creating relationship between 'Pilates' and their exercises
insert into modality_exercise (modality_id,exercise_id) select 4, e.id from exercise e where e.id between 31 and 40;

-- creating relationship between 'CrossFit' and their exercises
insert into modality_exercise (modality_id,exercise_id) select 5, e.id from exercise e where e.id between 41 and 50;

