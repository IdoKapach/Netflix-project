#include "ThreadedTCPServer.h"

// a constructor for the threded TCP server
ThreadedTCPServer::ThreadedTCPServer(int port, int maxClients, map<string, Icommand*> commands) 
: TCPServer(port, maxClients), commands(commands) {}

void ThreadedTCPServer::handleClient(int clientSocket) {

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
void ThreadedTCPServer::addClient(int clientSocket) {
    thread clientThread(&ThreadedTCPServer::handleClient,this , clientSocket);
    clientThread.detach();
}


