#ifndef TERMINALINPUT
#define TERMINALINPUT
#include "Iinput.h"
#include <iostream>
#include <sstream>

class TerminalInput : public Iinput {
    
    public:
        tuple<string, vector<unsigned long int>> nextCommand() override;
};

#endif