#include "SocketOutput.h"

// a constructor for a socket output
SocketOutput::SocketOutput(int clientSocket) : clientSocket(clientSocket) {}

// function prints the input string and endl if bool is true(default is false).
void SocketOutput::printAnswer(string functionOutput, bool endLine) {

    // if output is emply add a spacebar so that client will not continue waiting for a response
    if (functionOutput == "") {
        functionOutput = " ";
    }

    // add \n if bool endLine is true
    if (endLine) {
        functionOutput += "\n";
    }

    // change the string into a char array with the buffer size, and set last char to \0
    char buffer[BUFFER_SIZE];
    strncpy(buffer, functionOutput.c_str(), sizeof(buffer) - 1);
    buffer[sizeof(buffer) - 1] = '\0';

    // send massage
    int sent_bytes = send(clientSocket, buffer, functionOutput.size(), 0);

    // if sending failed throw error massage
    if (sent_bytes < 0) {
        throw runtime_error("failed to output");
    }
}

void SocketOutput::outputError() {

    // send error massage
    char error[] = "400 Bad Request\n";
    int error_len = sizeof(error);
    int sent_bytes = send(clientSocket, error, error_len, 0);
    

    // if sending failed throw error massage
    if (sent_bytes < 0) {
        throw runtime_error("failed to output");
    }

}