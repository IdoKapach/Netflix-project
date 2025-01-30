#ifndef ICOMMAND_H
#define ICOMMAND_H

#include <string>
#include <vector>

class Icommand {
public:
    virtual std::string execute(std::vector<unsigned long int> arr) = 0;
    virtual std::string getInstructions() = 0;
};

#endif // ICOMMAND_H