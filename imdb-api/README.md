# IMDB API

## Learn
- [ ] Spring Boot 3
  - [ ] Java 17 Features
  - [x] [Spring Boot 3 Feature Breakdown](https://www.youtube.com/watch?v=AcaR1wBi6mQ)
  - [ ] [Problem Detail](https://datatracker.ietf.org/doc/html/rfc7807)
  - [ ] GeneratedValue
    - [ ] UUID
  - [ ] ListCrudRepository
  - [ ] RestController
    - [ ] ResponseStatus
  - [ ] spring.datasource
  - [ ] Lombok
  - Spring Security
    - [ ] [Spring Security 6](https://www.youtube.com/watch?v=KxqlJblhzfI)
  - [ ] WebClient & RouteLocator
    - [ ] [Declarative REST Client](https://cloud.spring.io/spring-cloud-netflix/multi/multi_spring-cloud-feign.html)
  - [ ] Actuator & Micrometer (Metrics and Tracing)
    - [ ] [Observability with Spring Boot 3](https://spring.io/blog/2022/10/12/observability-with-spring-boot-3)
- [ ] Docker Compose with Spring Boot
  - [x] [Spring Boot Docker Compose](https://www.youtube.com/watch?v=lS1GwdIfk0c)
  - [ ] [Creating Docker images with Spring Boot](https://spring.io/blog/2020/01/27/creating-docker-images-with-spring-boot-2-3-0-m1)
  - [ ] [Docker Compose Support in Spring Boot 3](https://docs.spring.io/spring-boot/docs/3.1.0/reference/html/features.html#features.docker-compose)
  - [ ] [Buildpacks](https://buildpacks.io)
  - [ ] TestContainers
- [ ] POM
- [ ] Hibernate ORM Tooling
- [ ] Spring Properties
- [ ] Logging
  - [ ] Log4j2
- [ ] Async Exception Handling
- [ ] GraalVM
  - [ ] [Spring Boot in Graal](https://www.youtube.com/watch?v=VRb8JSfI9eg)
  - [ ] [ahead-of-time compilation and GraalVM](https://www.youtube.com/watch?v=TOfYlLjXufw)
  - [ ] Compilation: `mvn clean package -P native`
  - [ ] Memory footprint: `ps -o rss pid`
- [ ] ORM/Database Performance
- [ ] Testing
- [ ] MapReduce
- [ ] Any OLAP Database
- [ ] GraphQL
  - [ ] [Spring for GraphQL](https://docs.spring.io/spring-graphql/docs/current/reference/html/)

## Features

- [x] Users can Sign up using their email and password.
- [ ] Users (whether signed in or not) can search for Movies/TV Shows/Artists.
- [ ] Signed-in Users can leave review for Movies/TV Shows.
- [ ] Signed-in Users can view their previous review and edit/delete them.
- [ ] Signed-in Users can create lists of their favourite Movies/TV Shows.
- [ ] Show top Movies/TV Shows of the week by ratings, searches.
- [ ] Create catalog of top Movies/TV Shows by IMDB rating formula.
- [ ] Admin Users can add new Movies/TV Shows/Artists.
- [ ] Show sentiment for a Movie/TV Show based on user reviews.
- [ ] Flag and remove any review which not appropriate.
- [ ] Search Top Movies/TV Shows by Ratings/Genres.
- [ ] Create a GraphQL Client.

## Local Setup

**Setup Postgres 13**

- See [Docker Configuration](https://github.com/thegauravmahawar/docker/blob/main/postgres-13.yml)
- Create database `imdb`

**Environment Variables**

```shell
export SPRING_PROFILES_ACTIVE=localdev
```

**Setup** `~/.m2/settings.xml`

```xml
<settings>
    <servers>
        <server>
            <id>imdb_local</id>
            <username>pguser</username>
            <password>pgpass</password>
        </server>
    </servers>
</settings>
```

**Run Flyway migrations**

```shell
mvn clean compile flyway:migrate
```

**Start server**

```shell
mvn spring-boot:run
```

**Test API**

```shell
curl --location 'localhost:9292'
```

**Create User**

```shell
curl --location 'localhost:9292/users/signup' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "System Admin",
    "email": "system_admin@example.com",
    "password": "#4tUs_e@tt_A"
}'
```