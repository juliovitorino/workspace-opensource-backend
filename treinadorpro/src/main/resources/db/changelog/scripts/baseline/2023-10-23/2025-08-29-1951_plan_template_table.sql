--liquibase formatted sql

--changeset julio.vitorino:2025-08-29-1951_01-insert-plan-templates
--comment Insert default plan templates
INSERT INTO plan_template (description, price, status, payment_frequency) VALUES
    ('FREEMIUM', 67, 'A','MONTHLY'),
    ('BASIC', 804, 'A','ANNUALLY'),
    ('BASIC', 97, 'A','MONTHLY'),
    ('PROFESSIONAL', 1164, 'A','ANNUALLY'),
    ('PROFESSIONAL', 130, 'A','MONTHLY'),
    ('PREMIUM', 1560, 'A','ANNUALLY'),
    ('PREMIUM', 197, 'A','MONTHLY');
--rollback DELETE FROM plan_template
--rollback WHERE (description, price, payment_frequency) IN (
--rollback   ('FREEMIUM', 67, 'MONTHLY'),
--rollback   ('BASIC', 804, 'ANNUALLY'),
--rollback   ('BASIC', 97, 'MONTHLY'),
--rollback   ('PROFESSIONAL', 1164, 'ANNUALLY'),
--rollback   ('PROFESSIONAL', 130, 'MONTHLY'),
--rollback   ('PREMIUM', 1560, 'ANNUALLY'),
--rollback   ('PREMIUM', 197, 'MONTHLY')
--rollback );

--changeset julio.vitorino:2025-08-29-1951_02-insert-freemium-parameter
--comment Insert FREEMIUM_PLAN_ID parameter linked to plan_template
INSERT INTO parameters (keytag, valuetag)
SELECT 'FREEMIUM_PLAN_ID', id
FROM plan_template
WHERE description = 'FREEMIUM'
LIMIT 1 ON CONFLICT DO NOTHING;
--rollback DELETE FROM parameters WHERE keytag = 'FREEMIUM_PLAN_ID';
