#ifndef GETTER_H
#define GETTER_H

#include "Recommender.h"
#include "Icommand.h"
#include "IDataManager.h"
#include <string>

using namespace std;
// class that implements get method, "decorates" recommender's functionality
class Getter : public Icommand {
private:
    Recommender* recommender;
    IDataManager& dataManager;
public:
    Getter(IDataManager& dataManager);
    ~Getter();
    /*
     Overrides execute function of Icommand.
     the function gets vetcor of unsigned long int,
     so that the first argument is a user id and the second is movie id.
     the function returns string of recommended id-movies.
     The function calls to recommender.execute.
     the func returns "404" in case the user doesn't found, and "400" in case the num of arguments 
     is different from 2.
    */
    string execute(std::vector<unsigned long int> arr) override;
    /*
     Overrides getInstructions function of Icommand.
     returns the get signature string.
    */
    virtual std::string getInstructions() override;

};

#endif