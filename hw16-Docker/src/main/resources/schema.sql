
create table IF NOT EXISTS authors
(
    id        bigserial,
    full_name varchar(255),
    primary key (id)
);

create table IF NOT EXISTS genres
(
    id   bigserial,
    name varchar(255),
    primary key (id)
);
create table IF NOT EXISTS comments
(
    id      bigserial,
    text    varchar(255),
    book_id bigint,
    primary key (id)
);
create table IF NOT EXISTS books
(
    id         bigserial,
    title      varchar(255),
    author_id  bigint references authors (id) on delete cascade,
    comment_id bigint references comments (id) on delete cascade,
    primary key (id)
);


create table IF NOT EXISTS books_genres
(
    book_id  bigint references books (id) on delete cascade,
    genre_id bigint references genres (id) on delete cascade,
    primary key (book_id, genre_id)
);