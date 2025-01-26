#include <gtest/gtest.h>
#include "User.h"

class test_User : public ::testing::Test {
protected:
    unsigned long int id;
    vector<unsigned long int> movies;
    vector<unsigned long int> input;

};

// tests for the normal constructor.
TEST_F(test_User, 2ArgsConstructorNormalArgs) {
    movies = {100, 50, 20};
    id = 105;
    User test = User(id, movies);
    EXPECT_EQ(test.getId(), 105);
    EXPECT_EQ(test.getMovies()[0], 100);
    EXPECT_EQ(test.getMovies()[1], 50);
    EXPECT_EQ(test.getMovies()[2], 20);
}

TEST_F(test_User, 2ArgsConstructorEmptyArgs) {
    User test = User(id, movies);
    EXPECT_EQ(test.getId(), id);
    EXPECT_EQ(test.getMovies().size(), 0);
}

TEST_F(test_User, 2ArgsConstructorEditInput) {
    movies = {100};
    id = 105;
    User test = User(id, movies);
    EXPECT_EQ(test.getId(), 105);
    EXPECT_EQ(test.getMovies()[0], 100);
    EXPECT_EQ(test.getMovies().size(), 1);
    movies.pop_back();
    EXPECT_EQ(test.getId(), 105);
    EXPECT_EQ(test.getMovies()[0], 100);
    EXPECT_EQ(test.getMovies().size(), 1);
}

// tests for the constructor that get a vector of strings as input.
TEST_F(test_User, 1ArgConstructorNormalArgs) {
    input = {105, 100, 50, 20};
    User test = User(input);
    EXPECT_EQ(test.getId(), 105);
    EXPECT_EQ(test.getMovies()[0], 100);
    EXPECT_EQ(test.getMovies()[1], 50);
    EXPECT_EQ(test.getMovies()[2], 20);
    EXPECT_EQ(test.getMovies().size(), 3);
}

TEST_F(test_User, 1ArgConstructorEmptyArgs) {
    EXPECT_ANY_THROW({
        User test = User(input);
    });
}

TEST_F(test_User, 1ArgConstructorEditInput) {
    input = {105, 100};
    User test = User(input);
    EXPECT_EQ(test.getId(), 105);
    EXPECT_EQ(test.getMovies()[0], 100);
    EXPECT_EQ(test.getMovies().size(), 1);
    input.pop_back();
    EXPECT_EQ(test.getId(), 105);
    EXPECT_EQ(test.getMovies()[0], 100);
    EXPECT_EQ(test.getMovies().size(), 1);
}