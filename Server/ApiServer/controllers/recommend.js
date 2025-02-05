import { getRecommendService, postRecommendService } from "../services/recommend.js";
import movieServices from "../services/movie.js"
import { getUserService, updateUserService } from "../services/User.js";


const getRecommend = async (req, res) => {
    const movie = req.params.id
    const user = req.user
    try {
        const recommends = await getRecommendService(user, movie)
        if (recommends) {
            return res.status(200).json({ movies: recommends })
        } else {
            return res.status(404).json({error: "Recommend not found"})
        }
    } catch (e) {
        console.error("Error in getRecommend: ", e)
        return res.status(500).json({ error: e.message })
    }
}

const postRecommend = async (req, res) => {
    
    console.error(`posting`)
    // extract movie and user ids
    const movie = req.params.id
    console.error(`got movie id`)
    const user = req.user
    console.error(`post movie: ${movie} to user: ${user}`)


    // check if movie is in the api DB
    try {
        await movieServices.getMovieById(movie)
    } catch (e) {
        console.error(`Movie ${movie} wasnt found in database`)
        return res.status(404).json({ error: "wrong movie ID" })
    }

    // init the userDocument so its accessible in later blocks
    let userDocument = null
    // check user is in api db
    try {
        // get the user as recorded in the db
        userDocument = await getUserService(user)
    } catch (e) {
        console.error(`user ${user} wasnt found in db`)
        return res.status(409).json({ error: 'wrong user ID' })
    }

    // iterate over the user movies to make sure the movie wasnt already watched
    for (var m of userDocument.movies) {
        if (movie == m) {
            console.error(`${movie} was already watched by ${user}`)
            return res.status(409).json({ error: 'Movie already watched by user' })
        }
    }

    // add the movie to user in api db
    try {
        userDocument.movies.push({ id: movie })
        await updateUserService(user, { movies: userDocument.movies })
    } catch (e) {
        console.error("failed to update user data in api db")
        return res.status(500).json({ error: "recommend service error" })
    }

    // add the movie to user in recommend db
    try {
        await postRecommendService(user, movie)
        res.status(201).json({ message: "Movie added successfully" })
    } catch (e) {
        console.error("Error in postRecommend: ", e)
        res.status(500).json({ error: "Failed to add movie recommendation" })
    }
}

export { getRecommend, postRecommend }