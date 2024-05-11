create table public.tb_list (
    id_list bigserial NOT NULL,
    uuid_external_app UUID NOT NULL,
    uuid_external_user UUID NOT NULL,
    uuid_external_list UUID NOT NULL,
    tx_title varchar(100) NOT NULL,
    status VARCHAR(1) NOT NULL,
    date_created timestamp NOT NULL,
    date_updated timestamp NOT NULL,
    CONSTRAINT tb_list_pkey PRIMARY KEY (id_list)
);

create table public.tb_reminder (
    id_reminder bigserial NOT NULL,
    id_list int8 NOT NULL,
    tx_title varchar(500) NOT NULL,
    tx_note varchar(4000),
    dt_duedate timestamp(4000),
    tx_tags varchar(2000),
    in_priority varchar(20),
    in_flag boolean,
    tx_full_url_image varchar(1000),
    tx_url varchar(1000),
    status VARCHAR(1) NOT NULL,
    date_created timestamp NOT NULL,
    date_updated timestamp NOT NULL,
    CONSTRAINT tb_reminder_pkey PRIMARY KEY (id_reminder)
);

ALTER TABLE public.tb_reminder ADD CONSTRAINT fk_reminder_01 FOREIGN KEY (id_list)
references public.tb_list(id_list) on delete cascade;