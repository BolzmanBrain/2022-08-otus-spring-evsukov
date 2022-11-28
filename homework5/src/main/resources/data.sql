insert into authors_tbl (`name`) values ('Jack London');
insert into authors_tbl (`name`) values ('Ray Bradbury');

insert into genres_tbl (`name`) values ('Novel');
insert into genres_tbl (`name`) values ('Science fiction');

insert into books_tbl (`name`, id_author, id_genre) values ('The Call of the Wild',1,1);
insert into books_tbl (`name`, id_author, id_genre) values ('Martian Chronicles',2,2);
