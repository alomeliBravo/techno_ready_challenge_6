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
└── main
    └── java
        └── com
            └── pikolinc
                ├── config
                │   ├── database
                │   │   └── JdbiProvider.java
                │   ├── json
                │   │   └── GsonProvider.java
                │   ├── DatabaseProvider.java
                │   ├── EnvLoader.java
                │   └── JsonProvider.java
                │
                ├── controller
                │   ├── ItemController.java
                │   ├── OfferController.java
                │   ├── UserController.java
                │   └── WebController.java
                │
                ├── dao
                │   ├── JdbiItemDAO.java
                │   ├── JdbiOffersDAO.java
                │   └── JdbiUserDAO.java
                │
                ├── dto
                │   ├── item
                │   │   ├── ItemCreateDTO.java
                │   │   ├── ItemResponseDTO.java
                │   │   └── ItemUpdateDTO.java
                │   ├── offer
                │   │   ├── OfferCreateDTO.java
                │   │   ├── OfferResponseDTO.java
                │   │   └── OfferUpdateDTO.java
                │   └──user
                │       ├── UserCreateDTO.java
                │       ├── UserResponseDTO.java
                │       └── UserUpdateDTO.java
                │
                ├── enums
                │   └── OfferStatus.java
                │
                ├── exception
                │   ├── AlredyExistException.java
                │   ├── ApiExceptionBase.java
                │   ├── BadRequestException.java
                │   ├── ErrorResponse.java
                │   ├── ForbiddenException.java
                │   ├── GlobalExceptionHandler.java
                │   ├── NotFoundException.java
                │   └── ValidationProvider.java
                │
                ├── mapper
                │   ├── ItemMapper.java
                │   ├── OfferMapper.java
                │   └── UserMapper.java
                │
                ├── model
                │   ├── Item.java
                │   ├── Offer.java
                │   └── User.java
                │
                ├── repository
                │   ├── impl
                │   │   ├── JdbiItemRepository.java
                │   │   ├── JdbiOfferRepository.java
                │   │   └── JdbiUserRepository.java
                │   ├── ItemRepository.java
                │   ├── OfferRepository.java
                │   ├── Repository.java
                │   └── UserRepository.java
                │
                ├── routes
                │   ├── ItemApiRoutes.java
                │   ├── OfferApiRoutes.java
                │   ├── Router.java
                │   ├── UserApiRoutes.java
                │   └── WebApiRoutes.java
                │
                ├── service
                │   ├── impl
                │   │   ├── ItemServiceImpl.java
                │   │   ├── OfferServiceImpl.java
                │   │   └── UserServiceImpl.java
                │   ├── ItemService.java
                │   ├── OfferService.java
                │   └── UserService.java
                │
                ├── utils
                │   ├── OfferStatusEnumValidator.java
                │   ├── RequestValidator.java
                │   └── ValidOfferStatus.java
                │
                ├── web
                │   ├── ItemWebSocketHandler.java
                │   └── OfferWebSocketHandler.java
                │
                └── Main.java
    └── resources
        └── templates
            ├── item-offers.mustache
            └── items.mustache
```

## Endpoints

Users Endpoints

| Method  | Endpoint                      | Description            |
|---------|-------------------------------|------------------------|
| POST    | /api/v1/users                 | Create a new User      |
| GET     | /api/v1/users                 | Get all Users          |
| GET     | /api/v1/users/:id             | Get an User by ID      |
| GET     | /api/v1/users/by-email/:email | Get an User by Email   |
| PUT     | /api/v1/users/:id             | Update an User by ID   |
| DELETE  | /api/v1/users/:id             | Delete an User by ID   |
| OPTIONS | /api/v1/users/:id             | Check if an User exist |


Items Endpoint

| Method  | Endpoint          | Description                  |
|---------|-------------------|------------------------------|
| POST    | /api/v1/items     | Create a new Item            |
| GET     | /api/v1/items     | Get all Items                |
| GET     | /api/v1/items/:id | Get Item by ID               |
| PUT     | /api/v1/items/:id | Update and Item by ID        |
| PATCH   | /api/v1/items/:id | Partial Update an Item by ID |
| OPTIONS | /api/v1/items/:id | Checks if an Item exists     |
| DELETE  | /api/v1/items/:id | Delete an Item by ID         |

Offers Endpoint

| Method  | Endpoint                   | Descriptions                    |
|---------|----------------------------|---------------------------------|
| POST    | /api/v1/offers             | Create a new Offer              |
| GET     | /api/v1/offers             | Get all Offers                  |
| GET     | /api/v1/offers/:id         | Get an Offer by ID              |
| GET     | /api/v1/offers/user/:id    | Get all Offers of an User by ID |
| GET     | /api/v1/offers/item/:id    | Get All Items of an Item by ID  |
| PUT     | /api/v1/offers/:id         | Update an Offer by ID           |
| PUT     | /api/v1/offers/:id/:status | Update status of an Offer by ID |
| OPTIONS | /api/v1/offers/:id         | Checks if an Offer exist        |
| DELETE  | /api/v1/offers/:id         | Delete an Offer by ID           |

Web Enpoints

| Method | Endpoint                     | Description                                | Response Type |
|--------|------------------------------|--------------------------------------------|---------------|
| GET    | /                            | Generate the template with all the items   | HTML          |
| GET    | /items/:id/offers            | Generate the template to offer for an Item | HTML          |
| GET    | /api/items/:id/offers        | Get All the Offers of an Item by ID        | JSON          |
| GET    | /api/items/:id/current-price | Update the current price of an item by ID  | JSON          |

WebSockets

| PATH       | CLASS                 |
|------------|-----------------------|
| /ws/items  | ItemWebSocketHandler  |
| /ws/offers | OfferWebSocketHandler |