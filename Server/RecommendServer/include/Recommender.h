# ifndef RECOMMENDER_H
# define RECOMMENDER_H

#include "Icommand.h"
#include "IDataManager.h"
#include <vector>
#include <map>
#include "RecAlgPart1.h"
#include <iostream>

/*
this class is in charge of implementing the "recommend" command wich is part of the app interface
*/
class Recommender : public Icommand {
private:
    IDataManager& dataManager;
public:
    /*
     Gets IDataManager& for dataManager field
    */
    Recommender(IDataManager& dataManager);
    /*
     Overrides execute function of Icommand.
     the function gets vetcor of unsigned long int,
     so that the first argument is a user id and the second is movie id.
     the function returns string of reccomended id-movies.
     The function calls to reccomendAlgorithem.
    */
    std::string execute(std::vector<unsigned long int> arr) override;
    /*
     Overrides getInstructions function of Icommand.
     returns the recommend signature string.
    */
    virtual std::string getInstructions() override;

};

# endif