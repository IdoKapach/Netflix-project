#include "Ioutput.h"
#include "TerminalOutput.h"
#include <gtest/gtest.h>

class test_TerminalOutput : public ::testing::Test {
protected:
    Ioutput* test;
    ostringstream oss;
    streambuf* originalBuffer;

    void SetUp() override {
        test = new TerminalOutput();

        //save cout pointer to restore in TearDown.
        originalBuffer  = cout.rdbuf();

        // switch cout to print to our string.
        cout.rdbuf(oss.rdbuf());
    }

    void TearDown() override {

        // return cout to its original implmantation.
        cout.rdbuf(originalBuffer);
        delete test;
    }
};

// tests for the function printAnswer, redirects cout to a string which is compared.
TEST_F(test_TerminalOutput, NormalArgsEndl) {

    // test printAnswer.
    test->printAnswer("abcde", true);
    string output = oss.str();
    EXPECT_EQ(output, "abcde\n");
}

TEST_F(test_TerminalOutput, NormalArgsNoEndl) {

    // test printAnswer.
    test->printAnswer("abcde");
    string output = oss.str();
    EXPECT_EQ(output, "abcde");
}

TEST_F(test_TerminalOutput, FewLinesArgs) {

    // test printAnswer.
    test->printAnswer("abc def\nline2\nline3");
    string output = oss.str();
    EXPECT_EQ(output, "abc def\nline2\nline3");
}

TEST_F(test_TerminalOutput, EmptyArgs) {

    // test printAnswer.
    test->printAnswer("");
    string output = oss.str();
    EXPECT_EQ(output, "");
}
