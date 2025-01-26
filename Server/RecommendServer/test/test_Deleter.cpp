#include <gtest/gtest.h>
#include "Deleter.h"

class test_Deleter : public ::testing::Test {
protected:
    Deleter* deleter;
    IDataManager* dm;

    void SetUp() override {
        dm = new FileDataManager("./test_deleter", ",");
        // add some movies for testing
        dm->addMovie(100, 1);
        dm->addMovie(100, 2);
        dm->addMovie(100, 3);
        dm->addMovie(200, 7);
        deleter = new Deleter(dm);
    }

    void TearDown() override {
        delete deleter;
        delete dm;
        filesystem::remove_all("./test_deleter");
    }
};

TEST_F(test_Deleter, NormalArgsAll) {
    // execute input.
    vector<unsigned long int> args = {100, 1, 2, 3};
    string returnMsg = deleter->execute(args);
    EXPECT_EQ(returnMsg, "200 OK\n");
    EXPECT_EQ(dm->hasMovie(100, 1), false);
    EXPECT_EQ(dm->hasMovie(100, 2), false);
    EXPECT_EQ(dm->hasMovie(100, 3), false);
}

TEST_F(test_Deleter, NormalArgsSome) {
    // execute input.
    vector<unsigned long int> args = {100, 1, 2};
    string returnMsg = deleter->execute(args);
    EXPECT_EQ(returnMsg, "200 OK\n");
    EXPECT_EQ(dm->hasMovie(100, 1), false);
    EXPECT_EQ(dm->hasMovie(100, 2), false);
    EXPECT_EQ(dm->hasMovie(100, 3), true);
}

TEST_F(test_Deleter, EmptyArgs) {
    vector<unsigned long int> args = {};
    string returnMsg = deleter->execute(args);
    EXPECT_EQ(returnMsg, "400 Bad Request\n");
    EXPECT_EQ(dm->hasMovie(100, 1), true);
    EXPECT_EQ(dm->hasMovie(100, 2), true);
    EXPECT_EQ(dm->hasMovie(100, 3), true);
}

TEST_F(test_Deleter, OneArg) {
    vector<unsigned long int> args = {100};
    string returnMsg = deleter->execute(args);
    EXPECT_EQ(returnMsg, "400 Bad Request\n");
    EXPECT_EQ(dm->hasMovie(100, 1), true);
    EXPECT_EQ(dm->hasMovie(100, 2), true);
    EXPECT_EQ(dm->hasMovie(100, 3), true);
}

TEST_F(test_Deleter, NonExistingUser) {
    vector<unsigned long int> args = {300, 1, 2, 3};
    string returnMsg = deleter->execute(args);
    EXPECT_EQ(returnMsg, "404 Not Found\n");
}

TEST_F(test_Deleter, NonExistingMovie1) {
    vector<unsigned long int> args = {100, 1, 2, 3, 4};
    string returnMsg = deleter->execute(args);
    EXPECT_EQ(returnMsg, "404 Not Found\n");
    EXPECT_EQ(dm->hasMovie(100, 4), false);
    EXPECT_EQ(dm->hasMovie(100, 1), true);
    EXPECT_EQ(dm->hasMovie(100, 2), true);
    EXPECT_EQ(dm->hasMovie(100, 3), true);
}

TEST_F(test_Deleter, NonExistingMovie2) {
    vector<unsigned long int> args = {100, 4};
    string returnMsg = deleter->execute(args);
    EXPECT_EQ(returnMsg, "404 Not Found\n");
    EXPECT_EQ(dm->hasMovie(100, 4), false);
    EXPECT_EQ(dm->hasMovie(100, 1), true);
    EXPECT_EQ(dm->hasMovie(100, 2), true);
    EXPECT_EQ(dm->hasMovie(100, 3), true);
}