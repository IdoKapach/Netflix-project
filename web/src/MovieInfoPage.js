import { Navigate, useParams } from "react-router-dom";
import moviess from "./movies.json"
import CategoryCarousel from "./components/CategoryCarousel";
import { useState, useEffect } from "react";
import { getMovie, getRecommends } from "./fetchRequests";
import NoFoundPage from "./NoFoundPage";

function MovieInfoPage({token}) {
    const {movieId} = useParams()
    // gets the movie object by api/movies/:movieId
    // let movie = moviess["Action"][0]
    const [movie, setMovie] = useState(null)
    const [recoMovies, setRecommends] = useState([])
    useEffect(() => {
        const fetchCategories = async () => {
            const data = await getMovie(token, movieId)
            console.log("movie:", data)
            setMovie(data)
            const recommends = await getRecommends(token, movieId)
            setRecommends(recommends)
        };

        fetchCategories();
    }, [token, movieId]);


    // gets the recommended movies by api/movies/recommend/:movieId
    // let recoMovies = moviess["Comedy"]
    console.log("reco:", recoMovies)
    if (!movie) {
        return <NoFoundPage />
    }


    return (
    <div>
        <div style={{ display: "flex", gap: "10%", alignItems: "center", justifyContent: "center", marginTop: "1.5%"}}>
            <img class="rounded" src={`http://localhost:3000/${movie.image}?token=${token}`} height={"350px"} width="auto" alt={`the image of ${movie.name}`} />
            <div style={{display: "flex", flexDirection: "column", alignItems: "center", justifyContent: "center"}}>
                <a href={`/movie-play/${movie._id}?token=${token}`} type="button" class="btn btn-primary btn-lg" style={{marginBottom: "10%"}}>Watch movie</a>
                <div class="card" style={{maxWidth: "40vw"}}>
                <div class="card-body">
                    {movie.description}
                </div>
                </div>
            </div>
        </div>
        {recoMovies ? <CategoryCarousel token={token} category={"Recommends"} movies={recoMovies} id={0} /> : null}
    </div>
)
}

export default MovieInfoPage