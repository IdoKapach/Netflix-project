import Movie from "../models/movie.js"

// return all movies that query is a sub-string of their name field or of one of categories elemnts
const getMovieByQuery = async (query) => {
    try {
        const results = await Movie.find({
          $or: [
            { name: { $regex: query, $options: 'i' } }, 
            { categories: { $elemMatch: { $regex: query, $options: 'i' } } },
          ],
        })
        // throw exeption in case no movie found
        if (results.length === 0) {
            throw "No movie found"
        }
        return results
      } 
    catch (e) {
        throw e
    }
}

export default {getMovieByQuery}