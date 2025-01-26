#ifndef IDATAMANAGER_H
#define IDATAMANAGER_H

#include "User.h"
#include <tuple>
#include "Icommand.h"

class IDataManager {
    public:
        virtual ~IDataManager() = default;
        /* adds a movie to the user's watched list,
        returns 1 if successful or 0 otherwise */
        virtual int addMovie(unsigned long userId, unsigned long movieId) = 0;
        // deletes a movie from the user's watched list
        virtual int deleteMovie(unsigned long userId, unsigned long movieId) = 0;
        // checks if a user watched a specific movie
        virtual bool hasMovie(unsigned long userId, unsigned long movieId) = 0;
        // check if a user exists
        virtual bool hasUser(unsigned long userId) = 0;
        /* gets the next user in the db and returns it.
        if there are no more users, throw out of range exception */
        virtual User getNextUser() = 0;
        // returns true if there are more users in the db, false otherwise
        virtual bool hasMoreUsers() = 0;
        // reset the data manager to the first user
        virtual void reset() = 0;
};
#endif // IDATAMANAGER_H