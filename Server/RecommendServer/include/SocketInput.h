#ifndef SICKETINPUT
#define SOCKETINPUT
#include "Iinput.h"
#include <arpa/inet.h>

#define BUFFER_SIZE 4096

class SocketInput : public Iinput {
    private:
        int clientSocket;
    public:
        SocketInput(int clientSocket);
        tuple<string, vector<unsigned long int>> nextCommand() override;
};

#endif