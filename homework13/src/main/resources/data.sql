insert into authors_tbl (`name`) values ('Jack London');
insert into authors_tbl (`name`) values ('Ray Bradbury');

insert into genres_tbl (`name`) values ('Novel');
insert into genres_tbl (`name`) values ('Science fiction');

insert into books_tbl (`name`, id_author, id_genre) values ('The Call of the Wild',1,1);
insert into books_tbl (`name`, id_author, id_genre) values ('Martian Chronicles',2,2);

insert into book_comments_tbl(`text`,id_book)  values('Захватывающе',1);
insert into book_comments_tbl(`text`,id_book)  values('Меня это потрясло до глубины души!',1);
insert into book_comments_tbl(`text`,id_book)  values('Убийца - садовник',2);
insert into book_comments_tbl(`text`,id_book)  values('We need MOAR lesbian cosplayers',2);

insert into roles_tbl(id_role,`name`) values (1,'user');
insert into roles_tbl(id_role,`name`) values (2,'superuser');

-- password: password
insert into users_tbl(id_user, username, password, id_role)
values(1, 'user','$2a$10$GVFxN7TVbPikn1y8EVaKWeFLHoJDYZiLB2tmMzTkKYrPOl1RnyWhO', 1);

insert into users_tbl(id_user, username, password, id_role)
values(2, 'superuser','$2a$10$GVFxN7TVbPikn1y8EVaKWeFLHoJDYZiLB2tmMzTkKYrPOl1RnyWhO', 2);