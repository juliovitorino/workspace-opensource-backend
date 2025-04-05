-- Users Table

CREATE TABLE users (
    id bigserial PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    middle_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    cellphone VARCHAR(20),
    birthday DATE,
    gender VARCHAR(1) DEFAULT 'M' CHECK (gender IN ('M', 'F')),
    url_photo_profile TEXT,
    user_profile VARCHAR(50) DEFAULT 'PERSONAL_TRAINER' CHECK (user_profile IN ('PERSONAL_TRAINER', 'STUDENT')),
    master_language VARCHAR(10) DEFAULT 'pt-BR' CHECK (master_language IN ('pt-BR', 'en-US', 'es-ES')),
    guardian_integration UUID UNIQUE NOT NULL,
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
    start_time VARCHAR(5),
    end_time VARCHAR(5),
    days_of_week VARCHAR(14) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Comentários sobre a tabela e colunas
COMMENT ON TABLE user_pack_training IS 'Tabela que representa os pacotes de treinos entre personal trainer e aluno.';

COMMENT ON COLUMN user_pack_training.id IS 'Identificador único do pacote de treino.';
COMMENT ON COLUMN user_pack_training.personal_user_id IS 'ID do usuário que atua como personal trainer. Referência à tabela users.';
COMMENT ON COLUMN user_pack_training.student_user_id IS 'ID do usuário que atua como aluno. Referência à tabela users.';
COMMENT ON COLUMN user_pack_training.description IS 'Descrição do pacote de treino.';
COMMENT ON COLUMN user_pack_training.price IS 'Preço do pacote em moeda decimal (R$).';
COMMENT ON COLUMN user_pack_training.modality_id IS 'ID da modalidade associada ao pacote. Referência à tabela modality.';
COMMENT ON COLUMN user_pack_training.start_time IS 'Horário de início do treino (formato HH:MM).';
COMMENT ON COLUMN user_pack_training.end_time IS 'Horário de término do treino (formato HH:MM).';
COMMENT ON COLUMN user_pack_training.days_of_week IS 'Dias da semana em que o treino será realizado. Ex: 0=Domingo,1=Segunda,...';
COMMENT ON COLUMN user_pack_training.created_at IS 'Data e hora de criação do registro.';
COMMENT ON COLUMN user_pack_training.updated_at IS 'Data e hora da última atualização do registro.';