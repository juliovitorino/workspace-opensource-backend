-- parameters table
CREATE TABLE parameters (
    id SERIAL PRIMARY KEY,
    external_id UUID UNIQUE NOT NULL,
    keytag VARCHAR(500) UNIQUE NOT NULL,
    valuetag VARCHAR(2000) NOT NULL,
    status VARCHAR(1) DEFAULT 'A' ,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE parameters IS 'Table that stores system parameters.';

COMMENT ON COLUMN parameters.id IS 'Unique identifier for the parameter.';
COMMENT ON COLUMN parameters.keytag IS 'Key that identifies the parameter.';
COMMENT ON COLUMN parameters.valuetag IS 'Value associated with the parameter key.';
COMMENT ON COLUMN parameters.status IS 'Status of the parameter: A (Active), B (Blocked), I (Inactive), P (Pending).';
COMMENT ON COLUMN parameters.created_at IS 'Timestamp when the parameter was created.';
COMMENT ON COLUMN parameters.updated_at IS 'Timestamp when the parameter was last updated.';

-- program table
CREATE TABLE program (
    id SERIAL PRIMARY KEY,
    external_id UUID UNIQUE NOT NULL,
    name_pt VARCHAR(100),
    name_en VARCHAR(100),
    name_es VARCHAR(100),
    status VARCHAR(1) DEFAULT 'A' ,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE program IS 'Table that stores the exercises programs';

COMMENT ON COLUMN program.id IS 'Unique identifier for the program.';
COMMENT ON COLUMN program.name_pt IS 'Program name in Portuguese. Perda de peso com calistenia';
COMMENT ON COLUMN program.name_en IS 'Program name in English, such as Fat Loss with Calisthenics';
COMMENT ON COLUMN program.name_es IS 'Program name in Spanish.';
COMMENT ON COLUMN program.status IS 'Status of the program: A (Active), B (Blocked), I (Inactive), P (Pending).';
COMMENT ON COLUMN program.created_at IS 'Timestamp when the program was created.';
COMMENT ON COLUMN program.updated_at IS 'Timestamp when the program was last updated.';

-- goal table
CREATE TABLE goal (
    id SERIAL PRIMARY KEY,
    external_id UUID UNIQUE NOT NULL,
    name_pt VARCHAR(100),
    name_en VARCHAR(100),
    name_es VARCHAR(100),
    status VARCHAR(1) DEFAULT 'A' ,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE goal IS 'Table that stores the goals with multilingual names.';

COMMENT ON COLUMN goal.id IS 'Unique identifier for the goal.';
COMMENT ON COLUMN goal.name_pt IS 'Goal name in Portuguese, ex: Perda de peso; Hipertrofia';
COMMENT ON COLUMN goal.name_en IS 'Goal name in English., i.e: Fat loss; Hypertrophy';
COMMENT ON COLUMN goal.name_es IS 'Goal name in Spanish.';
COMMENT ON COLUMN goal.status IS 'Status of the goal: A (Active), B (Blocked), I (Inactive), P (Pending).';
COMMENT ON COLUMN goal.created_at IS 'Timestamp when the goal was created.';
COMMENT ON COLUMN goal.updated_at IS 'Timestamp when the goal was last updated.';

-- work_group table
CREATE TABLE work_group (
    id SERIAL PRIMARY KEY,
    external_id UUID UNIQUE NOT NULL,
    name_pt VARCHAR(100),
    name_en VARCHAR(100),
    name_es VARCHAR(100),
    status VARCHAR(1) DEFAULT 'A' ,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE work_group IS 'Table that stores body work groups with multilingual names.';

COMMENT ON COLUMN work_group.id IS 'Unique identifier for the work group.';
COMMENT ON COLUMN work_group.name_pt IS 'Work group name in Portuguese, ex: Peitoral; Pernas; Abdominais';
COMMENT ON COLUMN work_group.name_en IS 'Work group name in English, i.e.: Chest; Legs; Abs';
COMMENT ON COLUMN work_group.name_es IS 'Work group name in Spanish.';
COMMENT ON COLUMN work_group.status IS 'Status of the work group: A (Active), B (Blocked), I (Inactive), P (Pending).';
COMMENT ON COLUMN work_group.created_at IS 'Timestamp when the work group was created.';
COMMENT ON COLUMN work_group.updated_at IS 'Timestamp when the work group was last updated.';

-- Modality table
CREATE TABLE modality (
    id SERIAL PRIMARY KEY,
    external_id UUID UNIQUE NOT NULL,
    name_pt VARCHAR(100),
    name_en VARCHAR(100),
    name_es VARCHAR(100),
    status VARCHAR(1) DEFAULT 'A' ,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE modality IS 'Table that stores the available modalities in the system.';

COMMENT ON COLUMN modality.id IS 'Unique identifier of the modality.';
COMMENT ON COLUMN modality.name_pt IS 'Name of the modality in Portuguese.';
COMMENT ON COLUMN modality.name_en IS 'Name of the modality in English.';
COMMENT ON COLUMN modality.name_es IS 'Name of the modality in Spanish.';
COMMENT ON COLUMN modality.status IS 'Status of the modality (A = Active, B = Blocked, I = Inactive, P = Pending).';
COMMENT ON COLUMN modality.created_at IS 'Timestamp when the record was created.';
COMMENT ON COLUMN modality.updated_at IS 'Timestamp when the record was last updated.';

-- Exercise table
CREATE TABLE exercise (
    id SERIAL PRIMARY KEY,
    external_id UUID UNIQUE NOT NULL,
    name_pt VARCHAR(100),
    name_en VARCHAR(100),
    name_es VARCHAR(100),
    video_url_pt VARCHAR(1000),
    video_url_en VARCHAR(1000),
    video_url_es VARCHAR(1000),
    image_uuid UUID,
    status VARCHAR(1) DEFAULT 'A' ,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE exercise IS 'Table that stores the registered exercises.';

COMMENT ON COLUMN exercise.id IS 'Unique identifier of the exercise.';
COMMENT ON COLUMN exercise.name_pt IS 'Name of the exercise in Portuguese.';
COMMENT ON COLUMN exercise.name_en IS 'Name of the exercise in English.';
COMMENT ON COLUMN exercise.name_es IS 'Name of the exercise in Spanish.';
COMMENT ON COLUMN exercise.status IS 'Status of the exercise (A = Active, B = Blocked, I = Inactive, P = Pending).';
COMMENT ON COLUMN exercise.created_at IS 'Timestamp when the record was created.';
COMMENT ON COLUMN exercise.updated_at IS 'Timestamp when the record was last updated.';

-- Exercise table
CREATE TABLE work_group_exercise (
    id SERIAL PRIMARY KEY,
    external_id UUID UNIQUE NOT NULL,
    work_group_id INTEGER NOT NULL REFERENCES work_group(id) ON DELETE CASCADE,
    exercise_id INTEGER NOT NULL REFERENCES exercise(id) ON DELETE CASCADE,
    status VARCHAR(1) DEFAULT 'A' ,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE work_group_exercise IS 'Table that links exercises to specific work groups.';

COMMENT ON COLUMN work_group_exercise.id IS 'Unique identifier for the work group exercise record.';
COMMENT ON COLUMN work_group_exercise.work_group_id IS 'Foreign key referencing the associated work group.';
COMMENT ON COLUMN work_group_exercise.exercise_id IS 'Foreign key referencing the associated exercise.';
COMMENT ON COLUMN work_group_exercise.status IS 'Status of the record: A (Active), B (Blocked), I (Inactive), P (Pending).';
COMMENT ON COLUMN work_group_exercise.created_at IS 'Timestamp indicating when the record was created.';
COMMENT ON COLUMN work_group_exercise.updated_at IS 'Timestamp indicating the last time the record was updated.';


-- plan_template table
CREATE TABLE plan_template (
    id serial PRIMARY KEY,
    external_id UUID UNIQUE NOT NULL,
    description VARCHAR(500) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    amount_discount DECIMAL(10,2) DEFAULT 0,
    payment_frequency VARCHAR(10) DEFAULT 'MONTHLY' CHECK (payment_frequency IN ('MONTHLY', 'ANNUALLY')),
    qty_contract_allowed INTEGER DEFAULT 0 NOT NULL,
    qty_user_student_allowed INTEGER DEFAULT 0 NOT NULL,
    status VARCHAR(1) DEFAULT 'A' CHECK (status IN ('A', 'B', 'I', 'P')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE plan_template IS 'Table that stores plan templates for user subscription plans.';

COMMENT ON COLUMN plan_template.id IS 'Unique identifier for the plan template.';
COMMENT ON COLUMN plan_template.description IS 'Description of the plan template. i.e.: FREEMIUM; BASIC; PRO; PREMIUM';
COMMENT ON COLUMN plan_template.price IS 'Price of the plan.';
COMMENT ON COLUMN plan_template.amount_discount IS 'Discount amount applied to the plan.';
COMMENT ON COLUMN plan_template.payment_frequency IS 'Payment frequency for the plan: MONTHLY or ANNUALLY.';
COMMENT ON COLUMN plan_template.qty_contract_allowed IS 'Number of user training packages allowed in the plan.';
COMMENT ON COLUMN plan_template.qty_user_student_allowed IS 'Number of user student allowed.';
COMMENT ON COLUMN plan_template.status IS 'Status of the plan template: A (Active), B (Blocked), I (Inactive), P (Pending).';
COMMENT ON COLUMN plan_template.created_at IS 'Timestamp when the plan template was created.';
COMMENT ON COLUMN plan_template.updated_at IS 'Timestamp when the plan template was last updated.';

-- Users Table

CREATE TABLE users (
    id bigserial PRIMARY KEY,
    uuid_id UUID UNIQUE NOT NULL,
    name VARCHAR(200) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    cellphone VARCHAR(20) NOT NULL,
    birthday DATE,
    gender VARCHAR(1) CHECK (gender IN ('M', 'F')),
    url_photo_profile TEXT,
    user_profile VARCHAR(50) NOT NULL DEFAULT 'PERSONAL_TRAINER' CHECK (user_profile IN ('ADMIN','PERSONAL_TRAINER', 'STUDENT')),
    master_language VARCHAR(10) NOT NULL DEFAULT 'pt-BR' CHECK (master_language IN ('pt-BR', 'en-US', 'es-ES')),
    guardian_integration UUID,
    last_login TIMESTAMP,
    status VARCHAR(1) DEFAULT 'A' ,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

--CREATE TABLE trainer_users (
--    id bigserial PRIMARY KEY,
--    personal_user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
--    student_user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
--    status VARCHAR(1) DEFAULT 'A' ,
--    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
--);

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

CREATE TABLE available_time (
    id serial PRIMARY KEY,
    external_id UUID UNIQUE NOT NULL,
    personal_user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    days_of_week VARCHAR(30) NOT NULL,
    day_time VARCHAR(5) NOT NULL,
    available boolean,
    status VARCHAR(1) DEFAULT 'A' CHECK (status IN ('A', 'B', 'I', 'P')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- active_personal_plan table
CREATE TABLE active_personal_plan (
    id serial PRIMARY KEY,
    external_id UUID UNIQUE NOT NULL,
    personal_user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    description VARCHAR(500) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    amount_discount DECIMAL(10,2) DEFAULT 0 NOT NULL,
    plan_expiration_date DATE NOT NULL,
    payment_frequency VARCHAR(10) DEFAULT 'MONTHLY' CHECK (payment_frequency IN ('MONTHLY', 'ANNUALLY')),
    qty_contract_allowed INTEGER DEFAULT 0 NOT NULL,
    qty_user_student_allowed INTEGER DEFAULT 0 NOT NULL,
    status VARCHAR(1) DEFAULT 'A' CHECK (status IN ('A', 'B', 'I', 'P')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE active_personal_plan IS 'Table that stores active personal plans linked to personal trainers.';

COMMENT ON COLUMN active_personal_plan.id IS 'Unique identifier for the active personal plan.';
COMMENT ON COLUMN active_personal_plan.personal_user_id IS 'Reference to the personal trainer user.';
COMMENT ON COLUMN active_personal_plan.description IS 'Description of the active personal plan.';
COMMENT ON COLUMN active_personal_plan.price IS 'Price of the active personal plan.';
COMMENT ON COLUMN active_personal_plan.amount_discount IS 'Discount amount applied to the plan.';
COMMENT ON COLUMN active_personal_plan.plan_expiration_date IS 'Expiration date of the active personal plan.';
COMMENT ON COLUMN active_personal_plan.payment_frequency IS 'Payment frequency of the plan: MONTHLY or ANNUALLY.';
COMMENT ON COLUMN active_personal_plan.qty_contract_allowed IS 'Number of user training packages allowed.';
COMMENT ON COLUMN active_personal_plan.qty_user_student_allowed IS 'Number of user student allowed.';
COMMENT ON COLUMN active_personal_plan.status IS 'Status of the active personal plan: A (Active), B (Blocked), I (Inactive), P (Pending).';
COMMENT ON COLUMN active_personal_plan.created_at IS 'Timestamp when the active personal plan was created.';
COMMENT ON COLUMN active_personal_plan.updated_at IS 'Timestamp when the active personal plan was last updated.';

-- personal_trainer_payments table
CREATE TABLE personal_trainer_payments (
    id serial PRIMARY KEY,
    external_id UUID UNIQUE NOT NULL,
    active_personal_plan_id INTEGER NOT NULL REFERENCES active_personal_plan(id) ON DELETE CASCADE,
    expected_amount DECIMAL(10,2) NOT NULL,
    expected_date DATE NOT NULL,
    amount_discount DECIMAL(10,2) NOT NULL,
    amount_paid DECIMAL(10,2) NOT NULL,
    paid_date DATE,
    status VARCHAR(1) DEFAULT 'A' CHECK (status IN ('A', 'B', 'I', 'P')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE personal_trainer_payments IS 'Table that stores payments related to personal trainers'' active plans.';

COMMENT ON COLUMN personal_trainer_payments.id IS 'Unique identifier for the payment record.';
COMMENT ON COLUMN personal_trainer_payments.active_personal_plan_id IS 'Reference to the associated active personal plan.';
COMMENT ON COLUMN personal_trainer_payments.expected_amount IS 'Expected payment amount.';
COMMENT ON COLUMN personal_trainer_payments.expected_date IS 'Expected payment date.';
COMMENT ON COLUMN personal_trainer_payments.amount_discount IS 'Discount applied to the expected amount.';
COMMENT ON COLUMN personal_trainer_payments.amount_paid IS 'Actual amount paid.';
COMMENT ON COLUMN personal_trainer_payments.paid_date IS 'Date when the payment was made.';
COMMENT ON COLUMN personal_trainer_payments.status IS 'Status of the payment record: A (Active), B (Blocked), I (Inactive), P (Pending).';
COMMENT ON COLUMN personal_trainer_payments.created_at IS 'Timestamp when the payment record was created.';
COMMENT ON COLUMN personal_trainer_payments.updated_at IS 'Timestamp when the payment record was last updated.';

-- student_feature table
CREATE TABLE student_feature (
    id bigserial PRIMARY KEY,
    external_id UUID UNIQUE NOT NULL,
    student_user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    password VARCHAR(100) NOT NULL,
    height INTEGER,
    weight DECIMAL(10,2),
    weight_unit VARCHAR(10),
    status VARCHAR(1) DEFAULT 'A' CHECK (status IN ('A', 'B', 'I', 'P')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE student_feature IS 'Table that stores physical features and credentials of students.';

COMMENT ON COLUMN student_feature.id IS 'Unique identifier for the student feature record.';
COMMENT ON COLUMN student_feature.student_user_id IS 'Reference to the associated student user.';
COMMENT ON COLUMN student_feature.password IS 'Password for the student account.';
COMMENT ON COLUMN student_feature.height IS 'Height of the student (in centimeters).';
COMMENT ON COLUMN student_feature.weight IS 'Weight of the student.';
COMMENT ON COLUMN student_feature.weight_unit IS 'Measurement unit for the weight (e.g., kg, lbs).';
COMMENT ON COLUMN student_feature.status IS 'Status of the student feature record: A (Active), B (Blocked), I (Inactive), P (Pending).';
COMMENT ON COLUMN student_feature.created_at IS 'Timestamp when the student feature record was created.';
COMMENT ON COLUMN student_feature.updated_at IS 'Timestamp when the student feature record was last updated.';

-- personal_feature table
CREATE TABLE personal_feature (
    id bigserial PRIMARY KEY,
    external_id UUID UNIQUE NOT NULL,
    personal_user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    register VARCHAR(50),
    place VARCHAR(200),
    experience VARCHAR(50),
    specialty VARCHAR(255),
    mon_period VARCHAR(11),
    tue_period VARCHAR(11),
    wed_period VARCHAR(11),
    thu_period VARCHAR(11),
    fri_period VARCHAR(11),
    sat_period VARCHAR(11),
    sun_period VARCHAR(11),
    status VARCHAR(1) DEFAULT 'A' CHECK (status IN ('A', 'B', 'I', 'P')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE personal_feature IS 'Table that stores availability periods for personal trainers.';

COMMENT ON COLUMN personal_feature.id IS 'Unique identifier for the personal feature record.';
COMMENT ON COLUMN personal_feature.personal_user_id IS 'Reference to the associated personal trainer user.';
COMMENT ON COLUMN personal_feature.mon_period IS 'Available period on Monday. i.e.: 08:00;16:00';
COMMENT ON COLUMN personal_feature.tue_period IS 'Available period on Tuesday. i.e.: 09:00;16:00';
COMMENT ON COLUMN personal_feature.wed_period IS 'Available period on Wednesday. i.e.: 08:00;16:00';
COMMENT ON COLUMN personal_feature.thu_period IS 'Available period on Thursday. i.e.: 10:00;14:00';
COMMENT ON COLUMN personal_feature.fri_period IS 'Available period on Friday. i.e.: 09:00;15:00';
COMMENT ON COLUMN personal_feature.sat_period IS 'Available period on Saturday. i.e.: 08:00;16:00';
COMMENT ON COLUMN personal_feature.sun_period IS 'Available period on Sunday.';
COMMENT ON COLUMN personal_feature.status IS 'Status of the personal feature record: A (Active), B (Blocked), I (Inactive), P (Pending).';
COMMENT ON COLUMN personal_feature.created_at IS 'Timestamp when the personal feature record was created.';
COMMENT ON COLUMN personal_feature.updated_at IS 'Timestamp when the personal feature record was last updated.';

-- User pack tranning table
CREATE TABLE training_pack (
    id SERIAL PRIMARY KEY,
    external_id UUID UNIQUE NOT NULL,
    personal_user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    modality_id INTEGER NOT NULL REFERENCES modality(id) ON DELETE CASCADE,
    description VARCHAR(255) NOT NULL,
    duration_days INTEGER NOT NULL,
    weekly_frequency INTEGER NOT NULL,
    notes VARCHAR(255) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    currency VARCHAR(10),
    status VARCHAR(1) DEFAULT 'A' ,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- contract table
CREATE TABLE contract (
    id SERIAL PRIMARY KEY,
    external_id UUID UNIQUE NOT NULL,
    pack_training_id INTEGER NOT NULL REFERENCES training_pack(id) ON DELETE CASCADE,
    student_user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    description VARCHAR(200) NOT NULL,
    workout_site VARCHAR(200) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    currency VARCHAR(10),
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    duration VARCHAR(5) NOT NULL,
    monday VARCHAR(5),
    tuesday VARCHAR(5),
    wednesday VARCHAR(5),
    thursday VARCHAR(5),
    friday VARCHAR(5),
    saturday VARCHAR(5),
    sunday VARCHAR(5),
    situation VARCHAR(50),
    status VARCHAR(1) DEFAULT 'A' ,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

create UNIQUE index uix_upt_pack_training_id_student_user_id on contract(pack_training_id,student_user_id);

-- Comments for contract
COMMENT ON TABLE contract IS 'Table that represents training packages between personal trainers and students.';

COMMENT ON COLUMN contract.id IS 'Unique identifier of the training package.';
COMMENT ON COLUMN contract.student_user_id IS 'ID of the user acting as the student. References the users table.';
COMMENT ON COLUMN contract.description IS 'Description of the training package.';
COMMENT ON COLUMN contract.price IS 'Price of the package in decimal currency';
COMMENT ON COLUMN contract.start_time IS 'Start time of the training session (format HH:MM).';
COMMENT ON COLUMN contract.duration IS 'End time of the training session (format HH:MM).';
COMMENT ON COLUMN contract.days_of_week IS 'Days of the week when the training will take place. E.g.: 0=Sunday, 1=Monday, ...';
COMMENT ON COLUMN contract.created_at IS 'Date and time when the record was created.';
COMMENT ON COLUMN contract.updated_at IS 'Date and time of the last update to the record.';

-- student payments
CREATE TABLE student_payments (
    id serial PRIMARY KEY,
    external_id UUID UNIQUE NOT NULL,
    contract_id INTEGER NOT NULL REFERENCES contract(id) ON DELETE CASCADE,
    amount DECIMAL(10,2) NOT NULL,
    expected_date DATE NOT NULL,
    status VARCHAR(1) DEFAULT 'A' CHECK (status IN ('A', 'B', 'I', 'P')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE student_payments IS 'Table that stores student payment records for training packages.';

COMMENT ON COLUMN student_payments.id IS 'Unique identifier for the student payment record.';
COMMENT ON COLUMN student_payments.contract_id IS 'Reference to the associated user training package.';
COMMENT ON COLUMN student_payments.amount IS 'Payment amount expected from the student.';
COMMENT ON COLUMN student_payments.expected_date IS 'Expected date of payment.';
COMMENT ON COLUMN student_payments.payment_date IS 'Actual date when the payment was made.';
COMMENT ON COLUMN student_payments.status IS 'Status of the student payment record: A (Active), B (Blocked), I (Inactive), P (Pending).';
COMMENT ON COLUMN student_payments.created_at IS 'Timestamp when the student payment record was created.';
COMMENT ON COLUMN student_payments.updated_at IS 'Timestamp when the student payment record was last updated.';

-- student payments
CREATE TABLE student_payments_transaction (
    id serial PRIMARY KEY,
    external_id UUID UNIQUE NOT NULL,
    student_payments_id INTEGER NOT NULL REFERENCES student_payments(id) ON DELETE CASCADE,
    payment_date DATE,
    received_amount DECIMAL(10,2),
    payment_method VARCHAR(100),
    comment TEXT,
    status VARCHAR(1) DEFAULT 'A' CHECK (status IN ('A', 'B', 'I', 'P')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

--
---- program_template table
--CREATE TABLE program_template (
--    id serial PRIMARY KEY,
--    external_id UUID UNIQUE NOT NULL,
--    version INTEGER NOT NULL,
--    modality_id INTEGER NOT NULL REFERENCES modality(id) ON DELETE CASCADE,
--    goal_id INTEGER NOT NULL REFERENCES goal(id) ON DELETE CASCADE,
--    program_id INTEGER NOT NULL REFERENCES program(id) ON DELETE CASCADE,
--    work_group_id INTEGER NOT NULL REFERENCES work_group(id) ON DELETE CASCADE,
--    exercise_id INTEGER NOT NULL REFERENCES exercise(id) ON DELETE CASCADE,
--    execution_method VARCHAR(100),
--    qty_series INTEGER,
--    qty_reps INTEGER,
--    execution VARCHAR(100),
--    execution_time VARCHAR(5),
--    rest_time VARCHAR(5),
--    weight INTEGER,
--    weight_unit VARCHAR(10),
--    comments VARCHAR(500),
--    status VARCHAR(1) DEFAULT 'A' CHECK (status IN ('A', 'B', 'I', 'P')),
--    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
--);
--
--COMMENT ON TABLE program_template IS 'Table that stores templates for exercise programs.';
--
--COMMENT ON COLUMN program_template.id IS 'Unique identifier for the program template.';
--COMMENT ON COLUMN program_template.version IS 'Version or variation for the program template';
--COMMENT ON COLUMN program_template.modality_id IS 'Reference to the associated modality relationship.';
--COMMENT ON COLUMN program_template.exercise_id IS 'Reference to the associated exercise relationship.';
--COMMENT ON COLUMN program_template.work_group_id IS 'Reference to the associated work group.';
--COMMENT ON COLUMN program_template.goal_id IS 'Reference to the associated goal.';
--COMMENT ON COLUMN program_template.program_id IS 'Reference to the associated program.';
--COMMENT ON COLUMN program_template.execution IS 'Execution description or instructions.';
--COMMENT ON COLUMN program_template.execution_time IS 'Estimated time for execution (in minutes). format: hh:mi';
--COMMENT ON COLUMN program_template.rest_time IS 'Rest time between sets or exercises (in minutes). format: hh:mi';
--COMMENT ON COLUMN program_template.weight IS 'Suggested weight for the exercise.';
--COMMENT ON COLUMN program_template.weight_unit IS 'Unit of measurement for weight (e.g., kg, lbs).';
--COMMENT ON COLUMN program_template.comments IS 'Additional comments or notes about the exercise.';
--COMMENT ON COLUMN program_template.status IS 'Status of the program template: A (Active), B (Blocked), I (Inactive), P (Pending).';
--COMMENT ON COLUMN program_template.created_at IS 'Timestamp when the program template was created.';
--COMMENT ON COLUMN program_template.updated_at IS 'Timestamp when the program template was last updated.';

---- personal_trainer_program table
--CREATE TABLE personal_trainer_program (
--    id serial PRIMARY KEY,
--    personal_user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
--    modality_id INTEGER NOT NULL REFERENCES modality(id) ON DELETE CASCADE,
--    goal_id INTEGER NOT NULL REFERENCES goal(id) ON DELETE CASCADE,
--    program_id INTEGER NOT NULL REFERENCES program(id) ON DELETE CASCADE,
--    work_group_id INTEGER NOT NULL REFERENCES work_group(id) ON DELETE CASCADE,
--    exercise_id INTEGER REFERENCES exercise(id) ON DELETE CASCADE,
--    custom_exercise VARCHAR(100),
--    custom_program VARCHAR(100),
--    execution_method VARCHAR(100),
--    qty_series INTEGER,
--    qty_reps INTEGER,
--    execution VARCHAR(100),
--    execution_time VARCHAR(5),
--    rest_time VARCHAR(5),
--    weight INTEGER,
--    weight_unit VARCHAR(10),
--    comments VARCHAR(500),
--    obs VARCHAR(500),
--    status VARCHAR(1) DEFAULT 'A' CHECK (status IN ('A', 'B', 'I', 'P')),
--    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
--);
--
--COMMENT ON TABLE personal_trainer_program IS 'Table that stores customized exercise programs created by personal trainers.';
--
--COMMENT ON COLUMN personal_trainer_program.id IS 'Unique identifier for the personal trainer program.';
--COMMENT ON COLUMN personal_trainer_program.personal_user_id IS 'Reference to the associated personal trainer user.';
--COMMENT ON COLUMN personal_trainer_program.modality_id IS 'Reference to the associated modality relationship.';
--COMMENT ON COLUMN personal_trainer_program.work_group_id IS 'Reference to the associated work group.';
--COMMENT ON COLUMN personal_trainer_program.goal_id IS 'Reference to the associated goal.';
--COMMENT ON COLUMN personal_trainer_program.exercise_id IS 'Reference to the associated exercise (nullable if a custom exercise is provided).';
--COMMENT ON COLUMN personal_trainer_program.program_id IS 'Reference to the associated program.';
--COMMENT ON COLUMN personal_trainer_program.custom_exercise IS 'Name of the custom exercise if not using a predefined one.';
--COMMENT ON COLUMN personal_trainer_program.custom_program IS 'Name of the custom program if not using a predefined one.';
--COMMENT ON COLUMN personal_trainer_program.execution IS 'Execution description or instructions.';
--COMMENT ON COLUMN personal_trainer_program.execution_time IS 'Estimated time for execution (in minutes). format: hh:mi';
--COMMENT ON COLUMN personal_trainer_program.rest_time IS 'Rest time between sets or exercises (in minutes). format: hh:mi';
--COMMENT ON COLUMN personal_trainer_program.weight IS 'Suggested weight for the exercise.';
--COMMENT ON COLUMN personal_trainer_program.weight_unit IS 'Unit of measurement for weight (e.g., kg, lbs).';
--COMMENT ON COLUMN personal_trainer_program.comments IS 'Additional comments about the exercise or program.';
--COMMENT ON COLUMN personal_trainer_program.obs IS 'Observations or notes related to the exercise or program.';
--COMMENT ON COLUMN personal_trainer_program.status IS 'Status of the personal trainer program: A (Active), B (Blocked), I (Inactive), P (Pending).';
--COMMENT ON COLUMN personal_trainer_program.created_at IS 'Timestamp when the personal trainer program was created.';
--COMMENT ON COLUMN personal_trainer_program.updated_at IS 'Timestamp when the personal trainer program was last updated.';

-- User Workout Calendar
CREATE TABLE user_workout_plan (
    id SERIAL PRIMARY KEY,
    external_id UUID UNIQUE NOT NULL,
    contract_id INTEGER NOT NULL REFERENCES contract(id) ON DELETE CASCADE,
    modality_id INTEGER NOT NULL REFERENCES modality(id) ON DELETE CASCADE,
    goal_id INTEGER NOT NULL REFERENCES goal(id) ON DELETE CASCADE,
    program_id INTEGER NOT NULL REFERENCES program(id) ON DELETE CASCADE,
    work_group_id INTEGER NOT NULL REFERENCES work_group(id) ON DELETE CASCADE,
    exercise_id INTEGER REFERENCES exercise(id) ON DELETE CASCADE,
    custom_exercise VARCHAR(100),
    custom_program VARCHAR(100),
    execution_method VARCHAR(100),
    qty_series INTEGER,
    qty_reps VARCHAR(20),
    execution VARCHAR(100),
    execution_time VARCHAR(5),
    rest_time VARCHAR(5),
    weight INTEGER,
    weight_unit VARCHAR(10),
    comments TEXT,
    obs VARCHAR(500),
    status VARCHAR(1) DEFAULT 'A' CHECK (status IN ('A', 'B', 'I', 'P')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table comment
COMMENT ON TABLE user_workout_plan IS 'Stores scheduled workouts for users, including modality, date, time, and execution notes.';

-- Column comments
COMMENT ON COLUMN user_workout_plan.id IS 'Primary key for the user workout calendar entry.';
COMMENT ON COLUMN user_workout_plan.contract_id IS 'Foreign key referencing the contract table, indicating which training pack this workout is part of.';

CREATE TABLE user_training_session(
    id SERIAL PRIMARY KEY,
    external_id UUID UNIQUE NOT NULL,
    contract_id INTEGER NOT NULL REFERENCES contract(id) ON DELETE CASCADE,
    booking_date TIMESTAMP,
    start_at TIMESTAMP NOT NULL,
    finished_at TIMESTAMP NOT NULL,
    elapsed_time INTEGER,
    progress_status VARCHAR(50) NOT NULL,
    sync_status VARCHAR(50) NOT NULL,
    comments TEXT,
    status VARCHAR(1) DEFAULT 'A' CHECK (status IN ('A', 'B', 'I', 'P')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_training_session_exercises(
    id SERIAL PRIMARY KEY,
    external_id UUID UNIQUE NOT NULL,
    user_training_session_id INTEGER NOT NULL REFERENCES user_training_session(id) ON DELETE CASCADE,
    user_workout_plan_id INTEGER NOT NULL REFERENCES user_workout_plan(id) ON DELETE CASCADE,
    status VARCHAR(1) DEFAULT 'A' CHECK (status IN ('A', 'B', 'I', 'P')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP

);

CREATE TABLE user_training_execution_set (
    id SERIAL PRIMARY KEY,
    external_id UUID UNIQUE NOT NULL,
    user_training_session_exercises_id INTEGER NOT NULL REFERENCES user_training_session_exercises(id) ON DELETE CASCADE,
    started_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    finished_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    set_number INTEGER NOT NULL,
    reps INTEGER,
    weight NUMERIC(5,2),
    weight_unit VARCHAR(10) DEFAULT 'kg',
    elapsed_time INTEGER,
    status VARCHAR(1) DEFAULT 'A' CHECK (status IN ('A', 'B', 'I', 'P')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
