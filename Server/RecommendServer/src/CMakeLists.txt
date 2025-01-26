# list all source files in the directory
set(
    SRC_FILES
    User.cpp
    TerminalInput.cpp
    TerminalOutput.cpp
    FileDataManager.cpp
    Helper.cpp
    Recommender.cpp
    RecAlgPart1.cpp
    Adder.cpp
    App.cpp
    main.cpp
    TCPServer.cpp
    ThreadedTCPServer.cpp
    Iinput.cpp
    SocketInput.cpp
    SocketOutput.cpp
    Poster.cpp
    Deleter.cpp
    Patcher.cpp
    Getter.cpp
    ThreadPoolTCPServer.cpp
    # add more source files here when creating new classes
)

# Create a library from all the source files in the directory
add_library(targil2_lib ${SRC_FILES})

# Create an executable from main.cpp
add_executable(targil2 main.cpp)

# set the output directory of the executable
set_target_properties(targil2 PROPERTIES RUNTIME_OUTPUT_DIRECTORY "${CMAKE_BINARY_DIR}/")

# Link the executable with the library
target_link_libraries(targil2 targil2_lib)