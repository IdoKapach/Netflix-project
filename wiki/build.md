# Build Instructions
These are the instruction to build the different system components

## Prerequisites
- Docker

## Steps
1. **Clone the repository**:
    ```sh
    git clone https://github.com/Ido-Keren/Targil4
    cd Targil4
    ```

2. **Create a .env file**:
    ```sh
    cd Server/
    cp .env.example .env
    ```
    Edit the [`.env`](.env ) file to configure variables.

3. **Build all images**:
    ```sh
    docker compose build
    ```
4. **Run the server + web app**
    ```sh
    docker compose up -d
    ```
## next steps
- access the [web app](run-web.md).
- access the [android app](run-android.md).