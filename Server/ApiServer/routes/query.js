import express from 'express';
var QueryRouter = express.Router();
import {getMovieByQuery} from '../controllers/query.js';
import { authantication } from '../controllers/authantication.js'

// get and post calls for url api/movies
QueryRouter.route('/:query')
    .get(authantication(), getMovieByQuery)

export {QueryRouter};