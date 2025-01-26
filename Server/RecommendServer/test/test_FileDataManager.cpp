#include "gtest/gtest.h"
#include "FileDataManager.h"
#include "User.h"
#include <limits>

class test_FileDataManager : public ::testing::Test {
protected:
    FileDataManager* fdm;

    void SetUp() override {
        // Initialize FileDataManager with a test file or mock data
        std::string dbDirectory = "./test_data";
        std::string delimiter = ",";
        // setup the test data directory
        std::filesystem::remove_all(dbDirectory);
        std::filesystem::create_directory(dbDirectory);
        // create the test user files
        // user with ID 1 has 3 movies
        std::ofstream file1(dbDirectory + "/1.txt");
        file1 << "100,101,102";
        file1.close();
        // user with ID 2 has 1 movie
        std::ofstream file2(dbDirectory + "/2.txt");
        file2 << "200";
        file2.close();
        // user with ID 3 has no movies
        std::ofstream file3(dbDirectory + "/3.txt");
        file3.close();
        fdm = new FileDataManager(dbDirectory, delimiter);
    }

    void TearDown() override {
        delete fdm;
        // std::filesystem::remove_all("./test_data");
    }
};

TEST_F(test_FileDataManager, AddMovieOldUser) {
    unsigned long userId = 1;
    unsigned long movieId = 200;
    int result = fdm->addMovie(userId, movieId);
    EXPECT_EQ(result, 1);
}

TEST_F(test_FileDataManager, AddMovieNewUser) {
    unsigned long userId = 999; // Assuming this user does not exist
    unsigned long movieId = 101;
    int result = fdm->addMovie(userId, movieId);
    EXPECT_EQ(result, 1);
}

TEST_F(test_FileDataManager, GetNextUserOrderCheck) {
    // assuming the first user has ID == 1
    unsigned long userId = 1;
    unsigned long movieId = 200;
    fdm->addMovie(userId, movieId);
    fdm->reset();
    User user = fdm->getNextUser();
    EXPECT_EQ(user.getId(), 1);
    EXPECT_EQ(user.getMovies().back(), movieId); // Check if the movie was added
}

TEST_F(test_FileDataManager, HasMoreUsers) {
    bool result = fdm->hasMoreUsers();
    EXPECT_EQ(result, true); // Assuming there are more users
}

TEST_F(test_FileDataManager, HasNoMoreUsers) {
    // Assuming there are only a few users, call hasMoreUsers multiple times
    fdm->getNextUser(); // First user
    fdm->getNextUser(); // Second user
    fdm->getNextUser(); // Third user
    bool result = fdm->hasMoreUsers(); // No more users
    EXPECT_EQ(result, false);
}

TEST_F(test_FileDataManager, GetNextUserSuccess) {
    User user = fdm->getNextUser();
    EXPECT_EQ(user.getId(), 1); // Assuming the first user has ID 1
    EXPECT_EQ(user.getMovies().size(), 3); // Assuming the user has 3 movies
}

TEST_F(test_FileDataManager, GetNextUserNoMoreUsers) {
    // Assuming there are only a 3 users, call getNextUser 4 times
    fdm->getNextUser(); // First user
    fdm->getNextUser(); // Second user
    fdm->getNextUser(); // Third user
    EXPECT_THROW(fdm->getNextUser(), out_of_range); // No more users
}

TEST_F(test_FileDataManager, Reset) {
    fdm->getNextUser(); // First user
    fdm->getNextUser(); // Second user
    fdm->reset();
    User user = fdm->getNextUser();
    EXPECT_EQ(user.getId(), 1); // First user
}

TEST_F(test_FileDataManager, ResetAfterNoMoreUsers) {
    // Assuming there are only a 3 users, call getNextUser 4 times
    fdm->getNextUser(); // First user
    fdm->getNextUser(); // Second user
    fdm->getNextUser(); // Third user
    EXPECT_THROW(fdm->getNextUser(), out_of_range); // No more users
    fdm->reset();
    User user = fdm->getNextUser();
    EXPECT_EQ(user.getId(), 1); // First user
}

TEST_F(test_FileDataManager, maxUserId) {
    unsigned long maxid = std::numeric_limits<unsigned long>::max();
    bool success = fdm->addMovie(maxid, 53453);
    EXPECT_EQ(success, 1);
}

TEST_F(test_FileDataManager, maxMovieId) {
    unsigned long maxid = std::numeric_limits<unsigned long>::max();
    int result = fdm->addMovie(1, maxid);
    EXPECT_EQ(result, 1);
    User user = fdm->getNextUser();
    EXPECT_EQ(user.getMovies().back(), maxid);
}

TEST_F(test_FileDataManager, deleteMovie) {
    unsigned long userId = 1;
    unsigned long movieId = 101;
    int result = fdm->deleteMovie(userId, movieId);
    EXPECT_EQ(result, 1);
    User user = fdm->getNextUser();
    EXPECT_EQ(user.getMovies().size(), 2);
    EXPECT_EQ(user.getMovies().back(), 102);
    EXPECT_EQ(user.getMovies().front(), 100);
}