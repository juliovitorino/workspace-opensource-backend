--liquibase formatted sql

--changeset julio.vitorino:2025-08-29_12-insert-exercises-yoga
--comment Insert default exercises for Yoga (id=3)
INSERT INTO exercise (id, image_uuid, name_pt, name_en, name_es) VALUES
(21, 'd21c78f1-482c-45ac-9b7a-b0cc3eeec57a', 'Postura da criança', 'Child''s Pose', 'Postura del niño'),
(22, 'b9287c7a-6e69-4b8d-9d8f-83a9c2906ee7', 'Cão olhando para baixo', 'Downward-Facing Dog', 'Perro boca abajo'),
(23, 'c8d6b4c3-8d1a-45a1-8f36-44f3778f4b2e', 'Postura da cobra', 'Cobra Pose', 'Postura de la cobra'),
(24, 'f94e1528-f92f-49a9-a781-cc5f4577dc85', 'Postura da montanha', 'Mountain Pose', 'Postura de la montaña'),
(25, '46d6d5c2-36b0-4743-a815-11638f1b775e', 'Postura do guerreiro I', 'Warrior I Pose', 'Guerrero I'),
(26, '0fcd7a24-5a5a-47f2-8d2a-f4ef30a6fc0a', 'Postura do guerreiro II', 'Warrior II Pose', 'Guerrero II'),
(27, '7e47e580-3f1b-439b-90f4-3af68852c28c', 'Postura do triângulo', 'Triangle Pose', 'Postura del triángulo'),
(28, '3a0af8e8-b2b3-4d20-83aa-2cf0ec9fcffe', 'Postura da árvore', 'Tree Pose', 'Postura del árbol'),
(29, '7d2bba89-8bb0-46e2-b0dd-61aee03ad5a1', 'Postura do cadáver', 'Corpse Pose', 'Postura del cadáver'),
(30, '2f76bbcb-0041-4f44-b37d-9db5a7695fdb', 'Torção sentada', 'Seated Twist', 'Torsión sentada')
ON CONFLICT DO NOTHING;

--rollback DELETE FROM exercise WHERE id BETWEEN 21 AND 30;
