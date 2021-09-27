CREATE TABLE sugar
(
    id                  serial PRIMARY KEY,
    name                varchar(100),
    color               varchar(30),
    form                varchar(30),
    synchronizationFlag bool,
CONSTRAINT ConUn UNIQUE (name,color,form)
);
/*insert into sugars (name, color, form, url1, url2, synchronizationFlag) values ('red_long_bolivian_sugar.jpg','fsafa','/home/jj/Изображения','fdsf','fdsafsaf',false);
insert into sugars (name, color, form, url1, url2, synchronizationFlag) values ('red_long_bolivian_sugar1.jpg','fsafa','fsfsar','fdsf','fdsafsaf',false);
insert into sugars (name, color, form, url1, url2, synchronizationFlag) values ('white_long_black_cat1.jpg','fsafa','fsfsar','fdsf','fdsafsaf',false);
insert into sugars (name, color, form, url1, url2, synchronizationFlag) values ('white_long_russian-sugar.jpg','fsafa','fsfsar','fdsf','fdsafsaf',false);
insert into sugars (name, color, form, url1, url2, synchronizationFlag) values ('white_long_russian-sugar1.jpg','fsafa','fsfsar','fdsf','fdsafsaf',false);
insert into sugars (name, color, form, url1, url2, synchronizationFlag) values ('white_long_single_cup_coffee.jpg','fsafa','fsfsar','fdsf','fdsafsaf',false);
insert into sugars (name, color, form, url1, url2, synchronizationFlag) values ('white_long_single_cup_coffee1.jpg','fsafa','fsfsar','fdsf','fdsafsaf',false);*/
