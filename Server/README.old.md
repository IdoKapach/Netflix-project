# Targil 2

## Overview
- Targil 2 is a C++ project that provides a movie recommendation system.
- The system allows users to add movies to their watched list, delete movies, and get movie recommendations based on their viewing history.
- The project includes various components such as data management, command execution, and recommendation algorithm.
- A key feature of the system is its support for multiple clients connecting to a server using TCP. The server handles client requests concurrently using a threaded TCP server implementation.

## Project Development Overview
At this part of the project, we made relatively minor changes in the existing code (mostly in main.cpp and app.cpp) besides updates to support wider functionality (in IDataManager, for instance). This was made possible thanks to:
- Working with interfaces (abstract classes), which allows us to implement the "loose coupling" principle.
- Implementing functions and classes that fulfill specific needs.
- Creating new classes as decorators to old classes.
-  Developing using the TDD method, which helped us ensure that each class fulfills its purpose separately—almost without dependence on another class's implementation—and therefore, gaining loose coupling.

## Run Example
![proof](graphics/proof.jpg)

## Prerequisites
- CMake (version 3.14 or higher)
- A C++ compiler (e.g., g++)

# Project Instructions
## Building the Project
1. **Clone the directory**:
    ``` sh
    git clone https://github.com/Ido-Keren/Targil2.git
    cd Targil2
    ```
2. **Create a build directory**:
   ```sh
   cd RecommendServer
   mkdir -p build
   cd build
   ```
3. **Configure Cmake**
    ```sh
    cmake ..
    ```
4. **build the project**
    ```sh
    make
    ```
## Runnig the Project
### Running the Server
After building the project you can run the server executable:
```sh
RecommendServer/build/targil2 <port>
```
### Running the client
```sh
python3 RecommendServer/src/client.py <server-ip> <port>
```
## Docker
build all images with
```sh
docker compose build
```
### Server
run the server with
```sh
docker compose run --name server main <port>
```
default port is 8080
### Client
once the server is running - run the client with
```sh
docker compose run client <server-ip> <port>
```
- when connecting to a server in a container - server-ip = "server"

## Contributing Code
To add more code to this project, please follow these steps:  

1. Add new source (.cpp) and header (.h) files:  
    * Place new header file in the ```include``` directory.
    * Place new source files in the ```src``` directory.
2. Update ```src/CMakeLists.txt```
    * Add the new source files to the ```SRC_FILES``` list in ```src/CMakeLists.txt```

# Test Instructions
## Running the Tests
1. Naviagte to the build directory:
    ```sh
    cd RecommendServer/build    
    ```
2. Run Cmake (if project **not** already configured):
    ```sh
    cmake ..
    ```
3. Build the project:
    ```sh
    make
    ```
4. Run the test executable:
    ```sh
    RecommendServer/build/test/MyTests
    ```
This will execute all the tests defined in your test files and output the results to the console.

## Testing with docker
1. Build the test docker image
    ```sh
    docker compose build
    ```
2. Run the test docker container
    ``` sh
    docker compose run test
    ```

## Adding more Tests
To add more tests to this project, please follow these steps:  
**We only use GTest**
1. Create new test files: 
    * Place new test files in the ```test``` directory.
2. Update ```test/CMakeLists.txt```
    * Add the new test files to the ```TEST_FILES``` list in ```test/CMakeLists.txt```

## Generating Code Coverage
To generate code coverage reports, follow these steps:

1. **Install `lcov`** (if not already installed):
    ```sh
    sudo apt-get install lcov
    ```

2. **Build the project with coverage flags**:
    ```sh
    cd build
    cmake -DCOVERAGE=ON ..
    make
    ```

3. **Run the tests**:
    ```sh
    ./tests/MyTests
    ```

4. **Capture the coverage data**:
    ```sh
    lcov --capture --directory . --output-file coverage.info
    ```

5. **Generate HTML report**:
    ```sh
    genhtml coverage.info --output-directory out
    ```

6. **View the report**:
    Open `out/index.html` in a web browser to view the coverage report.
