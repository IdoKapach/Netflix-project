#include "User.h"

// the regular constructor.
User::User(unsigned long int userId, vector<unsigned long int> movies) : userId(userId), movies(movies) {}

// a construntor that gets words in a vector, first value is userId, rest are movies.
User::User(vector<unsigned long int> input) {

    // throw exeption if there's no userId.
    if (input.empty()) {
        throw invalid_argument("No userId found");
    }
    userId = input[0];
    
    // erase userId from input vector and set movies to it.
    input.erase(input.begin());
    movies = input;
}

// getters for userId and movies vector.
unsigned long int User::getId() {
    return userId;
}

vector<unsigned long int> User::getMovies() {
    return movies;
}

void User::addMovie(unsigned long int movieId) {
    // check if the movie is already in the vector.
    for (unsigned long int movie : this->movies) {
        if (movie == movieId) {
            // if the movie is alredy watched, return.
            return;
        }
    }
    // add the movie to the vector.
    movies.push_back(movieId);
    return;
}

int User::deleteMovie(unsigned long int movieId) {
    // check if the movie is in the vector.
    for (unsigned long int movie : this->movies) {
        if (movie == movieId) {
            // if the movie is found, erase it from the vector.
            // erase-remove idiom - removes moves the elements to the end of the vector and returns an iterator to the first element to be erased.
            movies.erase(std::remove(movies.begin(), movies.end(), movieId), movies.end());
            return 1;
        }
    }
    // if the movie is not found, return 0.
    return 0;
}