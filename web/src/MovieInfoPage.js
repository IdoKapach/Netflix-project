import { Navigate, useParams } from "react-router-dom";
import moviess from "./movies.json"
import CategoryCarousel from "./components/CategoryCarousel";
import { useState, useEffect } from "react";
import { getMovie, getRecommends } from "./fetchRequests";
import NoFoundPage from "./NoFoundPage";
import { Link } from 'react-router-dom'

// render the movie info page
function MovieInfoPage({token}) {
    const {movieId} = useParams()
    
    const [movie, setMovie] = useState(null)
    const [recoMovies, setRecommends] = useState([])

    useEffect(() => {
        const fetchCategories = async () => {
            // gets the movie object by fetching api/movies/:movieId
            const data = await getMovie(token, movieId)
            console.log("movie:", data)
            setMovie(data)
            // gets the recommends for the movie by calling api/movies/recommend/:movieId
            const recommends = await getRecommends(token, movieId)
            setRecommends(recommends)
        };

        fetchCategories()
    }, [token, movieId])


    console.log("reco:", recoMovies)
    // render no-found-page in case movie wasn't found
    if (!movie) {
        return <NoFoundPage />
    }
    if (!recoMovies) {
        console.log("")
    }


    return (
    <div>
        <div style={{ display: "flex", gap: "10%", alignItems: "center", justifyContent: "center", marginTop: "1.5%"}}>
            <img class="rounded" src={`http://localhost:3000/${movie.image}?token=${token}`} height={"350px"} width="auto" alt={`the image of ${movie.name}`} />
            <div style={{display: "flex", flexDirection: "column", alignItems: "center", justifyContent: "center"}}>
                <Link to={`/movie-play/${movie._id}?token=${token}`} type="button" class="btn btn-primary btn-lg" style={{marginBottom: "10%"}}>Watch movie</Link>
                <div class="card" style={{maxWidth: "40vw"}}>
                <div class="card-body">
                    {movie.description}
                </div>
                </div>
            </div>
        </div>
        {recoMovies ? <CategoryCarousel token={token} category={"Recommends"} movies={recoMovies.map((movie) => movie._id)} id={0} /> : null}
    </div>
)
}

export default MovieInfoPage