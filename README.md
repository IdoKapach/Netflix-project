# Targil 3 Project

## Overview
A dual-server setup:  
- **RecommendServer**: A C++ application providing a recommendation engine.  
- **ApiServer**: A C++ microservice providing a recommendation engine (not directly accessible by users).

## Features
- Movie management (add, delete, list movies)  
- Categories management (add, delete, list categories)
- User mangement (register, login, view users)
- User authentication (protected endpoints)  

## Prerequisites
- Docker

## Building & Running
1. clone the repo:
    ``` sh
    git clone https://github.com/Ido-Keren/Targil3
    cd Targil3
    ```
2. Create a .env file by copying .env.example:
    ``` sh
    cp .env.example .env
    ```
    Edit the .env file to configure variables.
    
4. Build all images:
    ``` sh
    docker compose build
    ```
5. Run the containers:
    ```sh
    docker compose up
    ```

## Usage
1.  The API server will be available at `http://localhost:{API_PORT}`. 
2.  You can use tools like [Bruno](https://www.usebruno.com/) or curl to interact with the API endpoints.
### Examples
![alt text](graphics/getUser0.png)
![alt text](graphics/getUser1.png)
![alt text](graphics/recommend.png)

## API Endpoints
| Endpoint                              | Method | Description    |
|---------------------------------------|--------|----------------|
| `/api/users`                          | POST   | Create a new User|
| `/api/users/:id`                      | GET    | Get details of a user by ID|
| `/api/tokens`                         | POST   | Authenticate a user and return a token|
| `/api/categories`                     | GET    | Get all categories|
| `/api/categories`                     | POST   | Create a new category|
| `/api/categories/:id`                 | GET    | Get details of a category by ID|
| `/api/categories/:id`                 | PATCH  | Update a category by ID|
| `/api/categories/:id`                 | DELETE | Delete a category by ID|
| `/api/movies`                         | GET    | Get a list of movies, including promoted categories and recently watched|
| `/api/movies`                         | POST   | Create a new movie|
| `/api/movies/:id`                     | GET    | Get details of a movie by ID|
| `/api/movies/:id`                     | PUT    | Replace an existing movie by ID|
| `/api/movies/:id`                     | DELETE | Delete a movie by ID|
| `/api/movies/:id/recommend`           | GET    | Get recommended movies for a user based on a specific movie|
| `/api/movies/:id/recommend`           | POST   | Add a movie to the recommendation system for a user|
| `/api/movies/search/:query`           | GET    | Search for movies based on a query string|