#include "TCPServer.h"
#include "ThreadedTCPServer.h"
#include "Icommand.h"
#include "FileDataManager.h"
#include "IDataManager.h"
#include "Adder.h"
#include "Recommender.h"
#include "Helper.h"
#include <gtest/gtest.h>
#include <thread>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <iostream>
 
class test_TCPServer : public ::testing::Test {
protected:
    TCPServer* server;
    std::thread serverThread;
 
    void SetUp() override {
        int port = 8080;
        int maxClients = 3;

        // setup commands
        map<string, Icommand*> commands;
        IDataManager* data = new FileDataManager("./user_data", ",");
        Icommand* add = new Adder(data);
        commands["add"] = add;
        Icommand* recommend = new Recommender(*data);
        commands["recommend"] = recommend;

        // extract commands from map (to pass to helper)
        vector<Icommand*> commandVec;
        for (map <string, Icommand*>::const_iterator it = commands.begin(); it != commands.end(); ++it) {
            commandVec.push_back(it->second);
        }

        Icommand* help = new Helper(commandVec);
        commands["help"] = help;

        // create and run server in a separate thread
        TCPServer* server = new ThreadedTCPServer(port, maxClients, commands);
        serverThread = thread(&TCPServer::runServer, server);

        // Give the server some time to start
        this_thread::sleep_for(std::chrono::seconds(1));
    }
    
        void TearDown() override {
        
        // detach server from our main thread
        serverThread.detach();

        // delete server
        delete server;
    }
 
    int createClientSocket() {
        int sock = socket(AF_INET, SOCK_STREAM, 0);
        if (sock < 0) {
            perror("Socket creation error");
            return -1;
        }
 
        struct sockaddr_in serv_addr;
        serv_addr.sin_family = AF_INET;
        serv_addr.sin_port = htons(8080);
 
        if (inet_pton(AF_INET, "127.0.0.1", &serv_addr.sin_addr) <= 0) {
            perror("Invalid address/ Address not supported");
            return -1;
        }
 
        if (connect(sock, (struct sockaddr*)&serv_addr, sizeof(serv_addr)) < 0) {
            perror("Connection Failed");
            return -1;
        }
 
        return sock;
    }
};
 
TEST_F(test_TCPServer, BadArgs) {

    // create a client socket
    int clientSock = createClientSocket();
    ASSERT_NE(clientSock, -1);

    // send a bad command to the server
    const char* message = "help";
    send(clientSock, message, strlen(message), 0);
 
    // get response from server
    char buffer[1024] = {0};
    int valread = read(clientSock, buffer, 1024);
    string response = buffer;

    // check output
    EXPECT_EQ(response, "add [userid] [movieid1] [movieid2] ...\nrecommend[userid] [movieid]\nhelp\n");
    close(clientSock);
}
