create table public.tb_notifier (
    id_notifier bigserial NOT NULL,
    uuid_external_app UUID NOT NULL,
    uuid_external_user UUID NOT NULL,
    tx_type varchar(100) NOT NULL,
    tx_title varchar(500) NOT NULL,
    tx_description TEXT NOT NULL,
    tx_url_image varchar(2000),
    tx_icon_class varchar(200),
    tx_object jsonb,
    in_seen VARCHAR(1),
    tx_url_follow VARCHAR(2000),
    status VARCHAR(1) NOT NULL,
    date_created timestamp NOT NULL,
    date_updated timestamp NOT NULL,
    CONSTRAINT tb_notifier_pkey PRIMARY KEY (id_notifier)
);

