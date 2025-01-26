#include "TerminalInput.h"

// method gets input from user, puts first word in commandType and the rest in args vector.
tuple<string, vector<unsigned long int>> TerminalInput::nextCommand() {
    string input;

    // get input from user.
    getline(cin, input);
    
    // parse to the correct tuple and return it.
    return tupleInput(input);
}