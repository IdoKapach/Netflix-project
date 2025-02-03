import movieServices from '../services/movie.js'
import fs from 'fs';
import path from 'path';

// function that responsible to create movie given name, video and categories arguments. this func demands for user authantication
const createMovie = async (req, res) => {
    try {
        const title = req.body.title;
        const videoPath = req.filePaths.
        //TODO: add the paths in the fileUpload.js, and fit them to the service call
        // create the movie in the mongoDB
        const newMovie = await movieServices.createMovie(req.body.title, videoPath, req.body.categories, imagePath);

        return res.json(newMovie);
    } catch(e) {
        return res.status(400).json({errors : [e.message] })
    }
};

// function that responsible to change movie given it's Id and given name, video and categories arguments for update.
//  this func demands for user authantication
const changeMovie = async (req, res) => {
    // tries to create the new movie
    try {
        return res.json(await movieServices.changeMovie(req.params.id, req.body.name, req.body.video, req.body.categories));
    }
    
    catch(e) {
        // failed because ID isn't exist
        if (e[0] === 404) {
            return res.status(404).json({"error" : e[1]})
        }
        // fail if the name or video or categories weren't given or if the name is already in use by another movie
        return res.status(400).json({"errors" : e})
    }
};

// deletes movie which was specified by it's Id. require authantication.
const deleteMovie = async (req, res) => {
    // tries to delete the movie
    try {
        const movie = await movieServices.deleteMovie(req.params.id);
        return res.status(204).json()
    }
    // throws exeption in case the delete failed
    catch (e) {
        return res.status(404).json({ "error": e });
    }
}

// gets specific movie. requires authantication.
const getMovie = async (req, res) => {
    try {
        // gets the movie object by it's Id
        const movie = await movieServices.getMovieById(req.params.id);
        res.json(movie)
    }
    // sends an error in case the movie Id isn't exits
    catch(e) {
        return res.status(404).json({ "error": e });
    }
};

// get 20 movies from any promoted category and the 20 latest viewd movies
const getMovies = async (req, res) => {
    try {
        return res.status(200).json(await movieServices.getMovies(req.header('userId')))
    }
    // raise 404 error in case user not found
    catch(e) {
        return res.status(404).json({"error": "User not found"})
    }
}




export const movieController =  {createMovie, getMovie, changeMovie, deleteMovie, getMovies}