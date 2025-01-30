# test/CMakeLists.txt

include(FetchContent)
FetchContent_Declare(
  googletest
  URL https://github.com/google/googletest/archive/03597a01ee50ed33e9dfd640b249b4be3799d395.zip
)
# For Windows: Prevent overriding the parent project's compiler/linker settings
set(gtest_force_shared_crt ON CACHE BOOL "" FORCE)
FetchContent_MakeAvailable(googletest)

# list all test files in the directory
set(
    TEST_FILES
    test_main.cpp
    test_User.cpp
    test_TerminalInput.cpp
    test_TerminalOutput.cpp
    test_FileDataManager.cpp
    test_Helper.cpp
    test_ThreadedTCPServer.cpp
    test_RecAlgPart1.cpp
    test_Adder.cpp
    test_Poster.cpp
    test_Deleter.cpp
    test_Patcher.cpp
    test_Getter.cpp
    # add more test files here when creating new test_classes
)

# Add the test executable
add_executable(MyTests ${TEST_FILES})

# Link the test executable with GoogleTest and the project library
target_link_libraries(MyTests gtest_main targil2_lib)

# Add test
add_test(NAME MyTests COMMAND MyTests)