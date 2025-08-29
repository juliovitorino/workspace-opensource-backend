--liquibase formatted sql

--changeset julio.vitorino:2025-08-29_01-insert-default-parameters
--comment Seed default application parameters
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
    ('MAINTENANCE_MODE', 'false', 'A') ON CONFLICT DO NOTHING;
--rollback DELETE FROM parameters WHERE keytag IN (
--rollback   'ACTIVE_ENVIRONMENT',
--rollback   'MAX_LOGIN_ATTEMPTS',
--rollback   'DEFAULT_LANGUAGE',
--rollback   'MAX_SESSIONS',
--rollback   'APP_NAME',
--rollback   'PASSWORD_EXPIRY_DAYS',
--rollback   'ENABLE_NOTIFICATIONS',
--rollback   'SUPPORT_EMAIL',
--rollback   'TIMEZONE_DEFAULT',
--rollback   'DEFAULT_CURRENCY',
--rollback   'MAINTENANCE_MODE'
--rollback );