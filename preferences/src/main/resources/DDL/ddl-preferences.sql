create table public.tb_user_preferences (
    id_user_preference bigserial NOT NULL,
    uuid_external_app UUID NOT NULL,
    uuid_external_user UUID NOT NULL,
    tx_key varchar(100) NOT NULL,
    tx_preference jsonb NOT NULL,
    status VARCHAR(1) NOT NULL,
    date_created timestamp NOT NULL,
    date_updated timestamp NOT NULL,
    CONSTRAINT tb_user_preferences_pkey PRIMARY KEY (id_user_preference)
);

create table public.tb_system_preferences(
    id_system_preference bigserial NOT NULL,
    uuid_external_app UUID NOT NULL,
    tx_key varchar(100) NOT NULL,
    tx_preference jsonb NOT NULL,
    status VARCHAR(1) NOT NULL,
    date_created timestamp NOT NULL,
    date_updated timestamp NOT NULL,
    CONSTRAINT tb_user_preferences_pkey PRIMARY KEY (id_system_preference)
);