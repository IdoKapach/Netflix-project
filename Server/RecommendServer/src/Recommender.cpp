#include "Recommender.h"

using namespace std;

Recommender::Recommender(IDataManager& dataManager) : dataManager(dataManager) {}

/*
    Overrides execute function of Icommand.
    the function gets vetcor of unsigned long int,
    so that the first argument is a user id and the second is movie id.
    the function returns string of reccomended id-movies.
    The function calls to reccomendAlgorithem.
*/
std::string Recommender::execute(std::vector<unsigned long int> arr) {
    if (arr.size() != 2) {
        return "";
    }
    unsigned long int userId = arr[0], movieId = arr[1];
    RecAlgPart1 recAlg(this->dataManager);
    this->dataManager.reset();
    return recAlg.recommendAlgorithm(userId, movieId);
}
/*
    Overrides getInstructions function of Icommand.
    returns the recommend signature string.
*/
std::string Recommender::getInstructions() {
    return "recommend[userid] [movieid]";
}

