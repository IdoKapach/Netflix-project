#ifndef FILEDATAMANAGER_H
#define FILEDATAMANAGER_H

#include "IDataManager.h"
#include <fstream>
#include <string>
#include <vector>
#include <tuple>
#include <filesystem>
#include <iostream>
#include <algorithm>

class FileDataManager : public IDataManager {
    public:
        FileDataManager(std::string directoryPath, std::string delimiter);
        ~FileDataManager();
        // add movie to the designated user
        int addMovie(unsigned long userId, unsigned long movieId) override;
        // delete movie from the designated user
        int deleteMovie(unsigned long userId, unsigned long movieId) override;
        // check if the user has watched the movie
        bool hasMovie(unsigned long userId, unsigned long movieId) override;
        // check if the user exists
        bool hasUser(unsigned long userId) override;
        // gets the next user in the iterator
        User getNextUser() override;
        // returns true if the iterator has more users to provide
        bool hasMoreUsers() override;
        // reset the iterator + update added users in the iterator
        void reset() override;

    private:
        // stores the directory path where the user files are stored
        std::string directoryPath;
        // stores the delimiter used to separate the movie IDs in the user files
        std::string delimiter;
        // the sorted list of user files (for consistent iteration)
        std::vector<std::filesystem::path> userFiles;
        // iterator with the users since the last reset()
        std::vector<std::filesystem::path>::iterator userFilesIterator;
        // loads the requested user from the file 
        User* loadUser(unsigned long userId);
        // saves the user to the file
        void saveUser(User* user);
        // parses the movies from the line (string) in the file
        std::vector<unsigned long> parseMovies(std::string line);
        // parses the user ID from the file path
        unsigned long parseUserId(std::string filePath);
};

#endif // FILEDATAMANAGER_H