
create table awardness
(
	id  bigserial  not null ,
    uuid_external_app UUID NOT NULL,
	tx_title  varchar(255)  not null ,
	dt_start  date  not null ,
	dt_duedate  date  not null ,
	dt_cancel  date ,
	tx_reason_cancel  varchar(255) ,
	tx_url_rule  varchar(4000) not null,
	nu_max_tickets int4 not null,
	nu_digits_tickets int4 not null,
	in_random_ticket boolean not null,
	status  varchar(1)   default 'A' not null,
	dt_created timestamp default current_timestamp not null ,
	dt_updated timestamp default current_timestamp not null,
	constraint awardness_pkey primary key(id)
) ;

create table awardness_tickets
(
	id  bigserial  not null ,
	id_awardness  int8  not null ,
	nu_ticket int4 not null,
    uuid_external_user UUID,
	status  varchar(1)   default 'A' not null,
	dt_created timestamp default current_timestamp not null ,
	dt_updated timestamp default current_timestamp not null,
	constraint awardness_tickets_pkey primary key(id)
);


create table award
(
	id  bigserial  not null ,
	id_awardness  int8  not null ,
    tx_title  varchar(255)  not null ,
    tx_specifications  varchar(4000)  not null ,
    in_delivery  boolean ,
	status  varchar(1)   default 'A' not null,
	dt_created timestamp default current_timestamp not null ,
	dt_updated timestamp default current_timestamp not null,
	constraint award_pkey primary key(id)
);

create table awardness_winners
(
	id  bigserial  not null ,
	id_award  int8  not null ,
	id_awardness_tickets  int8  not null ,
	dt_redemption timestamp,
	status  varchar(1)   default 'A' not null,
	dt_created timestamp default current_timestamp not null ,
	dt_updated timestamp default current_timestamp not null,
	constraint awardness_winners_pkey primary key(id)
);

alter table awardness_tickets add constraint awardness_tickets_to_awardness foreign key (id_awardness) references awardness(id);
alter table award add constraint award_to_awardness foreign key (id_awardness) references awardness(id);
alter table awardness_winners add constraint awardness_winners_to_award foreign key (id_award) references award(id);
alter table awardness_winners add constraint awardness_winners_to_awardness_tickets foreign key (id_awardness_tickets) references awardness_tickets(id);
