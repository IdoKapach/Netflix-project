#include "Poster.h"

Poster::Poster(IDataManager* dataManager) {
    this->dataManager = dataManager;
    this->adder = new Adder(dataManager);
}

Poster::~Poster() {
    delete adder;
}

string Poster::execute(vector<unsigned long int> arr) {
    if (arr.size() < 2) {
        return "400 Bad Request\n";
    }

    if (dataManager->hasUser(arr[0])) {
        return "404 Not Found\n";
    }

    // delegate the task to the nested Adder object.
    adder->execute(arr);
    return "201 Created\n";
}

string Poster::getInstructions() {
    return "POST,arguments: [userid] [movieid1] [movieid2] ...";
}