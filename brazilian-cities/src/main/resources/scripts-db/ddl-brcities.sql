
create table uf
(
	id  bigserial  not null ,
	sg_uf  varchar(20)  not null ,
	nm_uf  varchar(20)  not null ,
	status  varchar(1)   default 'A' not null,
	dt_created timestamp default current_timestamp not null ,
	dt_updated timestamp default current_timestamp not null,

	constraint uf_pkey primary key(id)
) ;
create  unique index un_uf on uf(sg_uf);

create table city
(
	id  bigserial  not null ,
	nm_city  varchar(50)  not null,
	status  varchar(1)   default 'A' not null,
	dt_created timestamp default current_timestamp not null ,
	dt_updated timestamp default current_timestamp not null,
	constraint city_pkey primary key(id)
);

create table city_item
(
	id  bigserial not null ,
	id_city  int8  not null ,
	id_uf  varchar(2)  not null ,
	tx_ddd  varchar(2) ,
	nr_lat numeric(10,6) default 0 not null,
	nr_long numeric(10,6) default 0 not null,
	status  varchar(1)   default 'A' not null,
	dt_created timestamp default current_timestamp not null ,
	dt_updated timestamp default current_timestamp not null,

	constraint city_item_pkey primary key(id)
) ;

create  unique index un_city_item on city_item(id_city,id_uf);

alter table city_item add constraint city_item_to_city foreign key (id_city) references city(id);
alter table city_item add constraint city_item_to_uf foreign key (id_uf) references uf(sg_uf);
