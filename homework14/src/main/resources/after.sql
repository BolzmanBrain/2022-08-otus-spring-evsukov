-- get all unique uuids and assign ids to them
insert into stg_uuids_tbl(uuid)
select distinct uuid
  from stg_authors_tbl
 where uuid not in (select uuid
                     from stg_uuids_tbl);

insert into stg_uuids_tbl(uuid)
select distinct uuid
  from stg_books_tbl
 where uuid not in (select uuid
                      from stg_uuids_tbl);

-- insert data from stage to target tables
insert into authors_tbl(id_author, name)
select u.numeric_id
      ,a.name
  from stg_authors_tbl a
  join stg_uuids_tbl u on a.uuid = u.uuid;

insert into books_tbl(id_book, name, id_author)
select ub.numeric_id
      ,b.name
      ,ua.numeric_id
  from stg_books_tbl b
  join stg_uuids_tbl ub on b.uuid = ub.uuid
  join stg_uuids_tbl ua on b.uuid_author = ua.uuid;

-- drop stage tables
drop table stg_authors_tbl;
drop table stg_books_tbl;
drop table stg_uuids_tbl;