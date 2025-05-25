// import MovieCard from "./components/MovieCard";
import MoviePlayer from "./components/MoviePlayer";
import CategoryCarousel from "./components/CategoryCarousel";
import categoriess from "./movies.json";
import {useState, useEffect} from 'react'
import { getCategories, getMovies, getMovie } from "./fetchRequests";

// render the carusels of all the categories that return from the call to ex3
function Carousels({token}) {
    // get categoriees json by api/categories
    const [categories, setCategories] = useState({})
    useEffect(() => {
        const fetchCategories = async () => {
            const data = await getMovies(token)
            setCategories(data)
        };

        fetchCategories()
    }, [token]);



    let id=0
    // send each category's arguments to category carusel componrnt
    return (
        <div>
            {Object.keys(categories).map((category) => <CategoryCarousel token={token} category={category} movies={categories[category]} id={id++}/>)}
        </div>
    )
}

// render tye main page for the logged users
function MainLoggedPage({token}) {
    const [categories, setCategories] = useState({})
    const [video, setVideo] = useState(null)
    useEffect(() => {
        const fetchCategories = async () => {
            // get the categories object from api/movies
            const data = await getMovies(token)
            setCategories(data)
            
            // get some random movie from this db
            const keys = Object.keys(data).filter(key => 
                Array.isArray(data[key]) && data[key].length > 0)
            console.log("KEYS:", keys)
            if (keys.length === 0) return 

            const randomKey = keys[Math.floor(Math.random() * keys.length)]
            const movies = data[randomKey]
            console.log("RANDOMKEY", randomKey)

            if (!movies || movies.length === 0) return 

            let movieId = movies[Math.floor(Math.random() * movies.length)];
            console.log("movieId:", movieId)
            // gets the video of the chosen movie
            const res = await getMovie(token, movieId)
            console.log("VIDEO PATH:", res.video)
            setVideo(res.video)
        }

        fetchCategories();

    }, [token]);


    return (
    <div>
        <MoviePlayer video={video} isHomePage={true} token={token} />
        <Carousels token={token}/>
    </div>
    );
}

export default MainLoggedPage;
