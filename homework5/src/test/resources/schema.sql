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
 ,name      varchar(255)
 ,id_author bigint not null
 ,id_genre  bigint not null
 ,foreign key(id_author) references authors_tbl(id_author)
 ,foreign key(id_genre) references genres_tbl(id_genre)
);
