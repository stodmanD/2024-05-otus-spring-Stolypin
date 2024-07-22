MERGE INTO authors KEY (id, full_name) VALUES (1, 'Nikolay Gogol'), (2, 'Fedor Dostoevsky');

MERGE INTO genres KEY (id, name) VALUES (1, 'Novel');

MERGE INTO books KEY (id, title, genre_id, author_id) VALUES (1, 'Dead souls', 1, 1), (2, 'Crime and punishment', 2, 1);

MERGE INTO comments KEY (id, text, book_id) VALUES (1, 'This book is cool', 1), (2, 'Wow! This awesome!', 1)