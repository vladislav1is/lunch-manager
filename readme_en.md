**EN** | [RU](readme.md)

Lunch manager
===============================
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/4da67edcf85b4d068d42bbe2483c3b3d)](https://www.codacy.com/gh/vladislav1is/lunch-manager/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=vladislav1is/lunch-manager&amp;utm_campaign=Badge_Grade)
[![Build Status](https://app.travis-ci.com/vladislav1is/lunch-manager.svg?branch=main)](https://app.travis-ci.com/vladislav1is/lunch-manager)

##### Graduation internship project of [Java Online Projects](https://javaops.ru/view/topjava)
- Source code taken from **TopJava**
- **Lunch manager** was based on this repository

Application stack:
[Spring MVC](https://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html),
[Spring Data JPA](http://projects.spring.io/spring-data-jpa),
[Spring Security](http://projects.spring.io/spring-security/),
[Spring Security
Test](http://spring.io/blog/2014/05/07/preview-spring-security-test-method-security),
[Hibernate ORM](http://hibernate.org/orm/),
[Hibernate Validator](http://hibernate.org/validator/),
[PostgreSQL](http://www.postgresql.org/),
[HSQLDB](http://hsqldb.org/),
[EHCACHE](http://ehcache.org),
[SLF4J](http://www.slf4j.org/),
[JUnit 5](https://junit.org/junit5/),
[Hamcrest](http://hamcrest.org/JavaHamcrest/),
[AssertJ](https://assertj.github.io/doc/),
[Json Jackson](https://github.com/FasterXML/jackson),
[JSP](http://ru.wikipedia.org/wiki/JSP),
[JSTL](http://en.wikipedia.org/wiki/JavaServer_Pages_Standard_Tag_Library),
[Apache Tomcat](http://tomcat.apache.org/),
[WebJars](http://www.webjars.org/),
[jQuery](http://jquery.com/),
[jQuery plugins](https://plugins.jquery.com/),
[Bootstrap](http://getbootstrap.com/),
[DataTables](http://datatables.net/).

-----
## The task is
Design and implement a JSON API using Hibernate/Spring/SpringMVC (or Spring-Boot).


>**Build a voting system for deciding where to have lunch.**
>* 2 types of users: admin and regular users
>* Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
>* Menu changes each day (admins do the updates)
>* Users can vote on which restaurant they want to have lunch at
>* Only one vote counted per user
>* If user votes again the same day:
>   - If it is before 11:00 we assume that he changed his mind.
>   - If it is after 11:00 then it is too late, vote can't be changed
>
>Each restaurant provides new menu each day.
> 
>As a result, provide a link to github repository. It should contain the code, README.md with API documentation and couple curl commands to test it.

P.S.: Assume that your API will be used by a frontend developer to build frontend on top of that.

-----
## Implementation

The voting system for deciding where to have lunch. Each restaurant provides a new menu every day.
Java Enterprise project with registration/authorization and role-based access rights (USER, ADMIN).
All REST interfaces are covered with JUnit tests by Spring MVC Test and Spring Security Test.

>* **2 types of users: admin and regular users**
>* Admin can input a restaurant and its lunch menu of the day (usually 2-5 items)
>* Admin can create/edit/delete users, users - can manage their profile data via UI (AJAX) and REST with basic authorization
>* **Menu changes every day (admins do the updates)**
>* Dishes can be filtered by date
>* Users can vote for restaurant where they want to have lunch
>* **Only one vote is counted for each user**
>* If user votes again the same day:
>   - If it's **before 11:00** we assume that he changed his mind
>   - If it's **after 11:00** it's already late, the vote cannot be changed
>* The restaurants row color **depends on daily user vote (restaurant param)**

#### Restaurant
Describes Restaurant.

>You can't create two restaurants with the same name.

#### Dish
Describes lunch and its price.

>You can't create two lunches at a restaurant with the same name and date.

#### User
Describes the user of this service. Can be a regular user or an administrator (same user, but with extended rights).

>You can't register two users with the same email address.

**Users can** register on service, edit their profile and vote for the selected Restaurant/Menu during the open Poll.

**Administrators can** create/edit/delete: Users, Restaurants, Dishes and perform actions available to regular Users.

#### Vote
Designed to register the User's voice, which he gave for selected Restaurant/Menu during the unfinished Poll.

>You can vote only during the unfinished current Poll (by default - until 11:00). If the User votes a second time during an open Poll, then his previous vote is overwritten.

----

## Description (API)

Credentials:
```
User:  user@yandex.ru / password
Admin: admin@gmail.com / admin
```
curl samples (application deployed at application context `lunch-manager`).
> For windows use `Git Bash`

[REST API documentation](http://localhost:8080/swagger-ui.html)

## Profile resources

-----

### User resources

* #### test UTF
`curl -X GET --location http://localhost:8080/lunch-manager/rest/profile/text --user user@yandex.ru:password`

* #### get User
`curl -X GET --location http://localhost:8080/lunch-manager/rest/profile --user user@yandex.ru:password`

* #### register User
`curl -X POST -d '{"name":"NewUser", "email": "test@mail.ru", "password": "test-password", "enabled":true, "registered":"2021-12-30T09:16:00", "roles":["USER"]}' -H "Content-Type:application/json" --location http://localhost:8080/lunch-manager/rest/profile`

* #### update User
`curl -X PUT -d '{"name":"UpdatedName","email":"user3@mail.ru", "password":"newPass", "enabled":true, "registered":"2021-11-02T21:45:00", "roles":["ADMIN"]}' -H "Content-Type:application/json" --location http://localhost:8080/lunch-manager/rest/profile --user user@yandex.ru:password`

* #### delete User
`curl -X DELETE --location http://localhost:8080/lunch-manager/rest/profile --user user@yandex.ru:password`

* #### validate with Error
`curl -X PUT -d '{"name":" ", "email": "test@mail.ru", "password": "test-password", "enabled":true, "registered":"2022-01-04T11:25:00", "roles":["USER"]}' -H 'Content-Type: application/json' http://localhost:8080/lunch-manager/rest/profile --user user@yandex.ru:password`

### Restaurant resources

* #### get Restaurant
`curl -X GET --location http://localhost:8080/lunch-manager/rest/restaurants/100004 --user user@yandex.ru:password`

* #### get all Restaurants
`curl -X GET --location http://localhost:8080/lunch-manager/rest/restaurants --user user@yandex.ru:password`

* #### get Restaurant with Dishes for Today
`curl -X GET --location http://localhost:8080/lunch-manager/rest/restaurants/100004/with-dishes --user user@yandex.ru:password`

### Dish resources

* #### get Dish
`curl -X GET --location http://localhost:8080/lunch-manager/rest/restaurants/100004/dishes/100009 --user user@yandex.ru:password`

* #### get Dishes For Today
`curl -X GET --location http://localhost:8080/lunch-manager/rest/restaurants/100004/dishes --user user@yandex.ru:password`

### Vote resources

* #### create Vote
`curl -X POST -d '{"registered":"2021-11-06"}' -H "Content-Type: application/json" --location http://localhost:8080/lunch-manager/rest/restaurants/100004/votes --user user@yandex.ru:password`

* #### delete Vote
`curl -X DELETE --location http://localhost:8080/lunch-manager/rest/restaurants/100004/votes/100020 --user user@yandex.ru:password`

* #### update Vote
`curl -X PUT -d '{"registered":"2021-10-06"}' -H "Content-Type: application/json" --location http://localhost:8080/lunch-manager/rest/restaurants/100004/votes/100016 --user user@yandex.ru:password`

* #### get Vote
`curl -X GET --location http://localhost:8080/lunch-manager/rest/restaurants/100004/votes/100017 --user user@yandex.ru:password`

* #### get all Votes
`curl -X GET --location http://localhost:8080/lunch-manager/rest/restaurants/100004/votes --user user@yandex.ru:password`

* #### get Votes between 2021-11-11 and 2021-11-11
`curl -X GET --location http://localhost:8080/lunch-manager/rest/restaurants/100004/votes/filter?startDate=2021-11-11&endDate=2021-11-11 --user user@yandex.ru:password`

## Admin resources

-----

### User resources

* #### create User
`curl -X POST -d '{"name":"CreatedUser", "email": "user4@mail.ru", "password": "123456", "enabled":false, "registered":"2021-11-02T21:45:00", "roles":["USER"]}' -H "Content-Type:application/json" --location http://localhost:8080/lunch-manager/rest/admin/users --user admin@gmail.com:admin`

* #### delete User
`curl -X DELETE --location http://localhost:8080/lunch-manager/rest/admin/users/100019 --user admin@gmail.com:admin`

* #### update User
`curl -X PUT -d '{"name":"UpdatedUser", "email":"user1@yandex.ru", "password":"newPass", "enabled":true, "registered":"2021-11-02T21:45:00", "roles":["ADMIN"]}' -H "Content-Type: application/json" --location http://localhost:8080/lunch-manager/rest/admin/users/100001 --user admin@gmail.com:admin`

* #### get User
`curl -X GET --location http://localhost:8080/lunch-manager/rest/admin/users/100001 --user admin@gmail.com:admin`

* #### get by email
`curl -X GET --location http://localhost:8080/lunch-manager/rest/admin/users/by?email=user1@yandex.ru --user admin@gmail.com:admin`

* #### get all Users
`curl -X GET --location http://localhost:8080/lunch-manager/rest/admin/users --user admin@gmail.com:admin --user admin@gmail.com:admin`

#### validate with Error
`curl -s -X POST -d '{}' -H 'Content-Type: application/json' http://localhost:8080/lunch-manager/rest/admin/users --user admin@gmail.com:admin`

### Restaurant resources

* #### create Restaurant
`curl -X POST -d '{"name":"CreatedRestaurant", "dishes":null}' -H "Content-Type:application/json" --location http://localhost:8080/lunch-manager/rest/admin/restaurants --user admin@gmail.com:admin`

* #### delete Restaurant
`curl -X DELETE --location http://localhost:8080/lunch-manager/rest/admin/restaurants/100006 --user admin@gmail.com:admin`

* #### update Restaurant
`curl -X PUT -d '{"name":"UpdatedRestaurant", "dishes":null}' -H "Content-Type:application/json" --location http://localhost:8080/lunch-manager/rest/admin/restaurants/100005 --user admin@gmail.com:admin`

* #### get Restaurant
`curl -X GET --location http://localhost:8080/lunch-manager/rest/admin/restaurants/100004 --user admin@gmail.com:admin`

* #### get all Restaurants
`curl -X GET --location http://localhost:8080/lunch-manager/rest/admin/restaurants --user admin@gmail.com:admin`

* #### get Restaurant with Dishes by Date
`curl -X GET --location http://localhost:8080/lunch-manager/rest/admin/restaurants/100004/with-dishes?date=2021-11-11 --user admin@gmail.com:admin`

### Dish resources

* #### create Dish
`curl -X POST -d '{"name":"CreatedDish", "price":111, "registered":"2021-11-27"}' -H "Content-Type:application/json" --location http://localhost:8080/lunch-manager/rest/admin/restaurants/100004/dishes --user admin@gmail.com:admin`

* #### delete Dish
`curl -X DELETE --location http://localhost:8080/lunch-manager/rest/admin/restaurants/100004/dishes/100019 --user admin@gmail.com:admin`

* #### update Dish
`curl -X PUT -d '{"name":"UpdatedDish", "price":1000, "registered":"2021-11-13"}' -H "Content-Type: application/json" --location http://localhost:8080/lunch-manager/rest/admin/restaurants/100004/dishes/100009 --user admin@gmail.com:admin`

* #### get Dish
`curl -X GET --location http://localhost:8080/lunch-manager/rest/admin/restaurants/100004/dishes/100009 --user admin@gmail.com:admin`

* #### get all Dishes
`curl -X GET --location http://localhost:8080/lunch-manager/rest/admin/restaurants/100004/dishes --user admin@gmail.com:admin`

* #### get Dishes between 2021-11-27 and 2021-11-27
`curl -X GET --location http://localhost:8080/lunch-manager/rest/admin/restaurants/100004/dishes/filter?startDate=2021-11-27&endDate=2021-11-27 --user admin@gmail.com:admin`

-----

### Security
The app uses **BASIC authentication**. Users are divided into two types:
**administrators** - edit application objects; **users** - can edit their profile and vote.

----

### Application profiles
Application supports 4 profiles: **postgres**, **heroku** и **hsqldb**

>- **postgres** - develop (default). Used PostgreSQL DB; demo data propagation; DB requests and responses logging (see 'postgres.properties').
>
>- **heroku** - for deploy on Heroku. Used PostgreSQL DB; demo data propagation; DB requests and responses logging (see 'heroku.properties').
>
>- **hsqldb** - production mode. H2 in file (see 'hsqldb.properties').
>
>- **hsqldb** - testing. H2 in memory (see 'hsqldb.properties').

And supports 3 types of repositories: **jdbc**, **jpa**, **datajpa**.

A necessary profile is setting as the **_spring.profiles.active_** parameter of the 'application.properties' or as the JVM option **_-Dspring.profiles.active="datajpa,heroku"_** (see 'Procfile').

----

### Launch Application
Application requires: [Java](https://java.com), [Git](https://git-scm.com/) и [Maven](https://maven.apache.org/).
To launch Application type in command line:

```
git clone https://github.com/vladislav1is/lunch-manager.git
cd lunch-manager
```

Than open in browser [http://localhost:8080](http://localhost:8080)

----

### Application demo on Heroku
Demo users:

- **ADMIN**: admin@gmail.com, password: admin
- **USER**: user@yandex.ru, password: password

[http://lunch-manager.herokuapp.com](http://lunch-manager.herokuapp.com)