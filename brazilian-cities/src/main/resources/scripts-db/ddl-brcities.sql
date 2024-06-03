
create table uf
(
	id  varchar(2)  not null ,
	nm_uf  varchar(20)  not null ,
	in_status  varchar(1)   not null,
	dt_created timestamp not null,
	dt_updated timestamp not null ,
	constraint uf_pkey primary key(id)
) ;

create table city
(
	id  integer  not null ,
	nm_city  varchar(50)  not null,
	in_status  varchar(1)   not null,
	dt_created timestamp not null ,
	dt_updated timestamp not null,
	constraint city_pkey primary key(id)
);

create table city_item
(
	id  integer not null ,
	id_city  integer  not null ,
	id_uf  varchar(2)  not null ,
	tx_ddd  varchar(2) ,
	nr_lat numeric(10,6) default 0 not null,
	nr_long numeric(10,6) default 0 not null,
	in_status  varchar(1)  not null,
	dt_created timestamp not null,
	dt_updated timestamp not null ,
	constraint city_item_pkey primary key(id)
) ;

create  unique index un_city_item on city_item(id_city,id_uf);

alter table city_item add constraint city_item_to_city foreign key (id_city) references city(id);
alter table city_item add constraint city_item_to_uf foreign key (id_uf) references uf(id);
