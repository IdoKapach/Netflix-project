#include "Iinput.h"
#include "TerminalInput.h"
#include <gtest/gtest.h>

class test_TerminalInput : public ::testing::Test {
protected:
    Iinput* test;

    void SetUp() override {
        test = new TerminalInput();
    }

    void TearDown() override {
        delete test;
    }
};

// tests for the nextCommand method.
TEST_F(test_TerminalInput, NormalArgs) {

    // get input string created into cin.
    string input = "add 100 10 20 30\n";
    istringstream input_stream(input);
    cin.rdbuf(input_stream.rdbuf());

    // test accuracy of nextCommand.
    tuple<string, vector<unsigned long int>> command = test->nextCommand();
    EXPECT_EQ(get<0>(command), "add");
    EXPECT_EQ(get<1>(command)[0], 100);
    EXPECT_EQ(get<1>(command)[1], 10);
    EXPECT_EQ(get<1>(command)[2], 20);
    EXPECT_EQ(get<1>(command)[3], 30);
    EXPECT_EQ(get<1>(command).size(), 4);
}

TEST_F(test_TerminalInput, EmptyArgs) {

    // get input string created into cin.
    string input = "\n";
    istringstream input_stream(input);
    cin.rdbuf(input_stream.rdbuf());

    // test accuracy of nextCommand.
    tuple<string, vector<unsigned long int>> command = test->nextCommand();
    EXPECT_EQ(get<1>(command).size(), 0);
}

TEST_F(test_TerminalInput, SpacebarArgs) {

    // get input string created into cin.
    string input = "    add    100  10      20     30     \n";
    istringstream input_stream(input);
    cin.rdbuf(input_stream.rdbuf());

    // test accuracy of nextCommand.
    tuple<string, vector<unsigned long int>> command = test->nextCommand();
    EXPECT_EQ(get<0>(command), "add");
    EXPECT_EQ(get<1>(command)[0], 100);
    EXPECT_EQ(get<1>(command)[1], 10);
    EXPECT_EQ(get<1>(command)[2], 20);
    EXPECT_EQ(get<1>(command)[3], 30);
    EXPECT_EQ(get<1>(command).size(), 4);
}

TEST_F(test_TerminalInput, OnlySpacebarArgs) {

    // get input string created into cin.
    string input = "      \n";
    istringstream input_stream(input);
    cin.rdbuf(input_stream.rdbuf());

    // test accuracy of nextCommand.
    tuple<string, vector<unsigned long int>> command = test->nextCommand();
    EXPECT_EQ(get<0>(command), "");
    EXPECT_EQ(get<1>(command).size(), 0);
}

TEST_F(test_TerminalInput, NegativeNumbers) {

    // get input string created into cin.
    string input = "    add    100  -10      20     -30     \n";
    istringstream input_stream(input);
    cin.rdbuf(input_stream.rdbuf());

    // test accuracy of nextCommand.
    EXPECT_ANY_THROW({
       test->nextCommand();
    });
}

TEST_F(test_TerminalInput, StringsInArgs) {

    // get input string created into cin.
    string input = "    add    100  hi      20     oleole     \n";
    istringstream input_stream(input);
    cin.rdbuf(input_stream.rdbuf());

    // test accuracy of nextCommand.
    EXPECT_ANY_THROW({
        test->nextCommand();
    });
}