#include "TCPServer.h"

// a constructor for a TCP server
TCPServer::TCPServer(int port, int maxClients) : port(port), maxClients(maxClients) {}

void TCPServer::runServer() {

    // create the sserver socket
    int serverSocket = socket(AF_INET, SOCK_STREAM, 0);
    if (serverSocket < 0) {
        exit(1);
    }

    // create struct to configure socket
    struct sockaddr_in socketConfig;

    // configure socket port, set the socket to ipv4 addressing, and to accept from any ip's
    socketConfig.sin_port = htons(port);
    socketConfig.sin_family = AF_INET;
    socketConfig.sin_addr.s_addr = INADDR_ANY;
    
    // bind socket
    if (bind(serverSocket, (struct sockaddr *) &socketConfig, sizeof(socketConfig)) < 0) {
        exit(1);
    }

    // set socket to listen with a set maximum clients in the backlog
    if (listen(serverSocket, maxClients) < 0) {
        exit(1);
    }

    // create variables to store client socket configuration
    struct sockaddr_in clientConfig;
    unsigned int addr_len = sizeof(clientConfig);

    // run server as long as bool running is true
    while (true) {

        // accept a client
        int clientSocket = accept(serverSocket,  (struct sockaddr *) &clientConfig,  &addr_len);
        if (clientSocket < 0) {
            exit(1);
        }

        // add client to deal with
        addClient(clientSocket);
    }

    // close server socket
    close(serverSocket);
}