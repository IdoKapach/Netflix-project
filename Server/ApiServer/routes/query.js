import express from 'express';
var QueryRouter = express.Router();
import {getMovieByQuery} from '../controllers/query.js';

// get and post calls for url api/movies
QueryRouter.route('/:query')
.get(getMovieByQuery)

export {QueryRouter};