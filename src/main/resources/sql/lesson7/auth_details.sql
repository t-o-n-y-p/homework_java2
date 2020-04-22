create table auth_details (
    id serial primary key,
    user_id integer not null unique,
    login varchar(100) not null unique,
    password varchar(50) not null,
    constraint auth_details_user_id_fkey foreign key (user_id) references users(id)
);

insert into auth_details (user_id, login, password) values
(1, 'qwerty', 'asdfgh'),
(2, 'zxcvbn', 'uiop[]'),
(7, 'qazwsx', 'edcrfv'),
(8, 'tgbyhn', 'ujmikl');