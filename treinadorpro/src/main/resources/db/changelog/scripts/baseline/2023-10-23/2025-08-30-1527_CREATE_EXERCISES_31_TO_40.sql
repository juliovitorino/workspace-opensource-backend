--liquibase formatted sql

--changeset julio.vitorino:2025-08-30-1527_01-insert-exercises-pilates
--comment Insert default exercises for Pilates (id=4)
INSERT INTO exercise (id, external_id, image_uuid, name_pt, name_en, name_es) VALUES
(31, gen_random_uuid(), '8b678b63-72b6-4b90-aaf7-4a7b4d3dfc36', 'The Hundred', 'The Hundred', 'El Cien'),
(32, gen_random_uuid(), 'cb208dad-5dcf-4855-87b6-42c5f442b1b9', 'Roll Up', 'Roll Up', 'Rodar hacia arriba'),
(33, gen_random_uuid(), 'abf1d9fd-3947-43eb-8936-b93d119d8a9d', 'Single Leg Stretch', 'Single Leg Stretch', 'Estiramiento de una pierna'),
(34, gen_random_uuid(), '6b55a6b1-5305-4190-9302-d11811c07d47', 'Double Leg Stretch', 'Double Leg Stretch', 'Estiramiento de dos piernas'),
(35, gen_random_uuid(), 'ce1e47cb-2a25-449b-91f5-97a7c1e0b6d6', 'Leg Circles', 'Leg Circles', 'Círculos de piernas'),
(36, gen_random_uuid(), 'e8d5e80b-d213-42d6-b3c7-7d2c1a3df4f8', 'Spine Stretch', 'Spine Stretch', 'Estiramiento de columna'),
(37, gen_random_uuid(), '1a2b7a4c-8491-4c9a-8a35-843274ad2e63', 'Teaser', 'Teaser', 'Teaser'),
(38, gen_random_uuid(), 'b2f66c5c-30cb-41e7-9dbd-563b6711f4f3', 'Saw', 'Saw', 'Sierra'),
(39, gen_random_uuid(), '77c15ff0-c2f7-47b3-bc24-03843b3c30da', 'Swimming', 'Swimming', 'Natación'),
(40, gen_random_uuid(), '1cf2a187-fb85-46d6-9b73-9d6a5f7ab891', 'Shoulder Bridge', 'Shoulder Bridge', 'Puente de hombros')
ON CONFLICT DO NOTHING;

--rollback DELETE FROM exercise WHERE id BETWEEN 31 AND 40;
