--liquibase formatted sql
--changeset julio.vitorino:2025-11-09-1507_01-add-google-columns

alter table users add provider varchar(50) null;
alter table users add id_google varchar(255) null;

--rollback alter table users drop column provider;
--rollback alter table users drop column id_google;
