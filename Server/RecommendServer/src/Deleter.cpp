#include "Deleter.h"

Deleter::Deleter(IDataManager* dm) {
    this->dm = dm;
}

std::string Deleter::execute(std::vector<unsigned long int> input) {
    // check if the input has at least 2 args
    if (input.size() < 2) {
        return "400 Bad Request\n";
    }

    // check if the user exists
    if (!this->dm->hasUser(input[0])) {
        return "404 Not Found\n";
    }

    // check that all movies were watched by the user
    for (size_t i = 1; i < input.size(); i++) {
        // input[0] is the user ID
        if (!this->dm->hasMovie(input[0], input[i])) {
            return "404 Not Found\n";
        }
    }

    // if all movies were watched, delete them
    for (size_t i = 1; i < input.size(); i++) {
        // input[0] is the user ID
        this->dm->deleteMovie(input[0], input[i]);
    }
    // return 200 OK
    return "200 OK\n";
}

std::string Deleter::getInstructions() {
    return "DELETE,arguments: [userid] [movieid1] [movieid2] ...";
}