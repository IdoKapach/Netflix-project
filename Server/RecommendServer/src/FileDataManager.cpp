#include "FileDataManager.h"

FileDataManager::FileDataManager(std::string directoryPath, std::string delimiter) {
    this->directoryPath = directoryPath;
    this->delimiter = delimiter;
    // create the directory if it does not exist
    std::filesystem::create_directories(directoryPath);
    this->reset();
}

FileDataManager::~FileDataManager() {
    // nothing to do here
}

int FileDataManager::addMovie(unsigned long userId, unsigned long movieId) {
    User* user = nullptr;
    try {
        // load the user from file
        user = this->loadUser(userId);
        if (user == nullptr) {
            // create a new user if it does not exist
            user = new User(userId, {});
        }
        // add the movie to the user class (not in file)
        user->addMovie(movieId);
        // save the user to file
        this->saveUser(user);
        // delete the user object
        delete user;
        // return 1 if the movie was added successfully
        return 1;
    } catch (const std::exception& e) {
        delete user;
        std::cerr << "Error: " << e.what() << std::endl;
        return 0;
    }
    delete user;
}

int FileDataManager::deleteMovie(unsigned long userId, unsigned long movieId) {
    User* user = nullptr;
    try {
        // load the user from file
        user = this->loadUser(userId);
        if (user == nullptr) {
            // return 0 if the user does not exist
            return 0;
        }
        // delete the movie from the user class (not in file)
        if (!user->deleteMovie(movieId)) {
            // the movie wasn't found
            // delete the user object
            delete user;
            // return 0 if the movie was not found
            return 0;
        }
        // save the user to file
        this->saveUser(user);
        // delete the user object
        delete user;
        // return 1 if the movie was deleted successfully
        return 1;
    } catch (const std::exception& e) {
        if (user != nullptr) {
            // delete the user object if an exception was thrown
            delete user;
        }
        std::cerr << "Error: " << e.what() << std::endl;
        return 0;
    }
}

bool FileDataManager::hasMovie(unsigned long userId, unsigned long movieId) {
    User* user = nullptr;
    try {
        // load the user from file
        user = this->loadUser(userId);
        if (user == nullptr) {
            // return false if the user does not exist
            return false;
        }
        vector<unsigned long> movies = user->getMovies();
        // delete the user object
        delete user;
        // return true if the movie is in the user's list, false otherwise
        return std::find(movies.begin(), movies.end(), movieId) != movies.end();
    } catch (const std::exception& e) {
        if (user != nullptr) {
            // delete the user object if an exception was thrown
            delete user;
        }
        std::cerr << "Error: " << e.what() << std::endl;
        return false;
    }
}

bool FileDataManager::hasUser(unsigned long userId) {
    // create the file path from the user ID
    std::string filePath = this->directoryPath + "/" + std::to_string(userId) + ".txt";
    // check if the file exists
    return std::filesystem::exists(filePath);
}

User* FileDataManager::loadUser(unsigned long userId) {
    // create the file path from the user ID
    std::string filePath = this->directoryPath + "/" + std::to_string(userId) + ".txt";
    // open the file
    std::ifstream file(filePath);
    if (!file.is_open()) {
        return nullptr;
    }
    std::string line;
    // read the only line
    std::getline(file, line);
    file.close();
    // parse the line to get the movies as a vector
    std::vector<unsigned long> movies = this->parseMovies(line);
    // return a new User object with updated movies
    return new User(userId, movies);
}

void FileDataManager::saveUser(User* user) {
    // create the file path from the user ID
    std::string filePath = this->directoryPath + "/" + std::to_string(user->getId()) + ".txt";
    // open the file + truncate (clear) it
    std::fstream file(filePath, std::ios::out | std::ios::trunc);
    if (!file.is_open()) {
        throw std::ios_base::failure("Failed to open user file");
    }
    std::vector<unsigned long> movies = user->getMovies();
    // write all the movies to the (now clear) file one by one
    for (size_t i = 0; i < movies.size(); i++) {
        file << movies[i];
        if (i < movies.size() - 1) {
            file << this->delimiter;
        }
    }
    file.close();
}

std::vector<unsigned long> FileDataManager::parseMovies(std::string line) {
    std::vector<unsigned long> movies;
    size_t startPos = 0;
    // find the first delimiter (it will find the end of the first movie)
    size_t endPos = line.find(this->delimiter);
        
    while (endPos != std::string::npos) {
        // extract the movie
        std::string token = line.substr(startPos, endPos - startPos);
        // convert the movie to an unsigned long and add it to the vector
        movies.push_back(std::stoul(token));
        // move the start position to the next token
        startPos = endPos + this->delimiter.length();
        // find the next delimiter
        endPos = line.find(this->delimiter, startPos);
    }

    // Add the last token (if any)
    if (startPos < line.length()) {
        std::string token = line.substr(startPos, line.length() - startPos);
        movies.push_back(std::stoul(token));
    }

    return movies;
}

User FileDataManager::getNextUser() {
    // check if there are more users in the iterator
    if (!this->hasMoreUsers()) {
        throw std::out_of_range("No more users");
    }

    // get the file path of the next user
    std::string filePath = this->userFilesIterator->string();
    // open the file
    std::ifstream file(filePath);
    if (!file.is_open()) {
        throw std::ios_base::failure("Failed to open user file");
    }
    std::string line;
    // read the only line
    std::getline(file, line);
    file.close();

    // parse the line to get the movies as a vector
    std::vector<unsigned long> movies = this->parseMovies(line);
    // get the user ID from the file name
    std::string fileName = this->userFilesIterator->filename().string();
    // the file format is <userId>.txt
    // so we extract the user ID by removing the ".txt" extension
    std::string userIdStr = fileName.substr(0, fileName.find("."));
    // convert the user ID to an unsigned long
    unsigned long userId = std::stoul(userIdStr);

    // increment the iterator
    this->userFilesIterator++;

    // return a new User object with the user ID and movies
    return User(userId ,movies);
}

bool FileDataManager::hasMoreUsers() {
    // check if the iterator is at the end of the list
    return this->userFilesIterator != this->userFiles.end();
}

void FileDataManager::reset() {
    this->userFiles.clear();
    // populate the list of user files
    for (const auto& entry : std::filesystem::directory_iterator(this->directoryPath)) {
        this->userFiles.push_back(entry.path());
    }
    // sort the list of user files
    std::sort(this->userFiles.begin(), this->userFiles.end());
    this->userFilesIterator = this->userFiles.begin();
}