create table tb_session_state (
    id_session_state bigserial NOT NULL,
    id_token UUID NOT NULL,
    id_user_uuid UUID NOT NULL,
    status VARCHAR(1) NOT NULL,
    date_created timestamp NOT NULL,
    date_updated timestamp NOT NULL,
    CONSTRAINT tb_session_state_pkey PRIMARY KEY (id_token)
);

create table tb_user (
    id_user bigserial NOT NULL,
    tx_name VARCHAR NOT NULL,
    tx_encoded_pass_phrase VARCHAR NOT NULL,
    id_user_uuid UUID NOT NULL,
    dt_birthday date NOT NULL,
    status VARCHAR(1) NOT NULL,
    date_created timestamp NOT NULL,
    date_updated timestamp NOT NULL,
    CONSTRAINT tb_user_pkey PRIMARY KEY (id_user)
);

CREATE UNIQUE INDEX UIX_USER_UUID ON tb_user(id_user_uuid);

ALTER TABLE tb_session_state
ADD CONSTRAINT fk_session_state_user FOREIGN KEY (id_user_uuid)
REFERENCES tb_user(id_user_uuid) ON DELETE CASCADE;



