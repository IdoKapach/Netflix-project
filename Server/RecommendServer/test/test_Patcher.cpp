#include <gtest/gtest.h>
#include "Patcher.h"
#include "FileDataManager.h"

class test_Patcher : public ::testing::Test {
protected:
    IDataManager* fdm;
    Patcher* patcher;

    void SetUp() override {
        fdm = new FileDataManager("./test_patcher", ",");
        patcher = new Patcher(fdm);
        // add some movies for testing
        fdm->addMovie(100, 1);
        fdm->addMovie(100, 2);
        fdm->addMovie(100, 3);
        fdm->addMovie(200, 7);
    }

    void TearDown() override {
        filesystem::remove_all("./test_patcher");
        delete fdm;
    }
};

// tests the class when used correctly. (user exists)
TEST_F(test_Patcher, newUser) {
    vector<unsigned long int> args = {100, 2, 4, 5};
    string response = patcher->execute(args);
    EXPECT_EQ(response, "204 No Content\n");
}

// tests the class when used incorrectly. (user does not exists)
TEST_F(test_Patcher, existingUser) {
    vector<unsigned long int> args = {300, 2, 4, 5};
    string response = patcher->execute(args);
    EXPECT_EQ(response, "404 Not Found\n");
}

// the input has less than 2 arguments.
TEST_F(test_Patcher, notEnoughArguments) {
    vector<unsigned long int> args = {100};
    string response = patcher->execute(args);
    EXPECT_EQ(response, "400 Bad Request\n");
}