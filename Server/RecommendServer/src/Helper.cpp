#include "Helper.h"

// a constructor for Helper
Helper::Helper(vector<Icommand*> commands) : commands(commands) {}

// overrides getInstructions to return help's instructions.
string Helper::getInstructions() {
    return "help";
}

// override execute for "help": prints all commands available if there's no args.
string Helper::execute(vector<unsigned long int> arr) {
    if (!arr.empty()) {
        throw invalid_argument("No extra arguments allowed");
    }
    string output = "";
    for (Icommand* command : commands) {
        output.append(command->getInstructions());
        output.append("\n");
    }
    output.append(getInstructions());
    output.append("\n");
    return output;
}