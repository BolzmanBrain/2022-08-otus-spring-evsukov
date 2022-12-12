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
