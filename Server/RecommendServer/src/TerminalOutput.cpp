#include "TerminalOutput.h"

// function prints the input string and endl if bool is true(default is false).
void TerminalOutput::printAnswer(string functionOutput, bool endLine) {
    cout << functionOutput;
    if (endLine) {
        cout << endl;
    }
}