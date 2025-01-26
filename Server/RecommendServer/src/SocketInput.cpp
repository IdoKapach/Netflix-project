#include "SocketInput.h"

// a constructor for a socket input
SocketInput::SocketInput(int clientSocket) : clientSocket(clientSocket) {}

tuple<string, vector<unsigned long int>> SocketInput::nextCommand() {

    // get input from the socket
    char buffer[BUFFER_SIZE] = "";
    int expected_data_len = sizeof(buffer);
    int read_bytes = recv(clientSocket, buffer, expected_data_len, 0);

    // if connection closed throw the appropriate error
    if (read_bytes <= 0) {
        throw runtime_error("Client Disconnected");
    }

    // if input is good parse to the correct tuple and return it
    else {
        string input = buffer;
        return tupleInput(input);
    }
}