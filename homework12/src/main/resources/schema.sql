drop table if exists authors_tbl;
create table authors_tbl
(
  id_author bigint primary key auto_increment
 ,name      varchar(255) not null
 ,unique (name)
);

drop table if exists genres_tbl;
create table genres_tbl
(
  id_genre bigint primary key auto_increment
 ,name     varchar(255) not null
 ,unique (name)
);

drop table if exists books_tbl;
create table books_tbl
(
  id_book   bigint primary key auto_increment
 ,name      varchar(255) not null
 ,id_author bigint not null
 ,id_genre  bigint not null
 ,foreign key(id_author) references authors_tbl(id_author)
 ,foreign key(id_genre) references genres_tbl(id_genre)
);

drop table if exists book_comments_tbl;
create table book_comments_tbl
(
  id_book_comment bigint primary key auto_increment
 ,text            varchar(8000)
 ,id_book         bigint not null
 ,foreign key(id_book) references books_tbl(id_book)
);

drop table if exists roles_tbl;
create table roles_tbl
(
  id_role bigint primary key auto_increment
 ,name    varchar(255) not null
 ,unique (name)
);

drop table if exists users_tbl;
create table users_tbl
(
  id_user   bigint primary key auto_increment
 ,username  varchar(255) not null
 ,password  varchar(255) not null
 ,id_role   bigint not null
 ,unique (username)
 ,foreign key(id_role) references roles_tbl(id_role)
);