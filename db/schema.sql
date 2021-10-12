CREATE TABLE accident (
    id serial primary key,
    name varchar(2000),
    text varchar(2000),
    address varchar(2000),
    type_id int references type(id)
    );

CREATE TABLE type (
    id serial primary key,
    name varchar(2000)
);

CREATE TABLE rule (
    id serial primary key,
    name varchar(2000)
);

CREATE TABLE if not exists accident_rule (
    id serial primary key,
    accident_id int references accident(id),
    rule_id int references rule(id)
);

insert into type(id, name) values (1, 'Type1');
insert into type(id, name) values (2, 'Type2');
insert into type(id, name) values (3, 'Type3');
