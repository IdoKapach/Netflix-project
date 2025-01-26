#include <gtest/gtest.h>
#include "Poster.h"
#include "FileDataManager.h"

class test_Poster : public ::testing::Test {
protected:
    IDataManager* fdm;
    Poster* poster;

    void SetUp() override {
        fdm = new FileDataManager("./test_poster", ",");
        poster = new Poster(fdm);
        // add some movies for testing
        fdm->addMovie(100, 1);
        fdm->addMovie(100, 2);
        fdm->addMovie(100, 3);
        fdm->addMovie(200, 7);
    }

    void TearDown() override {
        filesystem::remove_all("./test_poster");
        delete fdm;
    }
};

// tests the class when used correctly. (user does not exist)
TEST_F(test_Poster, newUser) {
    vector<unsigned long int> args = {300, 2, 4, 5};
    string response = poster->execute(args);
    EXPECT_EQ(response, "201 Created\n");
}

// tests the class when used incorrectly. (user exists)
TEST_F(test_Poster, existingUser) {
    vector<unsigned long int> args = {100, 2, 4, 5};
    string response = poster->execute(args);
    EXPECT_EQ(response, "404 Not Found\n");
}

// the input has less than 2 arguments.
TEST_F(test_Poster, notEnoughArguments) {
    vector<unsigned long int> args = {100};
    string response = poster->execute(args);
    EXPECT_EQ(response, "400 Bad Request\n");
}