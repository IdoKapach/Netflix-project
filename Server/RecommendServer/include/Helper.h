#ifndef HELPER
#define HELPER
#include "Icommand.h"
#include <stdexcept>
using namespace std;

class Helper : public Icommand {

    private:
        vector<Icommand*> commands;
    
    public:
        Helper(vector<Icommand*> commands);
        string execute(vector<unsigned long int> arr) override;
        string getInstructions() override;
};

#endif