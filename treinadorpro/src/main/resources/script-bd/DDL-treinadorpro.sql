-- parameters table
CREATE TABLE parameters (
    id SERIAL PRIMARY KEY,
    keytag VARCHAR(500) UNIQUE NOT NULL,
    valuetag VARCHAR(2000) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- plan_template table
CREATE TABLE plan_template (
    id serial PRIMARY KEY,
    description VARCHAR(500) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    payment_frequency VARCHAR(10) DEFAULT 'MONTHLY' check (payment_frequency in ('MONTHLY', 'ANNUALLY')),
    status VARCHAR(1) DEFAULT 'A' CHECK (status IN ('A', 'B', 'I','P')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


-- active_personal_plan table
CREATE TABLE active_personal_plan (
    id serial PRIMARY KEY,
    plan_template_id INTEGER NOT NULL REFERENCES plan_template(id) ON DELETE CASCADE,
    personal_user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    description VARCHAR(500) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    amount_discount DECIMAL(10,2) NOT NULL,
    plan_expiration_date DATE NOT NULL,
    payment_frequency VARCHAR(10) DEFAULT 'MONTHLY' check (payment_frequency in ('MONTHLY', 'ANNUALLY')),
    qty_user_pack_training_allowed INTEGER DEFAULT 0 NOT NULL,
    status VARCHAR(1) DEFAULT 'A' CHECK (status IN ('A', 'B', 'I','P')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


-- personal_trainer_payments table
CREATE TABLE personal_trainer_payments (
    id serial PRIMARY KEY,
    active_personal_plan_id INTEGER NOT NULL REFERENCES active_personal_plan(id) ON DELETE CASCADE,
    expected_amount DECIMAL(10,2) NOT NULL,
    expected_date DATE NOT NULL,
    amount_discount DECIMAL(10,2) NOT NULL,
    amount_paid DECIMAL(10,2) NOT NULL,
    paid_date DATE NOT NULL,
    status VARCHAR(1) DEFAULT 'A' CHECK (status IN ('A', 'B', 'I','P')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Users Table

CREATE TABLE users (
    id bigserial PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    middle_name VARCHAR(100),
    last_name VARCHAR(100),
    email VARCHAR(150) UNIQUE NOT NULL,
    cellphone VARCHAR(20),
    birthday DATE,
    gender VARCHAR(1) CHECK (gender IN ('M', 'F')),
    url_photo_profile TEXT,
    user_profile VARCHAR(50) NOT NULL DEFAULT 'PERSONAL_TRAINER' CHECK (user_profile IN ('ADMIN','PERSONAL_TRAINER', 'STUDENT')),
    master_language VARCHAR(10) NOT NULL DEFAULT 'pt-BR' CHECK (master_language IN ('pt-BR', 'en-US', 'es-ES')),
    guardian_integration UUID,
    status VARCHAR(1) DEFAULT 'A' CHECK (status IN ('A', 'B', 'I','P')),
    last_login TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON COLUMN users.id IS 'Unique identifier for the user (primary key)';
COMMENT ON COLUMN users.first_name IS 'First name of the user';
COMMENT ON COLUMN users.middle_name IS 'Middle name of the user';
COMMENT ON COLUMN users.last_name IS 'Last name of the user';
COMMENT ON COLUMN users.email IS 'User email address (must be unique)';
COMMENT ON COLUMN users.cellphone IS 'User cellphone number';
COMMENT ON COLUMN users.birthday IS 'User''s date of birth';
COMMENT ON COLUMN users.gender IS 'User gender: F = Female ou M = Male)';
COMMENT ON COLUMN users.url_photo_profile IS 'URL of the user''s profile photo';
COMMENT ON COLUMN users.user_profile IS 'Type of user profile: PERSONAL_TRAINER or STUDENT';
COMMENT ON COLUMN users.master_language IS 'User''s preferred language (e.g., pt-BR)';
COMMENT ON COLUMN users.guardian_integration IS 'Guardian user link integration';
COMMENT ON COLUMN users.status IS 'User status: A = Active, B = Blocked, I = Inactive, P = Pending';
COMMENT ON COLUMN users.last_login IS 'Timestamp of the user''s last login';
COMMENT ON COLUMN users.created_at IS 'Timestamp of when the user was created';
COMMENT ON COLUMN users.updated_at IS 'Timestamp of the last update to the user record';


CREATE TABLE student_feature (
    id bigserial PRIMARY KEY,
    student_user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    password VARCHAR(100) NOT NULL,
    height INTEGER,
    weight DECIMAL(10,2),
    weight_unit VARCHAR(10),
    status VARCHAR(1) DEFAULT 'A' CHECK (status IN ('A', 'B', 'I','P')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);



CREATE TABLE personal_feature (
    id bigserial PRIMARY KEY,
    personal_user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    mon_period VARCHAR(11),
    tue_period VARCHAR(11),
    wed_period VARCHAR(11),
    thu_period VARCHAR(11),
    fri_period VARCHAR(11),
    sat_period VARCHAR(11),
    sun_period VARCHAR(11),
    status VARCHAR(1) DEFAULT 'A' CHECK (status IN ('A', 'B', 'I','P')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


-- Modality table
CREATE TABLE modality (
    id SERIAL PRIMARY KEY,
    name_pt VARCHAR(100),
    name_en VARCHAR(100),
    name_es VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE modality IS 'Tabela que armazena as modalidades disponíveis no sistema.';
COMMENT ON COLUMN modality.id IS 'Identificador único da modalidade.';
COMMENT ON COLUMN modality.name_pt IS 'Nome da modalidade em português.';
COMMENT ON COLUMN modality.name_en IS 'Nome da modalidade em inglês.';
COMMENT ON COLUMN modality.name_es IS 'Nome da modalidade em espanhol.';
COMMENT ON COLUMN modality.created_at IS 'Data e hora de criação do registro.';
COMMENT ON COLUMN modality.updated_at IS 'Data e hora da última atualização do registro.';

-- Exercise table
CREATE TABLE exercise (
    id SERIAL PRIMARY KEY,
    name_pt VARCHAR(100),
    name_en VARCHAR(100),
    name_es VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE exercise IS 'Tabela que armazena os exercícios cadastrados.';
COMMENT ON COLUMN exercise.id IS 'Identificador único do exercício.';
COMMENT ON COLUMN exercise.name_pt IS 'Nome do exercício em português.';
COMMENT ON COLUMN exercise.name_en IS 'Nome do exercício em inglês.';
COMMENT ON COLUMN exercise.name_es IS 'Nome do exercício em espanhol.';
COMMENT ON COLUMN exercise.created_at IS 'Data e hora de criação do registro.';
COMMENT ON COLUMN exercise.updated_at IS 'Data e hora da última atualização do registro.';

-- Modality x Exercise relationship table
CREATE TABLE modality_exercise (
    id SERIAL PRIMARY KEY,
    modality_id INTEGER NOT NULL REFERENCES modality(id) ON DELETE CASCADE,
    exercise_id INTEGER NOT NULL REFERENCES exercise(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE modality_exercise IS 'Tabela que relaciona modalidades aos seus respectivos exercícios.';
COMMENT ON COLUMN modality_exercise.id IS 'Identificador único do relacionamento.';
COMMENT ON COLUMN modality_exercise.modality_id IS 'Chave estrangeira que referencia a tabela modality.';
COMMENT ON COLUMN modality_exercise.exercise_id IS 'Chave estrangeira que referencia a tabela exercise.';
COMMENT ON COLUMN modality_exercise.created_at IS 'Data e hora de criação do relacionamento.';
COMMENT ON COLUMN modality_exercise.updated_at IS 'Data e hora da última atualização do relacionamento.';

-- User pack tranning table
CREATE TABLE user_pack_training (
    id SERIAL PRIMARY KEY,
    personal_user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    student_user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    description VARCHAR(255) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    modality_id INTEGER NOT NULL REFERENCES modality(id) ON DELETE CASCADE,
    start_time VARCHAR(5) NOT NULL,
    end_time VARCHAR(5) NOT NULL,
    days_of_week VARCHAR(14) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Comments for user_pack_training
COMMENT ON TABLE user_pack_training IS 'Table that represents training packages between personal trainers and students.';

COMMENT ON COLUMN user_pack_training.id IS 'Unique identifier of the training package.';
COMMENT ON COLUMN user_pack_training.personal_user_id IS 'ID of the user acting as the personal trainer. References the users table.';
COMMENT ON COLUMN user_pack_training.student_user_id IS 'ID of the user acting as the student. References the users table.';
COMMENT ON COLUMN user_pack_training.description IS 'Description of the training package.';
COMMENT ON COLUMN user_pack_training.price IS 'Price of the package in decimal currency';
COMMENT ON COLUMN user_pack_training.modality_id IS 'ID of the modality associated with the package. References the modality table.';
COMMENT ON COLUMN user_pack_training.start_time IS 'Start time of the training session (format HH:MM).';
COMMENT ON COLUMN user_pack_training.end_time IS 'End time of the training session (format HH:MM).';
COMMENT ON COLUMN user_pack_training.days_of_week IS 'Days of the week when the training will take place. E.g.: 0=Sunday, 1=Monday, ...';
COMMENT ON COLUMN user_pack_training.created_at IS 'Date and time when the record was created.';
COMMENT ON COLUMN user_pack_training.updated_at IS 'Date and time of the last update to the record.';

-- student payments
CREATE TABLE student_payments(
    id serial PRIMARY KEY,
    user_pack_training_id INTEGER NOT NULL REFERENCES user_pack_training(id) ON DELETE CASCADE,
    amount DECIMAL(10,2) NOT NULL,
    expected_date DATE NOT NULL,
    payment_date DATE,
    status VARCHAR(1) DEFAULT 'A' CHECK (status IN ('A', 'B', 'I','P')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP

);

-- User Workout Calendar
CREATE TABLE user_workout_calendar (
    id SERIAL PRIMARY KEY,
    user_pack_training_id INTEGER NOT NULL REFERENCES user_pack_training(id) ON DELETE CASCADE,
    modality_exercise_id INTEGER NOT NULL REFERENCES modality_exercise(id) ON DELETE CASCADE,
    training_date DATE NOT NULL,
    start_time VARCHAR(5) NOT NULL,
    end_time VARCHAR(5) NOT NULL,
    execution VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table comment
COMMENT ON TABLE user_workout_calendar IS 'Stores scheduled workouts for users, including modality, date, time, and execution notes.';

-- Column comments
COMMENT ON COLUMN user_workout_calendar.id IS 'Primary key for the user workout calendar entry.';
COMMENT ON COLUMN user_workout_calendar.user_pack_training_id IS 'Foreign key referencing the user_pack_training table, indicating which training pack this workout is part of.';
COMMENT ON COLUMN user_workout_calendar.modality_exercise_id IS 'Foreign key referencing the modality_exercise table, specifying the exercise modality.';
COMMENT ON COLUMN user_workout_calendar.training_date IS 'Date on which the workout is scheduled to occur.';
COMMENT ON COLUMN user_workout_calendar.start_time IS 'Scheduled start time of the workout (HH:MM).';
COMMENT ON COLUMN user_workout_calendar.end_time IS 'Scheduled end time of the workout (HH:MM).';
COMMENT ON COLUMN user_workout_calendar.execution IS 'Details or notes regarding the execution of the workout.';
