#ifndef THREADPOOLTCPSERVER
#define THREADPOOLTCPSERVER
#include "TCPServer.h"
#include "App.h"
#include "SocketInput.h"
#include "SocketOutput.h"
#include <thread>
#include <mutex>
#include <condition_variable>
#include <queue>


class ThreadPoolTCPServer : public TCPServer {

    private:
        map<string, Icommand*> commands;
        // a mutex for the threads (for dataManager)
        mutex mtx;
        queue<int> clientsQueue;
        vector<thread> workers;
        mutex queueMutex;
        condition_variable condition;

    public:
        ThreadPoolTCPServer(int port, int maxClients, map<string, Icommand*> commands);
        void addClient(int clientSocket) override;
        void handleClient(int clientSocket);
        void workerFunction();
};

#endif