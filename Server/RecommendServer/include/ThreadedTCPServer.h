#ifndef THREADEDTCPSERVER
#define THREADEDTCPSERVER
#include "TCPServer.h"
#include <App.h>
#include <thread>
#include <mutex>
#include "SocketInput.h"
#include "SocketOutput.h"

class ThreadedTCPServer : public TCPServer {

    private:
        map<string, Icommand*> commands;
        // a mutex for the threads (for dataManager)
        mutex mtx;

    public:
        ThreadedTCPServer(int port, int maxClients, map<string, Icommand*> commands);
        void addClient(int clientSocket) override;
        void handleClient(int clientSocket);
};

#endif