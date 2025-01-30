#include "Adder.h"

// constructor to decide which dataManager will be used.
Adder::Adder(IDataManager* dataManager) : dataManager(dataManager) {}

// function returns the instructions for how to use add.
string Adder::getInstructions() {
    return "add [userid] [movieid1] [movieid2] ...";
}

// override execute for "add": add a user and his movies to the database.
string Adder::execute(vector<unsigned long int> arr) {

    // throw exeption if less than 2 args recieved.
    if (arr.size() < 2) {
        throw invalid_argument("Arguments missing");
    }

    // add all movies to the user.
    User user = User(arr);
    for (unsigned long int movie : user.getMovies()) {
        dataManager->addMovie(user.getId(), movie);
    }

    return "";
}