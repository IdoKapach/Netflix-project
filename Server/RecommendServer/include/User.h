#ifndef USER
#define USER
#include <string>
#include <vector>
#include <stdexcept>
#include <algorithm>
using namespace std;

class User {
    private:

        unsigned long int userId;
        vector<unsigned long int> movies;

    public:

        User(unsigned long int userId, vector<unsigned long int> movies);
        User(vector<unsigned long int> input);
        unsigned long int getId();
        vector<unsigned long int> getMovies();
        void addMovie(unsigned long int movieId);
        // returns 1 if the movie was deleted successfully, 0 if the movie was not found
        int deleteMovie(unsigned long int movieId);

};

#endif