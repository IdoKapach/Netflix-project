#ifndef IINPUT
#define IINPUT
#include <string>
#include <vector>
#include <tuple>
#include <sstream>
using namespace std;

class Iinput {

    public:
        virtual tuple<string, vector<unsigned long int>> nextCommand() = 0;
        tuple<string, vector<unsigned long int>> tupleInput(string input);
};
#endif