INSERT INTO books (id, title, author, isbn, price, cover_image, description)
VALUES (1 ,'Avatar', 'Someone', '12345678', 19.99, 'avatar-new-world.jpg', 'new world');

INSERT INTO books (id, title, author, isbn, price, cover_image, description)
VALUES (2, 'WhoAmI', 'ItAuthor', '13245768', 24.99, 'who-am-i.jpg', 'tech thriller about hacker');

INSERT INTO categories (id, name, description) VALUES (1, 'Fantasy', 'Fantasy books');
INSERT INTO categories (id, name, description) VALUES (2, 'Thriller', 'Thriller books');

INSERT INTO book_category (book_id, category_id) VALUES (1, 1);
INSERT INTO book_category (book_id, category_id) VALUES (2, 2);
INSERT INTO book_category (book_id, category_id) VALUES (2, 1);
