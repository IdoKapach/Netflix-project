import express from 'express'
import cors from 'cors'
import { userRouter } from './routes/User.js'
import { tokenRouter } from './routes/Token.js'
import { CategoryRouter } from './routes/category.js'
import { recommendRouter } from './routes/recommend.js'
import { MovieRouter } from './routes/movie.js'
import { QueryRouter } from './routes/query.js'
import { authantication } from './controllers/authantication.js'
import { UploadRouter } from './routes/Upload.js'
import mongoose from 'mongoose'
import dotenv from 'dotenv'
import path from 'path'
const __dirname = import.meta.dirname;
console.log(__dirname);

// load env variables
dotenv.config()

// init express app
const app = express();

// set mongoURI from .env
const mongoUri = process.env.MONGO_URI
// connect to mongoDB
try {
    await mongoose.connect(mongoUri)
    console.log('Connected to mongoDB')
} catch (e) {
    console.error('Failed to connect to mongoDB', e)
}

// enable CORS
app.use(cors())
// enable json content
app.use(express.json())
// enable url-encoded body parsing
app.use(express.urlencoded({extended:true}))

// append static directory access (with auth middlewear)
app.use('/media', 
    authantication(),
    express.static(path.join(__dirname, 'media')));

// add the user routes under /api route
app.use('/api', userRouter)
// add the token route under /api route
app.use('/api', tokenRouter)
// add the recommend route under /api route
app.use('/api', recommendRouter)
// add the category route under /api/categories route
app.use('/api/categories', CategoryRouter)
// add the movie route under /api/movies route
app.use('/api/movies', MovieRouter)
// add the query route under /api/movies/search route
app.use('/api/movies/search', QueryRouter)

// read PORT from .env (default to 3000)
const PORT = process.env.API_PORT || 3000

app.listen(PORT, () => {
    console.log(`server is running on port ${PORT}`)
})