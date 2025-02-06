import express from 'express';
import movieUpload from '../controllers/movieUpload.js';
import {movieController} from '../controllers/movie.js';
import { authantication } from '../controllers/authantication.js';


var MovieRouter = express.Router();

// get and post calls for url api/movies
MovieRouter.route('/')
    .post(authantication('admin'), movieUpload, movieController.createMovie)
    .get(authantication(), movieController.getMovies)

// get, patch and delete calls for url api/movies/:id
MovieRouter.route('/:id')
    .get(authantication(), movieController.getMovie)
    .put(authantication('admin'), movieController.changeMovie)
    .delete(authantication('admin'), movieController.deleteMovie)

export {MovieRouter};