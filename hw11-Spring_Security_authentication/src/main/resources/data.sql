insert into authors(full_name) values ('Author_1'), ('Author_2'), ('Author_3');

insert into genres(name) values ('Genre_1'), ('Genre_2'), ('Genre_3'), ('Genre_4'), ('Genre_5'), ('Genre_6');

insert into books(title, author_id)
values ('BookTitle_3', 3), ('BookTitle_1', 1), ('BookTitle_2', 2);

insert into books_genres(book_id, genre_id) values (1, 1),   (1, 2), (2, 3), (2, 4), (3, 5), (3, 6);

insert into comments (book_id, text) values (1, 'good'), (1, 'like');

/*user:password*/
insert into user_data(username, password) values ('user', '$2y$04$gMllrpB0tMcOAYi22jkI1eIIbR/jwNlQOhexTlZ9D90VXwt51A4D.');
