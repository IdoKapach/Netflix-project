#ifndef IOUTPUT
#define IOUTPUT
#include <string>
using namespace std;

class Ioutput {

    public:
        virtual void printAnswer(string functionOutput, bool endLine = false) = 0;
        virtual void outputError() {};
};
#endif