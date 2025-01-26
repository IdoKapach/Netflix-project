#ifndef TCPSERVER
#define TCPSERVER
#include <arpa/inet.h>
#include <unistd.h>
#include <stdlib.h>
using namespace std;

class TCPServer {

    protected:

        int port;
        int maxClients;
    
    public:
        TCPServer(int port, int maxClients);
        void runServer();
        virtual void addClient(int clientSocket) = 0;
};

#endif