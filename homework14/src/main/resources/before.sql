drop table if exists stg_authors_tbl;
create table stg_authors_tbl
(
  uuid varchar(255)
 ,name varchar(255)
);

drop table if exists stg_books_tbl;
create table stg_books_tbl
(
  uuid        varchar(255)
 ,name        varchar(255)
 ,uuid_author varchar(255)
);

drop table if exists stg_uuids_tbl;
create table stg_uuids_tbl
(
  numeric_id bigint primary key auto_increment
 ,uuid varchar(255) not null
);