DELETE FROM user_roles;
DELETE FROM dishes;
DELETE FROM votes;
DELETE FROM restaurants;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('user1', 'user1@yandex.ru', '{noop}password'),
  ('user2', 'user2@yandex.ru', '{noop}password'),
  ('admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_USER', 100001),
  ('ROLE_ADMIN', 100002);

INSERT INTO restaurants (name) VALUES
  ('Японский'),
  ('Итальянский'),
  ('Российский'),
  ('Китайский');

INSERT INTO dishes (date, name, price, restaurant_id) VALUES
  ('2018-03-24', 'Суши', 500, 100003),
  ('2018-03-24', 'Напиток', 700, 100003),
  ('2018-03-24', 'Напиток2', 100, 100003),
  ('2018-03-24', 'Макароны', 1200, 100004),
  ('2018-03-24', 'Паста', 1500, 100004),
  ('2018-03-24', 'Пельмени', 800, 100005),
  ('2018-03-24', 'Пюре', 400, 100005),
  ('2018-03-24', 'Салат', 700, 100005),
  ('2018-03-24', 'Картошка', 400, 100005),
  ('2018-03-24', 'Роллы', 600, 100006),
  ('2018-03-24', 'Суп', 1100, 100006),
  ('2018-03-25', 'Жаркое', 350, 100003);

INSERT INTO votes (user_id, restaurant_id, date) VALUES
  (100000, 100003, '2018-03-24'),
  (100001, 100004, '2018-03-24'),
  (100002, 100003, '2018-03-24'),
  (100000, 100003, '2018-03-25'),
  (100001, 100004, '2018-03-25');