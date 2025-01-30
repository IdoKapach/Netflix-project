#include "RecAlgPart1.h"
#include "Recommender.h"
#include "User.h"
#include <gtest/gtest.h>
#include <vector>
#include <iostream>

using namespace std;

class TestManager : public IDataManager {
public:
    vector<User> users;
    unsigned long int pointer=0, numOfUsers;
    TestManager(vector<User> users) {
        this->users = users;
        this->numOfUsers = users.size();
    }
    int addMovie(unsigned long userId, unsigned long movieId) override {
        return 1;
    }
    int deleteMovie(unsigned long userId, unsigned long movieId) override {
        return 1;
    }
    bool hasMovie(unsigned long userId, unsigned long movieId) override {
        return true;
    }
    bool hasUser(unsigned long userId) override {
        return true;
    }
    User getNextUser() override {
        if (this->pointer == this->numOfUsers){
            vector<unsigned long int> v(0);
            User u(-1, v);
            return u;
        }
        this->pointer++;
        return this->users[(this->pointer)-1];
    }
    bool hasMoreUsers() override {
        return this->pointer < this->numOfUsers;
    }
    void reset() override {
        this->pointer = 0;
    }
};

// Teststing the TestManager class:
TEST(TestManagerTest, getNextUserTest) {
    User u1(1, vector<unsigned long int>{22, 33, 44}), u2(2, vector<unsigned long int>{44, 55, 33});
    vector<User> users = {u1, u2};
    TestManager manager = TestManager(users);
    EXPECT_EQ(manager.getNextUser().getId(), 1);
    EXPECT_EQ(manager.hasMoreUsers(), true);
    EXPECT_EQ(manager.getNextUser().getId(), 2);
    EXPECT_EQ(manager.hasMoreUsers(), false);
    EXPECT_EQ(manager.getNextUser().getId(), -1);
}
// Testing the auxiliary functions
TEST(enterUsersToMovieMapTest, SingleUserRecommendTest) {
    User u1(1, vector<unsigned long int>{22, 33, 44, 11}), u2(2, vector<unsigned long int>{44, 55, 33, 22}),
      u3(3, vector<unsigned long int>{33, 55, 11});
    vector<User> users = {u1, u2, u3};
    TestManager manager = TestManager(users);
    RecAlgPart1 rec(manager);
    vector<unsigned long int> userMovies;
    EXPECT_EQ(rec.enterUsersToMovieMap(1, 22, userMovies)[33].size(), 1);
    EXPECT_EQ(userMovies.size(), 4);
    manager.reset();
    EXPECT_EQ(rec.enterUsersToMovieMap(1, 11, userMovies)[33].size(), 1);
}

TEST(updateMovieMapTest, SingleUserRecommendTest) {
    User u1(1, vector<unsigned long int>{22, 33, 44, 11}), u2(2, vector<unsigned long int>{44, 55, 33, 22}),
      u3(3, vector<unsigned long int>{33, 55, 11});
    vector<User> users = {u1, u2, u3};
    TestManager manager = TestManager(users);
    RecAlgPart1 rec(manager);
    vector<unsigned long int> userMovies = {22, 33, 44, 11};
    map<unsigned long int, vector<unsigned long int*>> movieMap;
    unsigned long int a=0, b=0, c=0;
    unsigned long int *x = &a, *y = &b, *z = &c;
    movieMap[22].push_back(x);
    movieMap[22].push_back(y);
    movieMap[77].push_back(x);
    movieMap[77].push_back(z);
    movieMap[44].push_back(x);
    movieMap[44].push_back(z);
    rec.updateMovieMap(movieMap, userMovies);
    EXPECT_EQ(*movieMap[77][0], 2);
    EXPECT_EQ(*movieMap[77][1], 1);
    EXPECT_EQ(*movieMap[22][0], 2);
    EXPECT_EQ(*movieMap[22][1], 1);
}

TEST(sumMovieMapValuesTest, SingleUserRecommendTest) {
    User u1(1, vector<unsigned long int>{22, 33, 44, 11}), u2(2, vector<unsigned long int>{44, 55, 33, 22}),
      u3(3, vector<unsigned long int>{33, 55, 11});
    vector<User> users = {u1, u2, u3};
    TestManager manager = TestManager(users);
    RecAlgPart1 rec(manager);
    vector<unsigned long int> userMovies = {22, 33, 44, 11};
    map<unsigned long int, vector<unsigned long int*>> movieMap;
    unsigned long int a=5, b=2, c=3;
    unsigned long int *x = &a, *y = &b, *z = &c;
    movieMap[22].push_back(x);
    movieMap[22].push_back(y);
    movieMap[77].push_back(x);
    movieMap[77].push_back(z);
    movieMap[88].push_back(x);
    movieMap[88].push_back(z);
    movieMap[88].push_back(y);
    map<unsigned long int, unsigned long int> sumMoviesMap = rec.sumMovieMapValues(movieMap, 22, userMovies);
    EXPECT_EQ(sumMoviesMap[77], 8);
    EXPECT_EQ(sumMoviesMap[22], 0);
    EXPECT_EQ(sumMoviesMap[88], 10);
}

TEST(sortSumMovieMapTest, SingleUserRecommendTest) {
    User u1(1, vector<unsigned long int>{22, 33, 44, 11}), u2(2, vector<unsigned long int>{44, 55, 33, 22}),
      u3(3, vector<unsigned long int>{33, 55, 11});
    vector<User> users = {u1, u2, u3};
    TestManager manager = TestManager(users);
    RecAlgPart1 rec(manager);
    map<unsigned long int, unsigned long int> sumMoviesMap;
    sumMoviesMap[22] = 4;
    sumMoviesMap[44] = 3;
    sumMoviesMap[11] = 5;
    sumMoviesMap[77] = 5;
    sumMoviesMap[66] = 6;
    vector<pair<unsigned long int, unsigned long int>> sortedVec = rec.sortSumMovieMap(sumMoviesMap);
    EXPECT_EQ(sortedVec.size(), 5);
    EXPECT_EQ(sortedVec[0].first, 66);
    EXPECT_EQ(sortedVec[1].first, 11);
    EXPECT_EQ(sortedVec[2].first, 77);
    EXPECT_EQ(sortedVec[3].first, 22);
    EXPECT_EQ(sortedVec[4].first, 44);
}

// Teststing the recommendAlgorithm function:
TEST(RecommendAlgorithmTest, SingleUserRecommendTest) {
    User u1(1, vector<unsigned long int>{22, 33, 44, 11}), u2(2, vector<unsigned long int>{44, 55, 33, 22, 32, 43}),
      u3(3, vector<unsigned long int>{33, 55, 11, 32});
    vector<User> users = {u1, u2, u3};
    TestManager manager = TestManager(users);
    RecAlgPart1 rec(manager);
    EXPECT_EQ(rec.recommendAlgorithm(1, 22), "32 43 55\n");
    manager.reset();
    EXPECT_EQ(rec.recommendAlgorithm(1, 11), "32 55\n");
}

TEST(RecommendAlgorithmTest, DualUserRecommendTest) {
    User u1(1, vector<unsigned long int>{22, 33, 44, 11}), u2(2, vector<unsigned long int>{44, 55, 33, 22, 32, 43}),
      u3(3, vector<unsigned long int>{22, 33, 55, 32});
    vector<User> users = {u1, u2, u3};
    TestManager manager = TestManager(users);
    RecAlgPart1 rec(manager);
    EXPECT_EQ(rec.recommendAlgorithm(1, 22), "32 55 43\n");
    manager.reset();
    EXPECT_EQ(rec.recommendAlgorithm(1, 11), "\n");
    manager.reset();
    EXPECT_EQ(rec.recommendAlgorithm(1, 55), "32 43\n");
}

TEST(RecommendAlgorithmTest, ExampleRecommendTest) {
    User u1(1, vector<unsigned long int>{100,101,102,103}), u2(2, vector<unsigned long int>{101,102,104,105,106}),
      u3(3, vector<unsigned long int>{100,104,105,107,108}), u4(4, vector<unsigned long int>{101,105,106,107,109,110}),
      u5(5, vector<unsigned long int>{100,102,103,105,108,111}), u6(6, vector<unsigned long int>{100,103,104,110,111,112,113}),
      u7(7, vector<unsigned long int>{102,105,106,107,108,109,110}), u8(8, vector<unsigned long int>{101,104,105,106,109,111,114}),
      u9(9, vector<unsigned long int>{100,103,105,107,112,113,115}), u10(10, vector<unsigned long int>{100,102,105,106,107,109,110,116});
    vector<User> users = {u1, u2, u3, u4, u5, u6, u7, u8, u9, u10};
    TestManager manager = TestManager(users);
    RecAlgPart1 rec(manager);
    EXPECT_EQ(rec.recommendAlgorithm(1, 104), "105 106 111 110 112 113 107 108 109 114\n");
}

TEST(RecommendAlgorithmTest, UnfoundUserRecommendTest) {
    User u1(1, vector<unsigned long int>{22, 33, 44, 11}), u2(2, vector<unsigned long int>{44, 55, 33, 22, 32, 43}),
      u3(3, vector<unsigned long int>{22, 33, 55, 32});
    vector<User> users = {u1, u2, u3};
    TestManager manager = TestManager(users);
    RecAlgPart1 rec(manager);
    EXPECT_EQ(rec.recommendAlgorithm(7, 22), "\n");
    manager.reset();
    EXPECT_EQ(rec.recommendAlgorithm(4, 11), "\n");
}

TEST(RecommendAlgorithmTest, OnlyMovieWatchedRecommendTest) {
    User u1(1, vector<unsigned long int>{22}), u2(2, vector<unsigned long int>{44, 55, 33, 22, 32, 43}),
      u3(3, vector<unsigned long int>{22, 33, 55, 32});
    vector<User> users = {u1, u2, u3};
    TestManager manager = TestManager(users);
    RecAlgPart1 rec(manager);
    EXPECT_EQ(rec.recommendAlgorithm(1, 22), "32 33 55 43 44\n");
    manager.reset();
    EXPECT_EQ(rec.recommendAlgorithm(1, 11), "\n");
}

TEST(RecommendAlgorithmTest, NoSharedMoviesRecommendTest) {
    User u1(1, vector<unsigned long int>{22, 11}), u2(2, vector<unsigned long int>{44, 55, 33, 22, 32, 43}),
      u3(3, vector<unsigned long int>{22, 33, 55, 32}), u4(4, vector<unsigned long int>{99, 88, 77});
    vector<User> users = {u1, u2, u3, u4};
    TestManager manager = TestManager(users);
    RecAlgPart1 rec(manager);
    EXPECT_EQ(rec.recommendAlgorithm(1, 99), "\n");
    manager.reset();
    EXPECT_EQ(rec.recommendAlgorithm(1, 11), "\n");
}

TEST(RecommendAlgorithmTest, MoreThen10RecommendTest) {
    User u1(1, vector<unsigned long int>{22, 11}), u2(2, vector<unsigned long int>{11, 22, 33, 44, 55, 66, 77, 88, 99, 100, 110, 120, 130, 140, 150}), 
    u3(3, vector<unsigned long int>{11, 22, 44, 66, 88, 100, 120, 140});
    vector<User> users = {u1, u2, u3};
    TestManager manager = TestManager(users);
    RecAlgPart1 rec(manager);
    EXPECT_EQ(rec.recommendAlgorithm(1, 22), "44 66 88 100 120 140 33 55 77 99\n");
    manager.reset();
    EXPECT_EQ(rec.recommendAlgorithm(1, 11), "44 66 88 100 120 140 33 55 77 99\n");
}

// testing Recommender
TEST(RecommendTest, ExecuteProperTest) {
    User u1(1, vector<unsigned long int>{100,101,102,103}), u2(2, vector<unsigned long int>{101,102,104,105,106}),
      u3(3, vector<unsigned long int>{100,104,105,107,108}), u4(4, vector<unsigned long int>{101,105,106,107,109,110}),
      u5(5, vector<unsigned long int>{100,102,103,105,108,111}), u6(6, vector<unsigned long int>{100,103,104,110,111,112,113}),
      u7(7, vector<unsigned long int>{102,105,106,107,108,109,110}), u8(8, vector<unsigned long int>{101,104,105,106,109,111,114}),
      u9(9, vector<unsigned long int>{100,103,105,107,112,113,115}), u10(10, vector<unsigned long int>{100,102,105,106,107,109,110,116});
    vector<User> users = {u1, u2, u3, u4, u5, u6, u7, u8, u9, u10};
    TestManager manager = TestManager(users);
    Recommender rec(manager);
    std::vector<unsigned long int> arr = {1, 104};
    EXPECT_EQ(rec.execute(arr), "105 106 111 110 112 113 107 108 109 114\n");
}

TEST(RecommendTest, ExecuteErrorTest) {
    User u1(1, vector<unsigned long int>{100,101,102,103}), u2(2, vector<unsigned long int>{101,102,104,105,106}),
      u3(3, vector<unsigned long int>{100,104,105,107,108}), u4(4, vector<unsigned long int>{101,105,106,107,109,110}),
      u5(5, vector<unsigned long int>{100,102,103,105,108,111}), u6(6, vector<unsigned long int>{100,103,104,110,111,112,113}),
      u7(7, vector<unsigned long int>{102,105,106,107,108,109,110}), u8(8, vector<unsigned long int>{101,104,105,106,109,111,114}),
      u9(9, vector<unsigned long int>{100,103,105,107,112,113,115}), u10(10, vector<unsigned long int>{100,102,105,106,107,109,110,116});
    vector<User> users = {u1, u2, u3, u4, u5, u6, u7, u8, u9, u10};
    TestManager manager = TestManager(users);
    Recommender rec(manager);
    std::vector<unsigned long int> arr1 = {1, 104, 88}, arr2 = {1};
    EXPECT_EQ(rec.execute(arr1), "");
    EXPECT_EQ(rec.execute(arr2), "");
}

TEST(RecommendTest, GetInstructionsTest) {
    User u1(1, vector<unsigned long int>{100,101,102,103}), u2(2, vector<unsigned long int>{101,102,104,105,106}),
        u3(3, vector<unsigned long int>{100,104,105,107,108}), u4(4, vector<unsigned long int>{101,105,106,107,109,110}),
        u5(5, vector<unsigned long int>{100,102,103,105,108,111}), u6(6, vector<unsigned long int>{100,103,104,110,111,112,113}),
        u7(7, vector<unsigned long int>{102,105,106,107,108,109,110}), u8(8, vector<unsigned long int>{101,104,105,106,109,111,114}),
        u9(9, vector<unsigned long int>{100,103,105,107,112,113,115}), u10(10, vector<unsigned long int>{100,102,105,106,107,109,110,116});
    vector<User> users = {u1, u2, u3, u4, u5, u6, u7, u8, u9, u10};
    TestManager manager = TestManager(users);
    Recommender rec(manager);
    EXPECT_EQ(rec.getInstructions(), "recommend[userid] [movieid]");
}