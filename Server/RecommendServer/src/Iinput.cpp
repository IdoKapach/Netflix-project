#include <Iinput.h>


tuple<string, vector<unsigned long int>> Iinput::tupleInput(string input) {
    // create a stream of words from input. 
    istringstream stream(input);

    // set commandType to the first word from input.
    string commandType;
    stream >> commandType;

    // place all args in the args vector.
    string arg;
    vector<unsigned long int> args;
    while (stream >> arg) {

        // throw exeption if number is negative.
        if (arg[0] == '-') {
            throw invalid_argument("Negative args not allowed");
        }

        // add argument to the vector.
        args.push_back(stoul(arg));
    }
    return tuple<string, vector<unsigned long int>>(commandType, args);
}