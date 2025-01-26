#include "Getter.h"
#include "User.h"
#include <gtest/gtest.h>
#include <vector>
#include <iostream>

using namespace std;

class TestManager1 : public IDataManager {
public:
    vector<User> users;
    unsigned long int pointer=0, numOfUsers;
    TestManager1(vector<User> users) {
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
        for (User user : users) {
            if (user.getId() == userId) {
                return true;
            }
        }
        return false;
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

TEST(GetterTest, ExecuteProperTest) {
    User u1(1, vector<unsigned long int>{100,101,102,103}), u2(2, vector<unsigned long int>{101,102,104,105,106}),
      u3(3, vector<unsigned long int>{100,104,105,107,108}), u4(4, vector<unsigned long int>{101,105,106,107,109,110}),
      u5(5, vector<unsigned long int>{100,102,103,105,108,111}), u6(6, vector<unsigned long int>{100,103,104,110,111,112,113}),
      u7(7, vector<unsigned long int>{102,105,106,107,108,109,110}), u8(8, vector<unsigned long int>{101,104,105,106,109,111,114}),
      u9(9, vector<unsigned long int>{100,103,105,107,112,113,115}), u10(10, vector<unsigned long int>{100,102,105,106,107,109,110,116});
    vector<User> users = {u1, u2, u3, u4, u5, u6, u7, u8, u9, u10};
    TestManager1 manager = TestManager1(users);
    Getter getter(manager);
    std::vector<unsigned long int> arr = {1, 104};
    EXPECT_EQ(getter.execute(arr), "200 Ok\n\n105 106 111 110 112 113 107 108 109 114\n");
}

TEST(GetterTest, ExecuteWrongNumOfArgumentsTest) {
    User u1(1, vector<unsigned long int>{100,101,102,103}), u2(2, vector<unsigned long int>{101,102,104,105,106}),
      u3(3, vector<unsigned long int>{100,104,105,107,108}), u4(4, vector<unsigned long int>{101,105,106,107,109,110}),
      u5(5, vector<unsigned long int>{100,102,103,105,108,111}), u6(6, vector<unsigned long int>{100,103,104,110,111,112,113}),
      u7(7, vector<unsigned long int>{102,105,106,107,108,109,110}), u8(8, vector<unsigned long int>{101,104,105,106,109,111,114}),
      u9(9, vector<unsigned long int>{100,103,105,107,112,113,115}), u10(10, vector<unsigned long int>{100,102,105,106,107,109,110,116});
    vector<User> users = {u1, u2, u3, u4, u5, u6, u7, u8, u9, u10};
    TestManager1 manager = TestManager1(users);
    Getter getter(manager);
    std::vector<unsigned long int> arr1 = {1, 104, 88}, arr2 = {1};
    EXPECT_EQ(getter.execute(arr1), "400 Bad Request\n");
    EXPECT_EQ(getter.execute(arr2), "400 Bad Request\n");
}

TEST(GetterTest, ExecuteUserDoesNotExistTest) {
    User u1(1, vector<unsigned long int>{100,101,102,103}), u2(2, vector<unsigned long int>{101,102,104,105,106}),
      u3(3, vector<unsigned long int>{100,104,105,107,108}), u4(4, vector<unsigned long int>{101,105,106,107,109,110}),
      u5(5, vector<unsigned long int>{100,102,103,105,108,111}), u6(6, vector<unsigned long int>{100,103,104,110,111,112,113}),
      u7(7, vector<unsigned long int>{102,105,106,107,108,109,110}), u8(8, vector<unsigned long int>{101,104,105,106,109,111,114}),
      u9(9, vector<unsigned long int>{100,103,105,107,112,113,115}), u10(10, vector<unsigned long int>{100,102,105,106,107,109,110,116});
    vector<User> users = {u1, u2, u3, u4, u5, u6, u7, u8, u9, u10};
    TestManager1 manager = TestManager1(users);
    Getter getter(manager);
    std::vector<unsigned long int> arr1 = {13, 104}, arr2 = {0, 100};
    EXPECT_EQ(getter.execute(arr1), "404 Not Found\n");
    EXPECT_EQ(getter.execute(arr2), "404 Not Found\n");
}

TEST(GetterTest, GetInstructionsTest) {
    User u1(1, vector<unsigned long int>{100,101,102,103}), u2(2, vector<unsigned long int>{101,102,104,105,106}),
        u3(3, vector<unsigned long int>{100,104,105,107,108}), u4(4, vector<unsigned long int>{101,105,106,107,109,110}),
        u5(5, vector<unsigned long int>{100,102,103,105,108,111}), u6(6, vector<unsigned long int>{100,103,104,110,111,112,113}),
        u7(7, vector<unsigned long int>{102,105,106,107,108,109,110}), u8(8, vector<unsigned long int>{101,104,105,106,109,111,114}),
        u9(9, vector<unsigned long int>{100,103,105,107,112,113,115}), u10(10, vector<unsigned long int>{100,102,105,106,107,109,110,116});
    vector<User> users = {u1, u2, u3, u4, u5, u6, u7, u8, u9, u10};
    TestManager1 manager = TestManager1(users);
    Getter getter(manager);
    EXPECT_EQ(getter.getInstructions(), "GET,arguments: [userid] [movieid]");
}