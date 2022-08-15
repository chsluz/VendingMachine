
CREATE TABLE IF NOT EXISTS users(
	id integer auto_increment primary key,
	name varchar(255) not null,
	username varchar(255) not null,
	password varchar(50) not null,
	deposit integer
);
	
CREATE TABLE IF NOT EXISTS roles(
    id integer auto_increment primary key,
    name varchar(50) not null
);
CREATE TABLE IF NOT EXISTS users_roles(
    id_user integer,
    id_role integer,
    foreign key (id_user) references users(id),
    foreign key (id_role) references roles(id)
);

CREATE TABLE IF NOT EXISTS products(
	id integer auto_increment primary key,
	name varchar(255),
	amount_available integer not null,
	cost integer not null,
	id_user integer,
	foreign key (id_user) references users(id)
);

insert into roles(name) values ('BUYER');
insert into roles(name) values ('SELLER');

insert into users(name,username,password,deposit) values ('test seller','tseller','$2a$10$Qr28AdzfaumEYH4.OlQooOvh6UJz0nITNZhyhiOpk/hwVQN.1BSxa',0);
insert into users(name,username,password,deposit) values ('test buyer','tbuyer','$2a$10$Qr28AdzfaumEYH4.OlQooOvh6UJz0nITNZhyhiOpk/hwVQN.1BSxa',0);

insert into users_roles(id_user,id_role) values (1,2);
insert into users_roles(id_user,id_role) values (2,1);

insert into products(name,amount_available,cost,id_user) values ('Diet Coke',10,50,1);
insert into products(name,amount_available,cost,id_user) values ('Cookies',15,20,1);