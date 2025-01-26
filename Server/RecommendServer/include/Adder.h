#ifndef ADDER
#define ADDER
#include "Icommand.h"
#include "IDataManager.h"
#include "User.h"
#include <stdexcept>
using namespace std;

class Adder : public Icommand {
    
    private:
        IDataManager* dataManager;

    public:
        Adder(IDataManager* dataManager);
        string execute(vector<unsigned long int> arr) override;
        string getInstructions() override;
};

#endif