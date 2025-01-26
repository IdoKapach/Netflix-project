#include "Getter.h"

Getter::Getter(IDataManager& dataManager): dataManager(dataManager) {
    recommender = new Recommender(dataManager);
}

Getter::~Getter() {
    delete recommender;
}

string Getter::execute(std::vector<unsigned long int> arr) {
    // in case num of arguments is different from 2
    if (arr.size() != 2) {
        return "400 Bad Request\n";
    }
    // if the user doesn't exist
    if (!dataManager.hasUser(arr[0])) {
        return "404 Not Found\n";
    }
    // in case the input is valid
    return "200 Ok\n\n" + recommender -> execute(arr);
}

string Getter::getInstructions() {
    return "GET,arguments: [userid] [movieid]";
}