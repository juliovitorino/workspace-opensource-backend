create table public.tb_reaction (
    id bigserial NOT NULL,
    tx_name varchar(100) NOT NULL,
    tx_icon varchar(100) NOT NULL,
    tx_tag varchar(100) NOT NULL,
    status VARCHAR(1) NOT NULL,
    dt_created timestamp NOT NULL,
    dt_updated timestamp NOT NULL,
    CONSTRAINT tb_reaction_pkey PRIMARY KEY (id)
);

create table public.tb_group (
    id bigserial NOT NULL,
    tx_name varchar(100) NOT NULL,
    status VARCHAR(1) NOT NULL,
    dt_created timestamp NOT NULL,
    dt_updated timestamp NOT NULL,
    CONSTRAINT tb_group_pkey PRIMARY KEY (id)
);

create table public.tb_group_reaction (
    id bigserial NOT NULL,
    id_group int8 NOT NULL,
    id_reaction int8 NOT NULL,
    status VARCHAR(1) NOT NULL,
    dt_created timestamp NOT NULL,
    dt_updated timestamp NOT NULL,
    CONSTRAINT tb_group_reaction_pkey PRIMARY KEY (id)
);

create table public.tb_reaction_event (
    id bigserial NOT NULL,
    id_reaction int8 NOT NULL,
    uuid_external_item UUID NOT NULL,
    uuid_external_app UUID NOT NULL,
    uuid_external_user UUID NOT NULL,
    status VARCHAR(1) NOT NULL,
    dt_created timestamp NOT NULL,
    dt_updated timestamp NOT NULL,
    CONSTRAINT tb_reaction_event_pkey PRIMARY KEY (id)
);

ALTER TABLE tb_reaction_event
ADD CONSTRAINT reaction_event_to_reaction_fkey
FOREIGN KEY (id_reaction) REFERENCES tb_reaction(id);

ALTER TABLE tb_group_reaction
ADD CONSTRAINT group_reaction_to_group_fkey
FOREIGN KEY (id_group) REFERENCES tb_group(id);

ALTER TABLE tb_group_reaction
ADD CONSTRAINT group_reaction_to_reaction_fkey
FOREIGN KEY (id_reaction) REFERENCES tb_reaction(id);

