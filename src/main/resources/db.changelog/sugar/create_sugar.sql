create table sugar(
      id serial PRIMARY KEY,
      name text not null ,
      color text not null,
      url text not null ,
      synchronizationFlag boolean not null ,
      description text not null
 );
/*insert into carts(user_id,status) values(1, 'На складе');
insert into carts(user_id,status) values(2, 'В пункте выдачи');
insert into carts(user_id,status) values(3, 'В обработке');
insert into carts(user_id,status) values(4, 'Выдан курьеру');*/
