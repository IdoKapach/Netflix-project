#ifndef SOCKETOUTPUT
#define SOCKETOUTPUT
#include "Ioutput.h"
#include <cstring>
#include <arpa/inet.h>
#include <sstream>

#define BUFFER_SIZE 4096

class SocketOutput : public Ioutput {
    private:
        int clientSocket;

    public:
        SocketOutput(int clientSocket);
        void printAnswer(string functionOutput, bool endLine = false) override;
        void outputError() override;
};
#endif