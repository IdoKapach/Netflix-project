#include <gtest/gtest.h>
#include "Icommand.h"
#include "Helper.h"
#include "IDataManager.h"
#include "FileDataManager.h"
#include "Adder.h"

class test_Helper : public ::testing::Test {
protected:
    Icommand *helper, *adder;
    IDataManager* data;


    void SetUp() override {
        data = new FileDataManager("./test_helper", ",");
        adder = new Adder(data);
    }

    void TearDown() override {
        delete data;
        delete adder;
        delete helper;
    }
};

// tests for execute function in class helper.
TEST_F(test_Helper, WithoutArgs) {

    // setup helper.
    vector<Icommand*> commands = {};
    helper = new Helper(commands);

    // test results.
    vector<unsigned long int> args;
    EXPECT_EQ(helper->execute(args), "help\n");
}

TEST_F(test_Helper, WithArgs) {

    // setup helper.
    vector<Icommand*> commands = {};
    helper = new Helper(commands);

    // test results.
    vector<unsigned long int> args = {10, 20, 30};
    EXPECT_ANY_THROW({
        helper->execute(args);
    });
}

TEST_F(test_Helper, WithCommands) {

    // setup helper.
    vector<Icommand*> commands = {adder};
    helper = new Helper(commands);

    // test results.
    vector<unsigned long int> args;
    EXPECT_EQ(helper->execute(args), "add [userid] [movieid1] [movieid2] ...\nhelp\n");
}


TEST_F(test_Helper, WithDupCommands) {

    // setup helper.
    vector<Icommand*> commands = {};
    commands.push_back(adder);
    commands.push_back(adder);
    helper = new Helper(commands);

    // test results.
    vector<unsigned long int> args;
    EXPECT_EQ(helper->execute(args), "add [userid] [movieid1] [movieid2] ...\nadd [userid] [movieid1] [movieid2] ...\nhelp\n");
}

TEST_F(test_Helper, GetInstructionsTest) {

    // setup helper.
    vector<Icommand*> commands = {};
    helper = new Helper(commands);

    EXPECT_EQ(helper->getInstructions(), "help");
}