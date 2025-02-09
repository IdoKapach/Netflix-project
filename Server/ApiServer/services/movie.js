import Movie from '../models/movie.js'
import Category from '../models/category.js';
import {User} from '../models/User.js'
import { IdCounter } from '../models/IdCounter.js';
import { deleteRecommendService } from './recommend.js';
import { getUserService } from './User.js';

const getNextId = async () => {
    try {
        const nextId = await IdCounter.findById("nextMovieId")

        // if the counter doesnt exist, create it and return 0 (first _id)
        if (!nextId) {
            const newCounter = new IdCounter({ _id: 'nextMovieId', seq: "0"})
            await newCounter.save()
            return "0"
        }
        // convert id string (stored in mongo) to BigInt (cant be stored in mongo) + increment
        const nextSeq = BigInt(nextId.seq) + BigInt(1)
        // convert id BigInt back to string
        const nextSeqStr = nextSeq.toString()
    
        // update nextId in mongo DB
        await IdCounter.findByIdAndUpdate(
            'nextMovieId',
            { seq: nextSeqStr},
            { new: true }
        )
        return nextSeqStr
    } catch (e) {
        console.error("Error in getNextId: ", e)
        throw e
    }
}

// function that responsible to create movie, given name, video and categories list arguments.
const createMovie = async (name, video, categories, image, description) => {
    console.log("CREATE: 1:", name, "2:", video, "3:", categories, "4:", image, "5:", description)
    // checks if all the fields are given
    let errors = []
    if (!name) {
        console.error("Name is required", e.message);
        errors.push("Name is required");
    }
    if (!video) {
        console.error("Video is required", e.message);
        errors.push("Video is required");
    }
    if (!categories || !Array.isArray(categories) || categories.length === 0) {
        console.error("Cate is required", e.message);
        errors.push("Categoris field is required");
    }
    if (!image) {
        console.error("Image is required", e.message);
        errors.push("Image is required");
    }
    if (!description) {
        console.error("Description is required", e.message);
        errors.push("Description is required");
    }
    // checks if at least one of fields wasn't given
    if (errors.length) throw errors;

    // checking if all the categories exist
    try{
        for (var catName of categories) {
            const category = await Category.find({'name' : catName})
            if (!category || category.length === 0) {
                console.log(catName, 'isnt a category')
                console.error("One of the categories doesn't exist", e.message)
                throw "One of the categories doesn't exist"
            }
    }}
    catch (e) {
        throw [e]
    }

    console.log('made it here')

    // in case errors didn't occur, save the movie
    try {
        let id = await getNextId()
        const movie = new Movie({ _id: id, name: name, video: video, image: image,  categories: categories, description: description });
        await movie.save()
        // add the movie to all its' categories
        const movieId = movie._id.toString()
        for (var catName of categories) {
            const category = (await Category.findOne({'name' : catName}))
            category.movies.push(movieId)
            await category.save()
        }

        // return the movie - on success
        console.log("The new movie: ", movie)
        return movie;
    } catch (e) {
        throw [e];
    }
};

// function that responsible to change the movie that has movieId, given name, video and categories list arguments.
const changeMovie = async (movieId, name, video, categories, image, description) => {
    let errors = []
    // checks if name has given
    if (name === undefined) {
        errors.push("Name is required")
        console.log("Name is required");
    }
    // checks if video has given
    if (video === undefined) {
        errors.push("Video is required")
        console.log("video is required");
    }
    // checks if categories list has given
    if (categories === undefined || categories.constructor != Array || categories.length === 0) {
        errors.push("Categoris field is required")
        console.log("categories is required");
    }
    if (description === undefined) {
        errors.push("Description is required")
        console.log("Description is required");
    }
    // checks if at least one of fields wasn't given
    if (errors.length) {
        throw errors
    }
    // checking if all the categories exist and that there's an existing movie with movieId
    try{
        for (var catName of categories) {
            const category = await Category.find({'name' : catName})
            if (!category || category.length === 0) {
                throw "One of the categories isn't exist"
            }
        } 
    }
    catch (e) {
        throw [e]
    }
    // in case error didn't occur, chaging the movie
    let movie
    let oldCategories
    console.log("chnging movie...");
    try {
        movie = await getMovieById(movieId);
        oldCategories = movie.categories
    }
    // in case There's no movie with the speciefied ID.
    catch {
        throw [404, "There's no movie with the speciefied ID."]
    }
    console.log("almost");
    try {
        movie.name = name
        movie.categories = categories
        movie.video = video
        movie.description = description
        movie.image = image
        movie = await movie.save()
    }
    catch {
        // in case Movie's name is already in use by another movie
        throw ["Movie's name is already in use"]
    }

    console.log("fixing categories");
    // removing the movieId from each movies list of each category that the old version of the movie was under
    for (var catName of oldCategories) {
        const category = (await Category.find({'name' : catName}))[0]
        category.movies = category.movies.filter(id => id !== movieId)
        await category.save()
    }
    // appending the movieId to each movies list of each category that the movie is under it
    for (var catName of categories) {
        const category = (await Category.find({'name' : catName}))[0]
        category.movies.push(movieId)
        await category.save()
    }
    return movie
};

// deletes movie which was specified by it's Id.
const deleteMovie = async (movieId) => {
    let movie = null;
    // search after the movie and return an error in case it wasn't found
    try {
        movie = await getMovieById(movieId);
    }
    catch (e) {
        throw e
    }
    // removing the movieId from each movies list of each category that the movie was under
    let categories = movie.categories
    for (var catName of categories) {
        const category = (await Category.find({'name' : catName}))[0]
        category.movies = category.movies.filter(id => id !== movieId)
        await category.save()
    }
    // removing the movieId from each movies list of each user that watched the movie
    const users = await User.find()
    for (var user of users) {
        // removing from mongoose
        user.movies = user.movies.filter(mov => mov.id !== movieId)
        await user.save()
        // removing from recommend database
        var userId = user._id
        await deleteRecommendService(userId, movieId)
    }
    // delete the movie
    await movie.deleteOne();
    return movie;
};

// gets specific movie by it's Id.
const getMovieById = async (id) => {
    // searches for the movie
    try {
        const movie = await Movie.findById(id);
        if (!movie) {
            throw "error"
        }
        // returns it if it is found
        return movie
    }
    // throws exeption in case it wasn't found
    catch {
        throw "Movie wasn't found";
    }
};

// get 20 movies from any promoted category and the 20 latest viewd movies
const getMovies = async (id) => {
    let user
    try {
        user = await getUserService(id)
        if (!user) {
            console.log(`user not found`)
            throw "user not found"
        }
    }
    catch (e) {
        console.log(`catch something`)
        // throwing exeption in case user doesn't exist
        throw e
    }

    console.log(`yes user`)
    const userMovies = new Set(user.movies.map(movie => movie.id));
    // get the promoted categories
    let promotedCategories;
    try {
        promotedCategories = await Category.find({ "promoted": true });
    } catch (e) {
        console.log("ERROR: ", e)
        throw e
    }
    let finalJson = {}
    for (var category of promotedCategories) {
        // get the movies that isn't watched by the user
        var noWatchedMovies = category.movies.filter(movie => !userMovies.has(movie))
        // shuffeling it to get random 20 movies
        noWatchedMovies.sort(() => Math.random() - 0.5)
        // get 20 movies from the array
        noWatchedMovies = noWatchedMovies.slice(0, 20)
        // appending the array to the json
        var name = category.name
        finalJson[name] = noWatchedMovies
    }

    // get the last 20 movies that war watched by the user
    let latestViewedMovies = user.movies
    // sort by last viewed date and get the first 20 movies
    latestViewedMovies = latestViewedMovies.sort((a, b) => new Date(b.watchDate) - new Date(a.watchDate)).slice(0, 20)
    latestViewedMovies = latestViewedMovies.map(movie => movie.id)
    // appending the array to the json
    finalJson['Latest viewed movies'] = latestViewedMovies

    return finalJson
}




export default { createMovie, getMovieById, changeMovie, deleteMovie, getMovies }