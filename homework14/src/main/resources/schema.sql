drop table if exists authors_tbl;
create table authors_tbl
(
  id_author bigint primary key
 ,name      varchar(255) not null
 ,unique (name)
);

drop table if exists books_tbl;
create table books_tbl
(
  id_book   bigint primary key
 ,name      varchar(255) not null
 ,id_author bigint not null
 ,foreign key(id_author) references authors_tbl(id_author)
);