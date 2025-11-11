--liquibase formatted sql
--changeset julio.vitorino:2025-11-11-0842_01-add-column-cancel-code

alter table contract add cancel_code varchar(6) null;
alter table contract add purge_at date null;
alter table contract add cancel_at date null;

--rollback alter table contract drop column cancel_code;
--rollback alter table contract drop column purge_at;
--rollback alter table contract drop column cancel_at;
