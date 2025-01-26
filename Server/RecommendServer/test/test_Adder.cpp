#include <gtest/gtest.h>
#include "Icommand.h"
#include "Adder.h"
#include "IDataManager.h"
#include "FileDataManager.h"
#include <cstdio>
#include <fstream>

class test_Adder : public ::testing::Test {
protected:
    IDataManager* fdm;
    Icommand* adder;

    void SetUp() override {
        fdm = new FileDataManager("./test_adder", ",");
        adder = new Adder(fdm);
    }

    void TearDown() override {
        filesystem::remove_all("./test_adder");
        delete fdm;
        delete adder;
    }
};

// tests for execute function in class adder.
TEST_F(test_Adder, NormalArgs) {

    // execute input.
    vector<unsigned long int> args = {100, 1, 2, 3};
    adder->execute(args);
    // open file.
    ifstream file("./test_adder/100.txt");
    EXPECT_EQ(file.is_open(), true);
    string lineInFile;

    // test content of file.
    getline(file, lineInFile);
    EXPECT_EQ(lineInFile, "1,2,3");

    // close and delete file.
    file.close();
}

TEST_F(test_Adder, EmptyArgs) {

    // execute input.
    vector<unsigned long int> args = {};
    EXPECT_ANY_THROW({
        adder->execute(args);
    });
}

TEST_F(test_Adder, OneArg) {

    // execute input.
    vector<unsigned long int> args = {100};
    EXPECT_ANY_THROW({
        adder->execute(args);
    });
}

TEST_F(test_Adder, DupArgs) {

    // execute input.
    vector<unsigned long int> args = {100, 1, 1, 3};
    adder->execute(args);
    // open file.
    ifstream file("./test_adder/100.txt");
    EXPECT_EQ(file.is_open(), true);
    string lineInFile;

    // test content of file.
    getline(file, lineInFile);
    EXPECT_EQ(lineInFile, "1,3");

    // close file.
    file.close();
}

TEST_F(test_Adder, MultyUse) {

    // execute input.
    vector<unsigned long int> args1 = {100, 1, 2, 3};
    adder->execute(args1);

    vector<unsigned long int> args2 = {100, 2, 3, 4};
    adder->execute(args2);

    // open file.
    ifstream file("./test_adder/100.txt");
    EXPECT_EQ(file.is_open(), true);
    string lineInFile;

    // test content of file.
    getline(file, lineInFile);
    EXPECT_EQ(lineInFile, "1,2,3,4");

    // close file.
    file.close();
}

TEST_F(test_Adder, getInstructionsTest) {
    EXPECT_EQ(adder->getInstructions(), "add [userid] [movieid1] [movieid2] ...");
}