--liquibase formatted sql

--changeset julio.vitorino:2025-08-29_16-insert-exercises-hamstrings
--comment Insert default exercises for Musculação (Hamstrings)
INSERT INTO exercise (id, external_id, image_uuid, name_pt, name_en, name_es) VALUES
(61, gen_random_uuid(), '9a0b8e9f-3739-4e40-9e8c-8f3d9698d00d', 'Mesa Flexora', 'Lying Leg Curl', 'Curl de Piernas Acostado'),
(62, gen_random_uuid(), '5f3f17a2-01b1-44cf-977a-1ad9288442d6', 'Flexão de Joelhos em Pé', 'Standing Leg Curl', 'Curl de Piernas de Pie'),
(63, gen_random_uuid(), 'a8db357a-ff1c-4c4b-8d6a-fd28358c8e3e', 'Flexão de Joelhos Sentado', 'Seated Leg Curl', 'Curl de Piernas Sentado'),
(64, gen_random_uuid(), '20dbf4ec-84b6-46f1-83f8-056b1444b9c5', 'Stiff com Barra', 'Barbell Stiff-Leg Deadlift', 'Peso Muerto Rumano con Barra'),
(65, gen_random_uuid(), '17c2c36a-c52c-4f8f-bc3b-3adff08c1787', 'Stiff com Halteres', 'Dumbbell Stiff-Leg Deadlift', 'Peso Muerto Rumano con Mancuernas'),
(66, gen_random_uuid(), 'bc24b4f9-65ad-4e94-b11c-57f57ad529b0', 'Glute Ham Raise', 'Glute Ham Raise', 'Elevación de Glúteos e Isquiotibiales'),
(67, gen_random_uuid(), 'a84f20fc-16cd-4980-84a9-64fbcac7d85d', 'Good Morning', 'Good Morning Exercise', 'Buenos Días'),
(68, gen_random_uuid(), 'c132a02c-c826-4e21-b4d5-2aaf0dcd76fc', 'Deadlift Romeno com Halteres', 'Dumbbell Romanian Deadlift', 'Peso Muerto Rumano con Mancuernas'),
(69, gen_random_uuid(), 'f173949d-166a-49ec-b94f-556a56a30238', 'Cadeira Flexora Unilateral', 'Unilateral Seated Leg Curl', 'Curl de Piernas Unilateral Sentado'),
(70, gen_random_uuid(), 'cbf1c3f4-4eaf-4e6d-8d62-236b29a9a1d4', 'Flexão Nórdica', 'Nordic Hamstring Curl', 'Curl Nórdico de Isquiotibiales')
ON CONFLICT DO NOTHING;


--rollback DELETE FROM exercise WHERE id BETWEEN 61 AND 70;
