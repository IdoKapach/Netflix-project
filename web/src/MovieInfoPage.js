import { useParams } from "react-router-dom";
import moviess from "./movies.json"
import CategoryCarousel from "./components/CategoryCarousel";

function MovieInfoPage({token}) {
    const {movieId} = useParams()
    // gets the movie object by api/movies/:movieId
    let movie = moviess["Action"][0]


    // gets the recommended movies by api/movies/recommend/:movieId
    let recoMovies = moviess["Comedy"]


    return (
    <div>
        <div style={{ display: "flex", gap: "10%", alignItems: "center", justifyContent: "center", marginTop: "1.5%"}}>
            <img class="rounded" src={`http://localhost:8777/media/${movie.img}`} height={"350px"} width="auto" alt={`the image of ${movie.name}`} />
            <div style={{display: "flex", flexDirection: "column", alignItems: "center", justifyContent: "center"}}>
                <a href={`/movie-play/${movie.id}`} type="button" class="btn btn-primary btn-lg" style={{marginBottom: "10%"}}>Watch movie</a>
                <div class="card" style={{maxWidth: "40vw"}}>
                <div class="card-body">
                    {movie.description}
                </div>
                </div>
            </div>
        </div>
        <CategoryCarousel token={token} category={"Recommends"} movies={recoMovies} id={0} />
    </div>
)
}

export default MovieInfoPage