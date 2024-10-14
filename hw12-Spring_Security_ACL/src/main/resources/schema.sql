create table authors
(
    id        bigserial,
    full_name varchar(255),
    primary key (id)
);

create table genres
(
    id   bigserial,
    name varchar(255),
    primary key (id)
);
create table comments
(
    id      bigserial,
    text    varchar(255),
    book_id bigint,
    primary key (id)
);
create table books
(
    id         bigserial,
    title      varchar(255),
    author_id  bigint references authors (id) on delete cascade,
    comment_id bigint references comments (id) on delete cascade,
    primary key (id)
);


create table books_genres
(
    book_id  bigint references books (id) on delete cascade,
    genre_id bigint references genres (id) on delete cascade,
    primary key (book_id, genre_id)
);

create table user_data
(
    id      bigserial,
    username    varchar(255),
    password varchar(255),
    primary key (id)
);

create table user_user_roles
(
    id      bigserial,
    user_id   bigserial,
    user_roles varchar(255),
    primary key (id)
);