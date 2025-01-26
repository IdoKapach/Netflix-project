#include "Patcher.h"

Patcher::Patcher(IDataManager* dm) {
    this->dm = dm;
    this->adder = new Adder(dm);
}

Patcher::~Patcher() {
    delete adder;
}

string Patcher::execute(vector<unsigned long int> input) {
    // If the vector is empty or has only one element, return 400 Bad Request
    if (input.size() < 2) {
        return "400 Bad Request\n";
    }

    // If the user does not exist, return 404 Not Found
    if (!dm->hasUser(input[0])) {
        return "404 Not Found\n";
    }

    // execute the Adder object (add the movies to the user's list)
    adder->execute(input);
    // return 204 No Content
    return "204 No Content\n";
}

string Patcher::getInstructions() {
    return "PATCH,arguments: [userid] [movieid1] [movieid2] ...";
}
