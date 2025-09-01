--liquibase formatted sql

--changeset julio.vitorino:2025-09-01-1641_01-insert-exercises-biceps
--comment Insert default exercises for Musculação (Bíceps)
INSERT INTO exercise (id, external_id, image_uuid, name_pt, name_en, name_es) VALUES
(136, gen_random_uuid(), '8750a2e1-8f5f-49c8-be90-c5a9fdb1e7f7', 'Rosca Direta com Barra', 'Barbell Curl', 'Curl de Bíceps con Barra'),
(137, gen_random_uuid(), 'e8acbada-8c94-4d4a-8a28-df1ec2f98743', 'Rosca Direta com Halteres', 'Dumbbell Curl', 'Curl de Bíceps con Mancuernas'),
(138, gen_random_uuid(), 'd1f22c63-30f3-4af8-92b0-d10ff902d043', 'Rosca Alternada com Halteres', 'Alternating Dumbbell Curl', 'Curl Alternado con Mancuernas'),
(139, gen_random_uuid(), '4e7d58d9-738a-4b43-bb19-6e302b8b25ad', 'Rosca Scott na Barra', 'Barbell Preacher Curl', 'Curl Predicador con Barra'),
(140, gen_random_uuid(), 'd01b8024-02c8-4c76-b83f-37bb7554ff3e', 'Rosca Scott com Halteres', 'Dumbbell Preacher Curl', 'Curl Predicador con Mancuernas'),
(141, gen_random_uuid(), '82c61277-4789-4c6a-9c48-0f5d41ddf978', 'Rosca Martelo com Halteres', 'Hammer Curl', 'Curl Martillo con Mancuernas'),
(142, gen_random_uuid(), 'dfd8a7ef-552a-4c06-9319-c3448dffcad6', 'Rosca Concentrada', 'Concentration Curl', 'Curl Concentrado'),
(143, gen_random_uuid(), '8d89a0b6-9f68-44d5-9a8f-b6ffb6d72aa5', 'Rosca Inversa com Barra', 'Reverse Barbell Curl', 'Curl Inverso con Barra'),
(144, gen_random_uuid(), 'c5c50baf-1f8d-4f26-a1d2-3aef86b4c88b', 'Rosca 21', '21s Curl', 'Curl 21s'),
(145, gen_random_uuid(), '6898c60b-405f-4b40-b3a8-0a934c28a38a', 'Rosca de Bíceps na Polia', 'Cable Biceps Curl', 'Curl de Bíceps en Polea'),
(146, gen_random_uuid(), 'c1e22027-19f5-4ab0-9105-1b2d56c56910', 'Rosca Martelo na Polia', 'Cable Hammer Curl', 'Curl Martillo en Polea'),
(147, gen_random_uuid(), 'ad20716e-ff15-48cb-87d7-2eb8ed3e6fc4', 'Rosca de Bíceps no Cross Over', 'Cross Cable Curl', 'Curl de Bíceps en Cruce de Poleas'),
(148, gen_random_uuid(), '86d73c49-5e38-4c09-9f3f-7477b89a2f3f', 'Rosca de Bíceps com Barra W', 'EZ Bar Curl', 'Curl con Barra EZ'),
(149, gen_random_uuid(), 'fbe8d0cd-d2d6-4721-8ac6-3c2a38c0aa7f', 'Rosca Spider com Halteres', 'Spider Dumbbell Curl', 'Curl Spider con Mancuernas'),
(150, gen_random_uuid(), '64b771f3-fd20-4cf0-8195-fc9a021b3c5f', 'Rosca Spider com Barra', 'Spider Barbell Curl', 'Curl Spider con Barra')
ON CONFLICT DO NOTHING;

--rollback DELETE FROM exercise WHERE id BETWEEN 136 AND 150;
