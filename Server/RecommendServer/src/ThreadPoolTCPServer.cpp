#include "ThreadPoolTCPServer.h"

// a constructor for the threded TCP server
ThreadPoolTCPServer::ThreadPoolTCPServer(int port, int maxClients, map<string, Icommand*> commands)
: TCPServer(port, maxClients), commands(commands) {
    for (int i = 0; i < maxClients; i++) {
        workers.emplace_back(&ThreadPoolTCPServer::workerFunction, this);
    }
}

void ThreadPoolTCPServer::workerFunction() {
    int clientSocket;
    while (true) {
        {
            // wait until clientsQueue is not empty
            unique_lock<mutex> lock(queueMutex);
            condition.wait(lock, [this]() { return !clientsQueue.empty(); });

            // get the client's socket and remove him from the queue
            clientSocket = clientsQueue.front();
            clientsQueue.pop(); 

            // queueMutex is unlocked here
        }

        // run client
        handleClient(clientSocket);
    }
}

void ThreadPoolTCPServer::handleClient(int clientSocket) {

    // create the app and run it
    Iinput* input = new SocketInput(clientSocket);
    Ioutput* output = new SocketOutput(clientSocket);
    App* app = new App(input, output, commands, mtx);
    app->run();

    // when app finished delete from heap and close client's socket
    delete app;
    delete input;
    delete output;
    close(clientSocket);
}


// function adds a client to work on- creates a thread for him and detach it to run independantly
void ThreadPoolTCPServer::addClient(int clientSocket) {

    {
        // lock queueMutex, and push socket into clientsQueue
        lock_guard<mutex> lock(queueMutex);
        clientsQueue.push(clientSocket);

        // queueMutex is unlocked here
    }

    // notify one of the waiting threads
    condition.notify_one();
}

