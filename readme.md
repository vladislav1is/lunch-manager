**RU** | [EN](readme_en.md)

Менеджер обеда
===============================
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/4da67edcf85b4d068d42bbe2483c3b3d)](https://www.codacy.com/gh/vladislav1is/lunch-manager/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=vladislav1is/lunch-manager&amp;utm_campaign=Badge_Grade)
[![Build Status](https://app.travis-ci.com/vladislav1is/lunch-manager.svg?branch=main)](https://app.travis-ci.com/vladislav1is/lunch-manager)

##### Выпускной проект стажировки [Java Online Projects](https://javaops.ru/view/topjava)
- Исходный код взят из **TopJava**
- **Менеджер обеда** выполнялся на основе этого репозитория

Стек технологий:
[Spring MVC](https://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html),
[Spring Data JPA](http://projects.spring.io/spring-data-jpa),
[Spring Security](http://projects.spring.io/spring-security/),
[Spring Security Test](http://spring.io/blog/2014/05/07/preview-spring-security-test-method-security),
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

## Задание
Разработать и внедрить JSON API с помощью Hibernate/Spring/SpringMVC (or Spring-Boot).

> **Build a voting system for deciding where to have lunch.**
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

P.S.: Предположим, что API будет использоваться разработчиком внешнего интерфейса для создания внешнего интерфейса
поверх этого.

-----

## Реализация
Система голосования для принятия решения о том, где пообедать. **Каждый ресторан предлагает новое меню каждый день.** Проект
Java Enterprise с регистрацией/авторизацией и правами доступа согласно ролям (USER, ADMIN). Все REST интерфейсы покрыты
тестами JUnit с помощью Spring MVC Test и Spring Security Test.

>* **2 типа пользователей: админ и обычные пользователи**
>* Администратор может ввести ресторан и его обеденное меню дня (обычно 2-5 пунктов)
>* Администратор может создавать/редактировать/удалять пользователей, пользователи - могут управлять данными своего профиля через пользовательский интерфейс (AJAX) и REST с базовой авторизацией
>* **Меню меняется каждый день (обновления делают админы)**
>* Обеды можно отфильтровать по дате
>* Пользователи могут проголосовать за ресторан, в котором они хотят пообедать 
>* **Только один голос засчитывается для каждого пользователя**
>* Если пользователь голосует снова в тот же день:
>   - Если **до 11:00**, мы предполагаем, что он передумал
>   - Если **после 11:00** уже поздно, голос не может быть изменен 
>* Цвет строки ресторанов **зависит от ежедневного голосования пользователя (параметр ресторана)**

#### Restaurant - Ресторан
Описывает Ресторан.

> Нельзя создать два ресторана с одним и тем же названием.

#### Dish - Обед
Описывает обед и его цену.

> Нельзя создать два обеда в ресторане с одним и тем же названием и датой.

#### User - Пользователь
Описывает пользователя данного сервиса. Может быть обычным пользователем или администратором (тот же пользователь, но с
расширенными правами).

> Нельзя зарегистрировать двух пользователей с одним и тем же адресом электронной почты.

**Пользователи могут** регистрироваться на сервисе, редактировать свой профиль и голосовать за выбранный Ресторан/Меню в
течение открытого Опроса.

**Администраторы могут** создавать/редактировать/удалять: Пользователей, Рестораны, Обеды и выполнять действия доступные обычным
Пользователям.

#### Vote - Голос

Предназначен для регистрации голоса Пользователя, который он отдал за выбранный Ресторан/Меню в течение незавершенного
Опроса.

> Голосовать можно только в течение незавершенного текущего Опроса (по умолчанию - до 11:00). Если Пользователь голосует второй раз в течение открытого Опроса, то его предыдущий голос перезаписывается.

----

## Описание (API)

Креденшелы:
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

* #### get All Restaurants
`curl -X GET --location http://localhost:8080/lunch-manager/rest/restaurants --user user@yandex.ru:password`

* #### get Restaurant with Dishes for Today
`curl -X GET --location http://localhost:8080/lunch-manager/rest/restaurants/100004/with-dishes --user user@yandex.ru:password`

### Dish resources

* #### get Dish
`curl -X GET --location http://localhost:8080/lunch-manager/rest/restaurants/100004/dishes/100009 --user user@yandex.ru:password`

* #### get Dishes For Today
`curl -X GET --location http://localhost:8080/lunch-manager/rest/restaurants/100004/dishes --user user@yandex.ru:password`

### Vote resources

* #### vote today
`curl -X POST -H 'Content-Type: application/json' --location "http://localhost:8080/lunch-manager/rest/votes?restaurantId=100004 --user user@yandex.ru:password`

* #### re-vote today
`curl -X PUT -H 'Content-Type: application/json' --location "http://localhost:8080/lunch-manager/rest/votes?restaurantId=100004 --user user@yandex.ru:password`

* #### delete today
`curl -X DELETE --location http://localhost:8080/lunch-manager/rest/votes --user user@yandex.ru:password`

* #### get own Vote by Date
`curl -X GET --location http://localhost:8080/lunch-manager/rest/votes/by-date?voteDate=2021-11-11 --user user@yandex.ru:password`

* #### get own Votes
`curl -X GET --location http://localhost:8080/lunch-manager/rest/votes --user user@yandex.ru:password`

* #### count Restaurant Votes
`curl -X GET --location http://localhost:8080/lunch-manager/rest/votes/count?voteDate=2021-11-11&restaurantId=100004 --user user@yandex.ru:password`

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

* #### validate with Error
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
`curl -X GET --location http://localhost:8080/lunch-manager/rest/admin/restaurants/100005/with-dishes?date=2021-11-11 --user admin@gmail.com:admin`

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

### Безопасность
В приложении используется **BASIC authentication**. Пользователи делятся на два типа:
**администраторы** - создают опросы, редактируют объекты приложения; **пользователи** - могут редактировать свой профиль и
голосовать.

----

### Профили приложения
Реализована поддержка 4-х профилей: **postgres**, **heroku** и **hsqldb**

>- **postgres** - разработка (профиль по-умолчанию). Используется БД PostgreSQL; заполняется демо данными; настроено логирование запросов в БД и логирование результатов (см. файл 'postgres.properties').
>
>- **heroku** - предназначен для деплоя на Heroku. Используется БД PostgreSQL; заполняется демо данными; настроено логирование запросов в БД и логирование результатов (см. файл 'heroku.properties').
>
>- **hsqldb** - предназначен для работы на продуктиве. Используется БД H2 в файле (см. файл 'hsqldb.properties').
>
>- **hsqldb** - предназначен для выполнения тестов. Используется БД H2 в памяти (см. файл 'hsqldb.properties').

И поддержка 3-х типов хранилищ: **jdbc**, **jpa**, **datajpa**.

Для задания нужного профиля, необходимо указать его в параметре **_spring.profiles.active_** в 'application.properties' либо как опцию JVM при запуску приложения **_-Dspring.profiles.active="datajpa,heroku"_** (см. файл 'Procfile').

----

### Запуск приложения
Для запуска приложения требуется установленные [Java](https://java.com), [Git](https://git-scm.com/) и [Maven](https://maven.apache.org/).
В командной строке выполнить команды:

```
git clone https://github.com/vladislav1is/lunch-manager.git
cd lunch-manager
```

Затем открыть в браузере адрес [http://localhost:8080](http://localhost:8080)

----

### Демонстрация работы приложения на Heroku
Демо пользователи:

- **ADMIN**: admin@gmail.com, пароль: admin
- **USER**: user@yandex.ru, пароль: password

[http://lunch-manager.herokuapp.com](http://lunch-manager.herokuapp.com)
