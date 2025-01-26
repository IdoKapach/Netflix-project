#ifndef DELETER_H
#define DELETER_H

#include <string>
#include "Icommand.h"
#include "IDataManager.h"
#include "FileDataManager.h"

class Deleter : public Icommand {
    public:
        Deleter(IDataManager* dm);
        std::string execute(std::vector<unsigned long int> arr) override;
        std::string getInstructions() override;
    private:
        IDataManager* dm;
};

#endif // DELETER_H