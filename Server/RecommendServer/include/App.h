#ifndef APP
#define APP
#include "Icommand.h"
#include "Iinput.h"
#include "Ioutput.h"
#include <map>
#include <tuple>
#include <iostream>
#include <mutex>
using namespace std;

class App {
    
    private:
        Iinput* input;
        Ioutput* output;
        map<string, Icommand*> commands;
        mutex& mtx;

    public:
        App(Iinput* input, Ioutput* output, map<string, Icommand*> commands, mutex& mtx);
        void run();
};

#endif