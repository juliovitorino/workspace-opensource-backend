CREATE TABLE IF NOT EXISTS public.economic_index (
    id bigserial NOT NULL,
    tx_economic_index varchar(100) NOT NULL,
    cd_serie_bacen varchar(10) NOT NULL,
    dt_last_date_value date,
    status_process VARCHAR(50),
    status VARCHAR(1) NOT NULL,
    date_created timestamp NOT NULL,
    date_updated timestamp NOT NULL,
    CONSTRAINT economic_index_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS  public.economic_index_data (
    id bigserial NOT NULL,
    id_economic_index int8 NOT NULL,
    dt_economic_index date NOT NULL,
    vl_economic_index numeric(20,8) NOT NULL,
    status VARCHAR(1) NOT NULL,
    date_created timestamp NOT NULL,
    date_updated timestamp NOT NULL,
    CONSTRAINT economic_index_data_pkey PRIMARY KEY (id)
);

ALTER TABLE public.economic_index_data
ADD CONSTRAINT economic_index_data_to_economic_index_fkey
FOREIGN KEY (id_economic_index)
REFERENCES public.economic_index(id) on delete cascade;

