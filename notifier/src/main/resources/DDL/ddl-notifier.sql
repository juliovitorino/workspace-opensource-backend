create table tb_notifier (
    id_notifier bigserial NOT NULL,
    id_application_uuid UUID NOT NULL,
    id_user_uuid UUID NOT NULL,
    in_type VARCHAR(2) NOT NULL,
    tx_title VARCHAR(100) NOT NULL,
    tx_description VARCHAR(500) NOT NULL,
    in_readed VARCHAR(1) NOT NULL,
    status VARCHAR(1) NOT NULL,
    date_created timestamp NOT NULL,
    date_updated timestamp NOT NULL,
    CONSTRAINT tb_notifier_pkey PRIMARY KEY (id_notifier)
);



