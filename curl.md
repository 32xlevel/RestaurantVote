### CURL Commands

---
##### RESTAURANT

get Restaurant 100003

`curl -s http://localhost:8080/restaurant/100003 --user user1@yandex.ru:password`


get All Restaurant with all Votes

`curl -s http://localhost:8080/restaurant --user user1@yandex.ru:password`


get All Restaurant with Votes for date

`curl -s http://localhost:8080/restaurant/date?date=2018-03-24 --user user1@yandex.ru:password`


get All Restaurant with all Votes and Menu for date

`curl -s http://localhost:8080/restaurant/menu?date=2018-03-24 --user user1@yandex.ru:password`


get All Restaurant with Votes and Menu for date

`curl -s http://localhost:8080/restaurant/date/menu?date=2018-03-24 --user user1@yandex.ru:password`


get All Restaurant with history Votes from date to date

`curl -s "http://localhost:8080/restaurant/date/menu?startDate=2018-03-24&endDate=2018-03-25" --user user1@yandex.ru:password`


get All Restaurant with history Votes from date to date and Menu for date

`curl -s "http://localhost:8080/restaurant/history/menu?startDate=2018-03-24&endDate=2018-03-25&dateMenu=2018-03-24" --user user1@yandex.ru:password`


get All Restaurant with history Votes from date to date and history Menu from date to date 

`curl -s "http://localhost:8080/restaurant/history/menu/history?startDateVoice=2018-03-24&endDateVoice=2018-03-25&startDateMenu=2018-03-24&endDateMenu=2018-03-25" --user user1@yandex.ru:password`


create Restaurant

`curl -s -X POST -d '{"name":"Dizzy"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/restaurant --user admin@gmail.com:admin`


delete Restaurant 100003

`curl -s -X DELETE http://localhost:8080/restaurant/100003 --user admin@gmail.com:admin`


update Restaurant 100003

`curl -s -X PUT -d '{"id":100003,"name":"newRestaurant"}' -H 'Content-Type: application/json' http://localhost:8080/restaurant/100003 --user admin@gmail.com:admin`


---
##### MENU


get All Dish from Restaurant 100003 for date

`curl -s http://localhost:8080/menu/100003?date=2018-03-24 --user user1@yandex.ru:password`


get history Dish from Restaurant 100003 from date to date

`curl -s "http://localhost:8080/menu/history/100003?startDate=2018-03-24&endDate=2018-03-25" --user user1@yandex.ru:password`


create Dish for Restaurant 100003

`curl -s -X POST -d '{"name":"Cacke","price":100}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/menu/100003 --user admin@gmail.com:admin`


update Dish 100007 for Restaurant 100003

`curl -s -X PUT -d '{"id":100007,"date":"2018-03-26","name":"Pudding","price":100}' -H 'Content-Type: application/json' http://localhost:8080/menu/100003/100007 --user admin@gmail.com:admin`


delete Dish 100007

`curl -s -X DELETE http://localhost:8080/menu/100007 --user admin@gmail.com:admin`


get Dish 100007

`curl -s http://localhost:8080/menu/is/100007 --user user1@yandex.ru:password`


---
##### VOICES

get All Votes from date

`curl -s http://localhost:8080/vote?date=2018-03-24 --user user1@yandex.ru:password`


get All Votes from Restaurant 100003 for date

`curl -s http://localhost:8080/vote/100003?date=2018-03-24 --user user1@yandex.ru:password`


get history Votes from Restaurant 100003 from date to date

`curl -s "http://localhost:8080/vote/history/100003?startDate=2018-03-24&endDate=2018-03-25" --user user1@yandex.ru:password`


get history All Votes from date to date

`curl -s "http://localhost:8080/vote/history?startDate=2018-03-24&endDate=2018-03-25" --user user1@yandex.ru:password`


get Votes for User 100000 for date

`curl -s http://localhost:8080/vote/is/100000?date=2018-03-24 --user user1@yandex.ru:password`


Votes to Restaurant 100003

`curl -s -X POST http://localhost:8080/vote/100003 --user user1@yandex.ru:password`


---
##### USER

Registration

`curl -s -X POST -d '{"name":"newUser","email":"newuser@ya.ru","password":"newPass"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/login/registration`


get User

`curl -s http://localhost:8080/profile --user user1@yandex.ru:password`


update User

`curl -s -X PUT -d '{"id":100001,"name":"updateUser","email":"update@ya.ru","password":"newPass"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/profile --user user2@yandex.ru:password`


delete User

`curl -s -X DELETE http://localhost:8080/profile --user user2@yandex.ru:password`


---
##### Admin

get All User

`curl -s http://localhost:8080/admin/users --user admin@gmail.com:admin`


get By email

`curl -s http://localhost:8080/admin/users/by?email=user1@yandex.ru --user admin@gmail.com:admin`


get User 100000

`curl -s http://localhost:8080/admin/users/100000 --user admin@gmail.com:admin`


create User

`curl -s -X POST -d '{"name":"newUser","email":"newuser@ya.ru","password":"newPass"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/admin/users/ --user admin@gmail.com:admin`


update User

`curl -s -X PUT -d '{"id":100000,"name":"updateUser","email":"update@ya.ru","password":"newPass"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/admin/users/100000 --user admin@gmail.com:admin`


delete User 100000

`curl -s -X DELETE http://localhost:8080/admin/users/100000 --user admin@gmail.com:admin`