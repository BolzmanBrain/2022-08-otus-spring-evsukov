drop table if exists authors_tbl cascade;
create table authors_tbl
(
  id_author bigint generated always as identity
 ,name      varchar(255) not null
 ,unique (name)
 ,primary key(id_author)
);

drop table if exists genres_tbl cascade;
create table genres_tbl
(
  id_genre bigint generated always as identity
 ,name     varchar(255) not null
 ,unique (name)
 ,primary key(id_genre)
);

drop table if exists books_tbl cascade;
create table books_tbl
(
  id_book   bigint generated always as identity
 ,name      varchar(255) not null
 ,id_author bigint not null
 ,id_genre  bigint not null
 ,primary key(id_book)
 ,constraint fk_books_authors foreign key(id_author) references authors_tbl(id_author)
 ,constraint fk_books_genres  foreign key(id_genre) references genres_tbl(id_genre)
);

drop table if exists book_comments_tbl cascade;
create table book_comments_tbl
(
  id_book_comment bigint generated always as identity
 ,text            varchar(8000)
 ,id_book         bigint not null
 ,primary key(id_book_comment)
 ,constraint fk_comments_books foreign key(id_book) references books_tbl(id_book)
);