import express from 'express';
var MovieRouter = express.Router();
import {movieController} from '../controllers/movie.js';

// get and post calls for url api/movies
MovieRouter.route('/')
.post(movieController.createMovie)
.get(movieController.getMovies)

// get, patch and delete calls for url api/movies/:id
MovieRouter.route('/:id')
.get(movieController.getMovie)
.put(movieController.changeMovie)
.delete(movieController.deleteMovie)

export {MovieRouter};