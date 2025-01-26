#ifndef TERMINALOUTPUT
#define TERMINALOUTPUT
#include "Ioutput.h"
#include <iostream>

class TerminalOutput : public Ioutput {

    public:
        void printAnswer(string functionOutput, bool endLine = false) override;
};
#endif