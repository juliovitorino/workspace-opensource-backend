create table tb_session_state (
    id_session_state bigserial NOT NULL,
    id_token UUID NOT NULL,
    id_user_uuid UUID NOT NULL,
    status VARCHAR(1) NOT NULL,
    date_created timestamp NOT NULL,
    date_updated timestamp NOT NULL,
    CONSTRAINT tb_session_state_pkey PRIMARY KEY (id_token)
);


create table tb_application (
    id_application bigserial NOT NULL,
    tx_name VARCHAR NOT NULL,
    cd_external UUID NOT NULL,
    status VARCHAR(1) NOT NULL,
    date_created timestamp NOT NULL,
    date_updated timestamp NOT NULL,
    CONSTRAINT tb_applicationpkey PRIMARY KEY (id_application)
);

CREATE UNIQUE INDEX uix_cd_external ON tb_application(cd_external);


create table tb_user (
    id_user bigserial NOT NULL,
    tx_name VARCHAR NOT NULL,
    dt_birthday date NOT NULL,
    status VARCHAR(1) NOT NULL,
    date_created timestamp NOT NULL,
    date_updated timestamp NOT NULL,
    CONSTRAINT tb_user_pkey PRIMARY KEY (id_user)
);

CREATE UNIQUE INDEX UIX_USER_UUID ON tb_user(id_user_uuid);

create table tb_application_user (
    id_application_user bigserial NOT NULL,
    id_application int8 NOT NULL,
    id_user int8 NOT NULL,
    tx_email VARCHAR NOT NULL,
    tx_encoded_pass_phrase VARCHAR NOT NULL,
    cd_external_uuid UUID NOT NULL,
    cd_external_user_uuid UUID NOT NULL,
    cd_url_token_activation VARCHAR,
    cd_activation VARCHAR,
    dt_due_activation timestamptz,
    status VARCHAR(1) NOT NULL,
    date_created timestamp NOT NULL,
    date_updated timestamp NOT NULL,
    CONSTRAINT tb_application_user_pkey PRIMARY KEY (id_application_user)
);
CREATE UNIQUE INDEX uix_application_user_cd_external_uuid ON tb_application_user(cd_external_uuid);
CREATE UNIQUE INDEX uix_application_user_cd_external_user_uuid ON tb_application_user(cd_external_user_uuid);

ALTER TABLE tb_session_state
ADD CONSTRAINT fk_session_state_user FOREIGN KEY (id_user_uuid)
REFERENCES tb_application_user(cd_external_user_uuid) ON DELETE CASCADE;

ALTER TABLE tb_application_user
ADD CONSTRAINT fk_appuser_application FOREIGN KEY (id_application)
REFERENCES tb_application(id_application) ON DELETE CASCADE;

ALTER TABLE tb_application_user
ADD CONSTRAINT fk_appuser_application_01 FOREIGN KEY (cd_external_uuid)
REFERENCES tb_application(cd_external) ON DELETE CASCADE;


ALTER TABLE tb_application_user
ADD CONSTRAINT fk_appuser_user FOREIGN KEY (id_user)
REFERENCES tb_user(id_user) ON DELETE CASCADE;




