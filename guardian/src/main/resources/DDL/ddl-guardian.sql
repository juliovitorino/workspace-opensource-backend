create table tb_session_state (
    id_session_state bigserial NOT NULL,
    id_token UUID NOT NULL,
    id_user_uuid UUID NOT NULL,
    status VARCHAR(1) NOT NULL,
    date_created timestamp NOT NULL,
    date_updated timestamp NOT NULL,
    CONSTRAINT tb_session_state_pkey PRIMARY KEY (id_token)
);

