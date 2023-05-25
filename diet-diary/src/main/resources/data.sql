insert into nutrient_storage_types_tbl(nutrient_storage_type_id, code, name)
values(1, 'IN_SERVING', 'Порция');
insert into nutrient_storage_types_tbl(nutrient_storage_type_id, code, name)
values(2, 'IN_100_GRAMS', '100 грамм');

insert into roles_tbl(role_id, name) values(1, 'superuser');
insert into roles_tbl(role_id, name) values(2, 'user');

insert into users_tbl(login, password, role_id) values('superuser', '$2a$10$GVFxN7TVbPikn1y8EVaKWeFLHoJDYZiLB2tmMzTkKYrPOl1RnyWhO', 1); -- password
insert into users_tbl(login, password, role_id) values('user', '$2a$10$GVFxN7TVbPikn1y8EVaKWeFLHoJDYZiLB2tmMzTkKYrPOl1RnyWhO', 2); -- password

insert into food_categories_tbl(food_category_id, name) values(1,'Овощи');
insert into food_categories_tbl(food_category_id, name) values(2,'Мясо');
insert into food_categories_tbl(food_category_id, name) values(3,'Гарнир');
insert into food_categories_tbl(food_category_id, name) values(4,'Крупа');
insert into food_categories_tbl(food_category_id, name) values(5,'Сложное блюдо');
insert into food_categories_tbl(food_category_id, name) values(6,'Пищевая добавка');

insert into meal_types_tbl(meal_type_id, name) values(1, 'Завтрак');
insert into meal_types_tbl(meal_type_id, name) values(2, 'Ланч');
insert into meal_types_tbl(meal_type_id, name) values(3, 'Обед');
insert into meal_types_tbl(meal_type_id, name) values(4, 'Полдник');
insert into meal_types_tbl(meal_type_id, name) values(5, 'Ужин');
insert into meal_types_tbl(meal_type_id, name) values(6, 'Перекус');
insert into meal_types_tbl(meal_type_id, name) values(7, 'Праздник');

insert into food_tbl
    (name
    ,calories
    ,proteins_in_grams
    ,fats_in_grams
    ,carbs_in_grams
    ,food_category_id
    ,nutrient_storage_type_id)
values
    ('Куриная грудка варёная'
    ,170
    ,25
    ,7
    ,0
    ,2
    ,2);

insert into food_tbl
    (name
    ,calories
    ,proteins_in_grams
    ,fats_in_grams
    ,carbs_in_grams
    ,food_category_id
    ,nutrient_storage_type_id)
values
    ('Говяжья шея варёная'
    ,254
    ,25.8
    ,16.8
    ,0
    ,2
    ,2);

insert into food_tbl
    (name
    ,calories
    ,proteins_in_grams
    ,fats_in_grams
    ,carbs_in_grams
    ,food_category_id
    ,nutrient_storage_type_id)
values
    ('Отбивная свинная'
    ,250
    ,27.91
    ,14.57
    ,0
    ,2
    ,2);

insert into food_tbl
    (name
    ,calories
    ,proteins_in_grams
    ,fats_in_grams
    ,carbs_in_grams
    ,food_category_id
    ,nutrient_storage_type_id)
values
    ('Картошка отварная'
    ,86
    ,1.7
    ,0.1
    ,18
    ,3
    ,2);

insert into food_tbl
    (name
    ,calories
    ,proteins_in_grams
    ,fats_in_grams
    ,carbs_in_grams
    ,food_category_id
    ,nutrient_storage_type_id)
values
    ('Гречка варёная'
    ,118
    ,4.2
    ,1.1
    ,21.3
    ,4
    ,2);

insert into food_tbl
    (name
    ,calories
    ,proteins_in_grams
    ,fats_in_grams
    ,carbs_in_grams
    ,food_category_id
    ,nutrient_storage_type_id)
values
    ('Спагетти Шебекинские (al-dente)'
    ,170
    ,5.2
    ,0.5
    ,35.2
    ,3
    ,2);

insert into food_tbl
    (name
    ,calories
    ,proteins_in_grams
    ,fats_in_grams
    ,carbs_in_grams
    ,food_category_id
    ,nutrient_storage_type_id)
values
    ('Рис белый варёный'
    ,117
    ,2.9
    ,0.4
    ,25.2
    ,4
    ,2);

insert into food_tbl
    (name
    ,calories
    ,proteins_in_grams
    ,fats_in_grams
    ,carbs_in_grams
    ,food_category_id
    ,nutrient_storage_type_id)
values
    ('Помидор'
    ,24
    ,1.1
    ,0.2
    ,3.8
    ,1
    ,2);

insert into food_tbl
    (name
    ,calories
    ,proteins_in_grams
    ,fats_in_grams
    ,carbs_in_grams
    ,food_category_id
    ,nutrient_storage_type_id)
values
    ('Огурец'
    ,14
    ,0.8
    ,0.1
    ,2.5
    ,1
    ,2);

-- test diet
insert into diets_tbl
  (diet_date
  ,user_id)
values
  (clock_timestamp()::date
  ,1);

insert into meals_tbl
  (diet_id
  ,meal_type_id)
values
  ((select max(diet_id) from diets_tbl)
  ,1);
insert into meals_tbl
  (diet_id
  ,meal_type_id)
values
  ((select max(diet_id) from diets_tbl)
  ,2);

insert into courses_tbl
  (food_id
  ,meal_id
  ,quantity)
values
  (1
  ,(select max(meal_id) from meals_tbl)
  ,100);
insert into courses_tbl
  (food_id
  ,meal_id
  ,quantity)
values
  (2
  ,(select max(meal_id) from meals_tbl)
  ,200);

insert into courses_tbl
  (food_id
  ,meal_id
  ,quantity)
values
  (3
  ,(select min(meal_id) from meals_tbl)
  ,300);
insert into courses_tbl
  (food_id
  ,meal_id
  ,quantity)
values
  (4
  ,(select min(meal_id) from meals_tbl)
  ,400);