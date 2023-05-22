create table student (birthdate date, id bigint generated by default as identity, email varchar(255), first_name varchar(255), image_base64 varchar(255), last_name varchar(255), password varchar(255), primary key (id));
create table teacher (birthdate date, id bigint generated by default as identity, description varchar(255), email varchar(255), first_name varchar(255), image_base64 varchar(255), last_name varchar(255), password varchar(255), primary key (id));
