 #include "App.h"

 // a constructor for the app.
 App::App(Iinput* input, Ioutput* output, map<string, Icommand*> commands, mutex& mtx) : input(input), output(output), commands(commands), mtx(mtx) {}


// function to run the app.
 void App::run() {

    // continue forever.
    while (true) {

        // try and execute an inputted command.
        try {

            // get next command from input.
            tuple<string, vector<unsigned long int>> command = input->nextCommand();

            // execute and print answer only if command exists.
            if (commands.find(get<0>(command)) != commands.end()) {
                string answer;
                // we made extra scope to lock the mutex only for the command execution
                // (the lock_guard will unlock the mutex when it goes out of scope & handle exceptions)
                {
                    // lock the mutex. (each command is a critical section)
                    lock_guard<mutex> lock(mtx);
                    // execute the command
                    answer = commands[get<0>(command)]->execute(get<1>(command));
                    // end of scope - lock_guard will unlock the mutex safely
                }
                output->printAnswer(answer);
            }
            else {
                throw runtime_error("400 Bad Arguments");
            }
        }
        catch (const std::runtime_error& e) {
            if (std::string(e.what()) == "Client Disconnected") {
                return;
            } else { 
                output->outputError();
            }
        }
        catch(...) {
            output->outputError();
        }
    }
 }