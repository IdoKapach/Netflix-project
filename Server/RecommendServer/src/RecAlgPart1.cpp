#include "RecAlgPart1.h"
#include <algorithm>
#include <sstream>

RecAlgPart1::RecAlgPart1(IDataManager& dataManager) : dataManager(dataManager) {}

/*
 response to build map<unsigned long int, vector<int*>> movieMap and getting inputed user's movies vector.
 passing iterativly over the users in dataManager
*/
map<unsigned long int, vector<unsigned long int*>> RecAlgPart1::enterUsersToMovieMap(unsigned long int userId, unsigned long int movieId, vector<unsigned long int>& userMovies) {
    // initiating a map that will map movies to vector of pointers to numbers so that each 
    // number will represet the "score" it's correspond user has. score means the number
    // of the shared movies the user has with the inputed user.
    map<unsigned long int, vector<unsigned long int*>> movieMap;
    while (this->dataManager.hasMoreUsers()) {
        User user = this->dataManager.getNextUser();
        // if the user is the inputed user:
        if (user.getId() == userId) {
            // setting userMovies to contain the inputed user's movies
            userMovies = user.getMovies();
        }
        else {
            bool con = true;
            vector<unsigned long int> movies = user.getMovies();
            // if user doesn't have movieId, skipping to the next user
            if (find(movies.begin(), movies.end(), movieId) == movies.end()) {
                con = false;
            }
            // initiating an int which will represent the score of the user and entering it's address 
            // to the vector of each of the user's movies.
            if (con) {
                unsigned long int* x = new unsigned long int;
                *x = 0;
                for (unsigned long int movie : movies) {
                    movieMap[movie].push_back(x);
                }
            }
        }
    }
    return movieMap;
}
/*
updates the values in the vectors so that with each update of a value x
that associated with user y, the update will appear at all the appearances of
the pointer x.
*/
void RecAlgPart1::updateMovieMap(map<unsigned long int, vector<unsigned long int*>>& movieMap, vector<unsigned long int>& userMovies){
    //  for each inputed user's movie:
    for (unsigned long int movie : userMovies) {
        // for each pointer in movie's map value:
        for (unsigned long int* x: movieMap[movie]) {
            ++(*x); 
        }
    }
}
/*
this part will sums the values in each vector in movieMap and keeps the sum in the correspond
key in the map<unsigned long int, int> sumMovieMap
*/
map<unsigned long int, unsigned long int> RecAlgPart1::sumMovieMapValues(map<unsigned long int, vector<unsigned long int*>>& movieMap, unsigned long int movieId, vector<unsigned long int>& userMovies) {
    map<unsigned long int, unsigned long int> sumMovieMap;
    // for each key in movieMap
    for (auto mp : movieMap) {
        // summing the vector's values
        unsigned long int sum = 0;
        vector<unsigned long int*> vecM = mp.second;
        for (unsigned long int* x : vecM) {
            sum += *x;
        }
        // keeps the sum in the correspond
        // key in the map sumMovieMap
        sumMovieMap[(unsigned long int)mp.first] = sum;
    }
    // setting the value in movieId and in the movies the inputed user watched to 0,
    // so that it won't include them in the recommended movies
    sumMovieMap[movieId] = 0;
    //  for each inputed user's movie:
    for (unsigned long int movie : userMovies) {
        // for each pointer in movie's map value:
        sumMovieMap[movie]=0;
    }
    return sumMovieMap;
}
/*
sort vector<pair<unsigned long int, int>> which based on sumMoviesMap according to the value(sum)
and between keys with the same values, it will sort them according to the keys (descending order)
*/
vector<pair<unsigned long int, unsigned long int>> RecAlgPart1::sortSumMovieMap(map<unsigned long int, unsigned long int>& sumMovieMap) {
    // copy the sumMovieMap's contents to a vector of pairs
    vector<pair<unsigned long int, unsigned long int>> sortMoviesVector(sumMovieMap.begin(), sumMovieMap.end());

    // sort the vector based on values, and by keys for equal values
    sort(sortMoviesVector.begin(), sortMoviesVector.end(), [](const pair<unsigned long int, unsigned long int>& a, const pair<unsigned long int, unsigned long int>& b) {
        if (a.second == b.second) {
            // sort by key if the values are equal
            return a.first < b.first; 
        }
        // sort by value otherwise
        return a.second > b.second; 
    });
    return sortMoviesVector;
}
/*
delocaiting the allocated memory of pointers in movieMap.
*/
void RecAlgPart1::delocateMovieMapValues(map<unsigned long int, vector<unsigned long int*>>& movieMap){
    // first step will be count the number of pointers to each user's score
    // the count is calculated by getting it's value
    // for each key in movieMap:
    for (auto mp : movieMap) {
        // itirating over the pointers in his vector
        vector<unsigned long int*> vecM = mp.second;
        for (unsigned long int* x : vecM) {
            // zero *x so that it will be possible to count it's appearances in the next scan
            *x = 0;
        }
    }
    for (auto mp : movieMap) {
        // itirating over the pointers in his vector
        vector<unsigned long int*> vecM = mp.second;
        for (unsigned long int* x : vecM) {
            // counting the number of pointers to the same user's score
            (*x)++;
        }
    }
    // the second step is to ensure that the last pointer delocating the
    // allocated memory.
    // for each key in movieMap:
    for (auto mp : movieMap) {
        // itirating over the pointers in his vector
        vector<unsigned long int*> vecM = mp.second;
        for (unsigned long int* x : vecM) {
            // checking if this is the last pointer
            (*x)--;
            if (*x == 0) {
                // if it is, delocating memory.
                delete x;
            }
        }
    }
}
/*
build the final string of list of up to 10 recommended movies
*/
string RecAlgPart1::generateStringList(vector<pair<unsigned long int, unsigned long int>>& sortMoviesVector) {
    // build the final string of list of up to 10 recommended movies
    stringstream sstrMovies;
    int i = 0;
    // for each pair in sortMoviesVector:
    for (pair<unsigned long int, unsigned long int> movie : sortMoviesVector) {
        // if there's already 10 movies in sstrMovies or the score of the movie is 0, break
        if (i == 10 || movie.second == 0) {
            break;
        }
        // otherwise, append it's ID to sstrMovies
        i++;
        sstrMovies << movie.first << " ";
    }
    // deleting last space
    string finalRecommend = sstrMovies.str();
    if (i > 0) {
        finalRecommend.pop_back();
    }
    // adding endl
    finalRecommend += "\n";
    
    return finalRecommend;
}
/*
    the function recieves as arguments
    user id and movie id - of type unsighned long int.
    and will return string, "list", of recommended id-movies.
*/
string RecAlgPart1::recommendAlgorithm(unsigned long int userId, unsigned long int movieId) {
    // initiating a vector of the movies of the inputed user
    vector<unsigned long int> userMovies;
    // initiating a map that will map movies to vector of pointers to numbers so that each 
    // number will represet the "score" it's correspond user has. score means the number
    // of the shared movies the user has with the inputed user.
    map<unsigned long int, vector<unsigned long int*>> movieMap;
    // This part response to build movieMap and getting inputed user's movies vector and saving it in userMovies.
    // passing iterativly over the users in dataManager
    movieMap = enterUsersToMovieMap(userId, movieId, userMovies);

    // in case the user doesn't appear in the data, returning empty list
    if (!userMovies.size()){
        string emptyStr = "\n";
        return emptyStr;
    }

    // after setting the vectors in the movieMap, this part
    // updates the values in the vectors so that with each update of a value x
    // that associated with user y, the update will appear at all the appearances of
    // the pointer x.
    updateMovieMap(movieMap, userMovies);

    // this part will sum the values in each vector in movieMap and keeps the sum in the correspond
    // key in the map sumMovieMap
    map<unsigned long int, unsigned long int> sumMovieMap = sumMovieMapValues(movieMap, movieId, userMovies);

    // There's no longer use to movieMap, so freeing the allocated memory of
    // the variables in it.
    delocateMovieMapValues(movieMap);

    // This part in charge to sort sumMoviesMap according to the value(sum)
    // and between keys with the same values, it will sort them according to the keys (descending order)
    vector<pair<unsigned long int, unsigned long int>> sortMoviesVector = sortSumMovieMap(sumMovieMap);

    return generateStringList(sortMoviesVector);
}
