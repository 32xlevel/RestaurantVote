# Restaurant
### Test task for paid internship

Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) without frontend.

The task is:

Build a voting system for deciding where to have lunch.

* 2 types of users: admin and regular users
* Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
* Menu changes each day (admins do the updates)
* Users can vote on which restaurant they want to have lunch at
* Only one vote counted per user
* If user votes again the same day:
  * If it is before 11:00 we asume that he changed his mind.
  * If it is after 11:00 then it is too late, vote can't be changed
  
Each restaurant provides new menu each day.

As a result, provide a link to github repository.

It should contain the code and README.md with API documentation and curl commands to get data for voting and vote.

---
P.S.: Make sure everything works with latest version that is on github :)

P.P.S.: Asume that your API will be used by a frontend developer to build frontend on top of that.

---

### How to work?

1. Use Java 8 or later
2. Curl commands is in **curl.md** file
3. Database data is in **postgres.properties** file 
4. Use predefined users:
* Role: user, Email: user1@yandex.ru, Password: password
* Role: user, Email: user2@yandex.ru, Password: password
* Role: admin, Email: admin@gmai.com, Password: admin

---

#### Attention! Many JUnit tests are not finalized!