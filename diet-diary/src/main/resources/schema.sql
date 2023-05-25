drop table if exists food_categories_tbl cascade;
create table food_categories_tbl
(
    food_category_id bigint
   ,name             varchar(100) not null
   ,create_timestamp timestamp    default clock_timestamp()::timestamp
   ,primary key(food_category_id)
   ,unique(name)
);


drop table if exists nutrient_storage_types_tbl cascade;
create table nutrient_storage_types_tbl
(
    nutrient_storage_type_id bigint
   ,code                     varchar(30) not null
   ,name                     varchar
   ,create_timestamp         timestamp    default clock_timestamp()::timestamp
   ,primary key(nutrient_storage_type_id)
   ,unique(code)
);


drop table if exists roles_tbl cascade;
create table roles_tbl
(
    role_id           bigint
   ,name              varchar(30) not null
   ,create_timestamp  timestamp default clock_timestamp()::timestamp
   ,primary key(role_id)
   ,unique(name)
);


drop table if exists users_tbl cascade;
create table users_tbl
(
    user_id           bigint generated always as identity
   ,login             varchar(30) not null
   ,password          varchar(100) not null
   ,role_id           bigint not null
   ,create_timestamp  timestamp default clock_timestamp()::timestamp
   ,primary key(user_id)
   ,unique(login)
   ,constraint fk_user_role foreign key(role_id) references roles_tbl(role_id)
);


drop table if exists food_tbl cascade;
create table food_tbl
(
    food_id                  bigint generated always as identity
   ,name                     varchar(150) not null
   ,calories                 smallint not null
   ,proteins_in_grams        smallint not null
   ,fats_in_grams            smallint not null
   ,carbs_in_grams           smallint not null
   ,food_category_id         bigint   not null
   ,nutrient_storage_type_id bigint   not null
   ,user_id                  bigint
   ,create_timestamp         timestamp default clock_timestamp()::timestamp
   ,primary key(food_id)
   ,unique(name)
   ,constraint fk_food_food_category foreign key(food_category_id) references food_categories_tbl(food_category_id)
   ,constraint fk_food_nutr_stor_type foreign key(nutrient_storage_type_id) references nutrient_storage_types_tbl(nutrient_storage_type_id)
   ,constraint fk_food_user foreign key(user_id) references users_tbl(user_id)
);
create index food_category_idx on food_tbl(food_category_id);


drop table if exists diets_tbl cascade;
create table diets_tbl
(
    diet_id          bigint generated always as identity
   ,diet_date        date not null default clock_timestamp()::date
   ,user_id          bigint not null
   ,create_timestamp timestamp default clock_timestamp()::timestamp
   ,primary key(diet_id)
   ,unique(user_id, diet_date)
   ,constraint fk_diet_user foreign key(user_id) references users_tbl(user_id)
);


drop table if exists meal_types_tbl cascade;
create table meal_types_tbl
(
    meal_type_id bigint
   ,name         varchar(30) not null
   ,primary key(meal_type_id)
   ,unique(name)
);


drop table if exists meals_tbl cascade;
create table meals_tbl
(
    meal_id        bigint generated always as identity
   ,diet_id        bigint -- removed not null because of Hibernate cascade operations
   ,meal_type_id   bigint not null
   ,meal_timestamp timestamp default clock_timestamp()::timestamp
   ,primary key(meal_id)
   ,constraint fk_meal_diet foreign key(diet_id) references diets_tbl(diet_id)
   ,constraint fk_meal_meal_type foreign key(meal_type_id) references meal_types_tbl(meal_type_id)
);
create index meals_diet_meal_type_idx on meals_tbl(diet_id, meal_type_id);


drop table if exists courses_tbl cascade;
create table courses_tbl
(
    course_id bigint generated always as identity
   ,food_id   bigint not null
   ,meal_id   bigint -- removed not null because of Hibernate cascade operations
   ,quantity  real   not null
   ,primary key(course_id)
   ,unique(meal_id, food_id)
   ,constraint fk_course_food foreign key(food_id) references food_tbl(food_id)
   ,constraint fk_course_meal foreign key(meal_id) references meals_tbl(meal_id)
);