#ifndef RECALGPART1_H
#define RECALGPART1_H

#include <string>
#include <vector>
#include <map>
#include "IDataManager.h"

using namespace std;

/*
A class that responsible to implement movies recommend algorithm that described at part1
*/
class RecAlgPart1 {
private:
    IDataManager& dataManager;

public:
    RecAlgPart1(IDataManager& dataManager);
    /*
     response to build movieMap and getting inputed user's movies vector.
     passing iterativly over the users in dataManager
    */
    map<unsigned long int, vector<unsigned long int*>> enterUsersToMovieMap(unsigned long int userId, unsigned long int movieId, vector<unsigned long int>& userMovies);
    /*
    updates the values in the vectors so that with each update of a value x
    that associated with user y, the update will appear at all the appearances of
    the pointer x.
    */
    void updateMovieMap(map<unsigned long int, vector<unsigned long int*>>& movieMap, vector<unsigned long int>& userMovies);
    /*
    this part will sum the values in each vector in movieMap and keeps the sum in the correspond
    key in the map<unsigned long int, int> sumMovieMap
    */
    map<unsigned long int, unsigned long int> sumMovieMapValues(map<unsigned long int, vector<unsigned long int*>>& movieMap, unsigned long int movieId, vector<unsigned long int>& userMovies);
    /*
    sort vector<pair<unsigned long int, unsigned long int>> which based on sumMoviesMap according to the value(sum)
    and between keys with the same values, it will sort them according to the keys(ascending order)
    */
    vector<pair<unsigned long int, unsigned long int>> sortSumMovieMap(map<unsigned long int, unsigned long int>& sumMovieMap);
    /*
    delocaiting the allocated memory of pointers in movieMap.
    */
    void delocateMovieMapValues(map<unsigned long int, vector<unsigned long int*>>& movieMap);
    /*
    build the final string of list of up to 10 recommended movies
    */
    string generateStringList(vector<pair<unsigned long int, unsigned long int>>& sortMoviesVector); 
    /*
     the function recieves as arguments
     user id and movie id - of type unsighned long int.
     and will return string, "list", of recommended id-movies.
    */
    std::string recommendAlgorithm(unsigned long int userId, unsigned long int movieId);
};

#endif