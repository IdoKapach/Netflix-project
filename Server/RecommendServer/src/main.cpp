#include <map>
#include <string>
#include "Icommand.h"
#include "Helper.h"
#include "Poster.h"
#include "Getter.h"
#include "Deleter.h"
#include "Patcher.h"
#include "FileDataManager.h"
#include "TCPServer.h"
#include "ThreadPoolTCPServer.h"
using namespace std;

#define MAX_CLIENTS 5

int main(int argc, char *argv[]) {

    // setup commands.
    map<string, Icommand*> commands;
    IDataManager* data = new FileDataManager("./user_data", ",");
    Icommand* GET = new Getter(*data);
    commands["GET"] = GET;
    Icommand* POST = new Poster(data);
    commands["POST"] = POST;
    Icommand* PATCH = new Patcher(data);
    commands["PATCH"] = PATCH;
    Icommand* DELETE = new Deleter(data);
    commands["DELETE"] = DELETE;

    // extract commands from map. (to pass to helper)
    vector<Icommand*> commandVec;
    for (map <string, Icommand*>::const_iterator it = commands.begin(); it != commands.end(); ++it) {
        commandVec.push_back(it->second);
    }

    Icommand* help = new Helper(commandVec);
    commands["help"] = help;

    // get port from command line arguments.
    int port;
    // check if port was given.
    if (argc < 2) {
        // if not, use default port.
        cout << "no port given, using default port 8080" << endl;
        port = 8080;
    } else {
        // if port was given, use it.
        port = stoi(argv[1]);
        cout << "using port " << port << endl;
    }

    // create server and run it.
    TCPServer* server = new ThreadPoolTCPServer(port, MAX_CLIENTS, commands);
    server->runServer();
    delete server;
    delete help;
    delete DELETE;
    delete PATCH;
    delete POST;
    delete GET;
    delete data;
}