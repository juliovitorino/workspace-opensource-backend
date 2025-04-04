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
