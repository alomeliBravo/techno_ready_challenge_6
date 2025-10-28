## Project Overview

Rafael, a recent Systems Engineering graduate, developed a website for selling collectible items for his friend Ramón, a collector and event organizer.

Built with **Java**, **Spark**, **Mustache**, **JavaScript**, and **CSS**, the platform features item listings, auctions, price filtering, and real-time updates via **WebSockets**.

Through collaboration and problem-solving, Rafael optimized site performance and delivered a functional, user-friendly platform, enhancing his professional skills and experience.

# Technologies Used

- Java 17
- Spark Java 2.9.4
- Maven
- H2 Database
- Lombok
- Jakarta Validation
- JDBI3
- JDBI3-SQLObject
- Gson

## Prerequisites

- Java 17 or Higher
- Maven 3.9 or Higher

## Installation

1. Clone the repository

```bash
git clone https://github.com/alomeliBravo/techno_ready_challenge_6.git
```

2. Compile the project

```bash
mvn clean install
```

## How to run

### Setup the environment

Rename .env.example -> .env

```bash
mv .env.example .env
```

Configure .env file

**Example** 

```.env
DB_URL=jdbc:h2:mem:test;DB_CLOSE_DELAY=-1
DB_USER=sa
DB_PASS=
```

**How to run**

```bash
mvn exec:java
```

## Sprint #1

### Validation and Error Handling

The API uses Jakarta Validation annotations to ensure data integrity across all DTOs.

#### Global error handlind

All validation and business logic error are handled by a GlobalExceptionHandler, returning a consistent JSON responses:

| Exception Type                | HTTP Status | Example Message                             |
|-------------------------------|-------------|---------------------------------------------|
| NotFoundException             | 404         | User with id not found                      |
| ForbbidenException            | 403         | This offer doesn't belong to user with id # |
| BadRequestException           | 400         | Body is empty                               |
| AlreadyExistException         | 409         | User with email (email) already exist       |
| ConstraintValidationException | 400         | Email must be valid                         |
| Exception                     | 500         |Sorry. An unexpected error has occurred. Try again later                                             |

## Architecture

This project follows a layered architecture pattern for a clear separation of concerns:

```md
Routes -> Controller -> Service -> Repository -> DTO
```

```md
└── src
    └── main
        ├── java
        │   └── com
        │       └── pikolinc
        │           ├── config
        │           │   ├── database
        │           │   │   └── JdbiProvider.java
        │           │   ├── json
        │           │   │   └── GsonProvider.java
        │           │   ├── DatabaseProvider.java
        │           │   ├── EnvLoader.java
        │           │   └── JsonProvider.java
        │           ├── controller
        │           │   ├── .gitkeep
        │           │   └── UserController.java
        │           ├── dao
        │           │   └── JdbiUserDAO.java
        │           ├── dto
        │           │   └── user
        │           │       ├── UserCreateDTO.java
        │           │       ├── UserResponseDTO.java
        │           │       └── UserUpdateDTO.java
        │           ├── exception
        │           │   ├── AlredyExistException.java
        │           │   ├── ApiExceptionBase.java
        │           │   ├── BadRequestException.java
        │           │   ├── ErrorResponse.java
        │           │   ├── ForbiddenException.java
        │           │   ├── GlobalExceptionHandler.java
        │           │   └── NotFoundException.java
        │           ├── mapper
        │           │   └── UserMapper.java
        │           ├── model
        │           │   ├── .gitkeep
        │           │   └── User.java
        │           ├── repository
        │           │   ├── impl
        │           │   │   └── JdbiUserRepository.java
        │           │   ├── .gitkeep
        │           │   ├── Repository.java
        │           │   └── UserRepository.java
        │           ├── routes
        │           │   ├── Router.java
        │           │   └── UserApiRoutes.java
        │           ├── service
        │           │   ├── impl
        │           │   │   └── UserServiceImpl.java
        │           │   ├── .gitkeep
        │           │   └── UserService.java
        │           ├── web
        │           │   └── .gitkeep
        │           └── Main.java
        └── resources
```

## Endpoints

User Endpoints

| Method  | Endpoint          | Description           |
|---------|-------------------|-----------------------|
| POST    | /api/v1/users     | Create a new User     |
| GET     | /api/v1/users     | Get all Users         |
| GET     | /api/v1/users/:id | Get an User by ID     |
| PUT     | /api/v1/users/:id | Update an User by ID  |
| DELETE  | /api/v1/users/:id | Delete an User by ID  |
| OPTIONS | /api/v1/users/:id | Check if a User exist |



